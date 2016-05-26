package com.cun.appnotas.appnotas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by root on 25/05/16.
 */
public class AvisoDBAdapter {

    //Nombres de las columnas
    public static final String COL_ID = "_id";
    public static final String COL_CONTENT = "content";
    public static final String COL_IMPORTANT = "important";

    //indices correspondientes
    public static final int INDEX_ID = 0;
    public static final int INDEX_CONTENT = INDEX_ID + 1;
    public static final int INDEX_IMPORTANT = INDEX_ID + 2;

    //usado para logging
    private static final String TAG = "AvisosDBAdapter";

    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    private static final String DATABASE_NAME = "dba_remdrs";
    private static final String TABLE_NAME = "tbl_remdls";
    private static final int DATABASE_VERSION = 1;

    private final Context mCtx;

    //Declaracion de la creacion de la base de datos
    private static final String DATABASE_CREATE=
            "CREATE TABLE if no exists "+ TABLE_NAME+ " ("+
                    COL_ID + " INTEGER PRIMARY KEY autoincrement, "+
                    COL_CONTENT + " TEXT, "+
                    COL_IMPORTANT+ " INTEGER);";

    public AvisoDBAdapter(Context ctx){
        this.mCtx = ctx;
    }

    public void Open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
    }

    public void Close(){
        if( mDbHelper != null ){
            mDbHelper.close();
        }
    }

    //INSERT
    public void createReminder(String name, boolean important){
        ContentValues values = new ContentValues();
        values.put(COL_CONTENT, name);
        values.put(COL_IMPORTANT, important? 1:0);
        mDb.insert(TABLE_NAME,null, values);
    }

    public long createReminder(Aviso reminder){
        ContentValues values = new ContentValues();
        values.put(COL_CONTENT,reminder.getmContent());
        values.put(COL_IMPORTANT,reminder.getmImportant());

        return mDb.insert(TABLE_NAME,null,values);
    }

    //SEARCH
    public Aviso fetchReminderById(int id){
        Cursor cursor = mDb.query(TABLE_NAME, new String[]{COL_ID,
                                COL_CONTENT,COL_IMPORTANT},COL_ID+"=?",
                                new String[]{String.valueOf(id)},null,null,null,null);

        if(cursor != null)
            cursor.moveToFirst();

        return new Aviso(
            cursor.getInt(INDEX_ID),
            cursor.getString(INDEX_CONTENT),
            cursor.getInt(INDEX_IMPORTANT)
        );

    }

    public Cursor fetchAllReminders(){
        Cursor mCursor = mDb.query(TABLE_NAME, new String[]{COL_ID,
                    COL_CONTENT, COL_IMPORTANT},
                    null,null,null,null,null);

        if(mCursor != null)
            mCursor.moveToFirst();

        return mCursor;

    }

    //UPDATE
    public void updateReminder( Aviso reminder){
        ContentValues values = new ContentValues();

        values.put(COL_CONTENT, reminder.getmContent());
        values.put(COL_IMPORTANT, reminder.getmImportant());

        mDb.update(TABLE_NAME, values,
                COL_ID + "=?", new String[]{String.valueOf(reminder.getmId())});
    }

    //DELETE
    public void deleteReminderById(int nId){
        mDb.delete(TABLE_NAME, COL_ID+"=?",new String[]{String.valueOf(nId)});
    }

    public void deleteAllReminder(){
        mDb.delete(TABLE_NAME,null,null);
    }


    public static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context){
            super(context, DATABASE_NAME,null, DATABASE_VERSION);
        }


        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.w(TAG, DATABASE_CREATE);
            db.execSQL(DATABASE_CREATE);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Actualizando la base de datos de la version "+oldVersion+
                        " a "+newVersion+", se destruiran los datos antiguos");
            db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
            onCreate(db);

        }
    }
}
