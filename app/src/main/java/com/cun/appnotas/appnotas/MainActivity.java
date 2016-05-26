package com.cun.appnotas.appnotas;

import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private ListView mListView;
    private AvisoDBAdapter mDbdapter;
    private AvisosSimpleCursorAdapater mCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = (ListView) findViewById(R.id.avisos_list_view);
        findViewById(R.id.avisos_list_view);
        mListView.setDivider(null);
        mDbdapter = new AvisoDBAdapter(this);
        mDbdapter.Open();

        if( savedInstanceState == null ){
            //limpiar todos los datos
            mDbdapter.deleteAllReminder();
            //Add algunos datos
            mDbdapter.createReminder("Visitar el centro de Recogida",true);
            mDbdapter.createReminder("Enviar los regalos prometidos",false);
            mDbdapter.createReminder("Hacer la compra semanal",false);
            mDbdapter.createReminder("Comprobar el correo",false);

        }

        Cursor cursor = mDbdapter.fetchAllReminders();

        //desde las columnas defnidas en la base de datos
        String[] from = new String[]{
                AvisoDBAdapter.COL_CONTENT
        };

        // la id de views en el layout
        int[] to = new int[]{
                R.id.row_text
        };


        mCursorAdapter = new AvisosSimpleCursorAdapater(
                MainActivity.this,
                R.layout.avisos_row,
                cursor,
                from,
                to,
                0
        );


        mListView.setAdapter(mCursorAdapter);
        /*
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        //accion cuando seleccionamos un item de la lista
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, final int masterListPosition, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                ListView modeListView = new ListView(MainActivity.this);
                String[] modes = new String[] { "Editar Aviso", "Borrar Aviso" };
                ArrayAdapter<String> modeAdapter = new ArrayAdapter<>(MainActivity.this,
                        android.R.layout.simple_list_item_1, android.R.id.text1, modes);
                modeListView.setAdapter(modeAdapter);
                builder.setView(modeListView);
                final Dialog dialog = builder.create();
                dialog.show();
                modeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //editar aviso
                        if (position == 0) {
                            Toast.makeText(MainActivity.this, "editar "
                                    + masterListPosition, Toast.LENGTH_SHORT).show();
                            //borrar aviso
                        } else {
                            Toast.makeText(MainActivity.this, "borrar "
                                    + masterListPosition, Toast.LENGTH_SHORT).show();
                        }
                        dialog.dismiss();
                    }
                });
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_nuevo:
                Log.d( getLocalClassName(),"Crear nuevo aviso" );
                break;
            case R.id.action_salir:
                finish();
                break;
            default:
                return false;
        }
        return false;
    }
}
