package com.cfpj.tiktaktoe;

import android.content.ContentValues;
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

        db.execSQL("CREATE TABLE GAMES ( " + " _id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                         " PLAYER1 TEXT, " + " PLAYER2 TEXT, " +
                                         " SCORE1 INT, " + " SCORE2 INT);"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public static void insertGame(SQLiteDatabase db, String player1, String player2, int score1, int score2){
        ContentValues gameValues = new ContentValues();
        gameValues.put("PLAYER1", player1);
        gameValues.put("PLAYER2", player2);
        gameValues.put("SCORE1", score1);
        gameValues.put("SCORE2", score2);
    }
}
