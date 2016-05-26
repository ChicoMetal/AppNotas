package com.cun.appnotas.appnotas;

/**
 * Created by root on 25/05/16.
 */
public class Aviso {

    private int mId;
    private String mContent;
    private int mImportant;


    public Aviso( int id, String content, int important){
        mId = id;
        mContent = content;
        mImportant = important;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmContent() {
        return mContent;
    }

    public void setmContent(String mContent) {
        this.mContent = mContent;
    }

    public int getmImportant() {
        return mImportant;
    }

    public void setmImportant(int mImportant) {
        this.mImportant = mImportant;
    }
}
