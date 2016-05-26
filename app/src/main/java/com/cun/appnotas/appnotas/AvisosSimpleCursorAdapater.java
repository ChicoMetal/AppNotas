package com.cun.appnotas.appnotas;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;

/**
 * Created by root on 25/05/16.
 */
public class AvisosSimpleCursorAdapater extends SimpleCursorAdapter {

    public AvisosSimpleCursorAdapater(Context context, int layout, Cursor c,String[] from, int[] to, int flags) {
        super(context,layout,c, from, to, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return super.newView(context, cursor, parent);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        super.bindView(view, context, cursor);

        ViewHolder holder = (ViewHolder) view.getTag();
        if( holder == null){
            holder = new ViewHolder();
            holder.colImp = cursor.getColumnIndexOrThrow(AvisoDBAdapter.COL_IMPORTANT);
            holder.listTab = view.findViewById(R.id.row_view);
            view.setTag(holder);
        }

        if(cursor.getInt(holder.colImp) > 0 ){
            holder.listTab.setBackgroundColor(context.getResources().getColor(R.color.naranja));
        }else{
            holder.listTab.setBackgroundColor(context.getResources().getColor(R.color.rosa));
        }
    }

    static class ViewHolder{
        //almacena el index de la columna
        int colImp;
        View listTab;
    }
}
