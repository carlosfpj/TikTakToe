package com.cfpj.tiktaktoe;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class GameDbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "tictactoeDB";
    private static final int DB_VERSION = 1;

    GameDbHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE GAMES ( " + " _id INTEGER PRIMARY KEY AUTOINCREMENT, " + " PLAYER1 TEXT, " + " PLAYER2 TEXT, " + " SCORE1 INT, " + " SCORE2 INT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
