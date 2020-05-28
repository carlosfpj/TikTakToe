package com.cfpj.tiktaktoe;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

public class LoadGameActivity extends AppCompatActivity {
    private static SQLiteDatabase db;
    private static Cursor cursor;
    private int [] id;
    private String [] player1, player2;
    private int [] score1, score2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_game);

        try{
            SQLiteOpenHelper gameDbHelper = new GameDbHelper(this);
            db = gameDbHelper.getReadableDatabase();

            cursor = db.query("GAMES",new String[]{"_id", "PLAYER1", "PLAYER2", "SCORE1", "SCORE2"}, null,null, null, null, null, null);
            int cursorCount = cursor.getCount();
            //test ok
            Log.d(LoadGameActivity.class.toString(), "CURSOR COUNT = " + String.valueOf(cursorCount));
            //test, catching column names inside the cursor, TEST OK
            Log.d(LoadGameActivity.class.toString(), "CATCHING CURSOR COLUMN 0 NAME = : " + cursor.getColumnName(0) + " CURSOR COLUMN 1 NAME = : " + cursor.getColumnName(1));

            //test, catching column names as array, TEST OK
            String [] columnNames = cursor.getColumnNames();
            for(int i=0; i<columnNames.length; i++){
                Log.d(LoadGameActivity.class.toString(), "CATCHING CURSOR COLUMN NAMES = : " + columnNames[i] + " AT LINE 37");
            }

            id = new int[cursorCount];
            player1 = new String[cursorCount];
            player2 = new String[cursorCount];
            score1 = new int[cursorCount];
            score2 = new int[cursorCount];

            for(int i=0; i<cursorCount; i++) {
                Log.d(LoadGameActivity.class.toString(), "DEFAULT CLASS VALUES = " + String.valueOf(id[i]) + " " + player1[i] + " " + player2[i] + " " + String.valueOf(score1[i])
                                                                                  +  " " + String.valueOf(score2[i]) + " AT LINE 49");
            }

             if (cursor != null && cursorCount !=0) {
                if(cursor.moveToFirst()) {
                    do{
                        for (int i = 0; i < cursorCount; i++) {
                            id[i] = cursor.getInt(0);
                            player1[i] = cursor.getString(1);
                            player2[i] = cursor.getString(2);
                            score1[i] = cursor.getInt(3);
                            score2[i] = cursor.getInt(4);
                            cursor.moveToNext();
                            //test, OK
                            Log.d(LoadGameActivity.class.toString(), "CATCHING REAL CURSOR VALUES AT LINE 63, ID = " + id[i] + ", PLAYER 1 = " + player1[i] + ",  PLAYER 2 = " + player2[i] + ", SCORE 1 = " + score1[i] + ", SCORE 2 = " + score2[i]);
                        }
                    }
                    while (cursor.moveToNext());
                }
                 Log.d(LoadGameActivity.class.toString(), "CATCHING REAL CURSOR VALUES ID AT LINE 68, ID = " + id[0] + id[1]);
                 LoadGameAdapter gameAdapter = new LoadGameAdapter(id,player1,player2,score1,score2);
                 RecyclerView gameRecycler = findViewById(R.id.recView_games_loaded);
                 gameRecycler.setAdapter(gameAdapter);
                 RecyclerView.LayoutManager recyclerLayoutManager = new LinearLayoutManager(this);
                 gameRecycler.setLayoutManager(recyclerLayoutManager);

            }else{
                 gamesNoFound();
            }
        } catch (SQLiteException e){
            Log.d(LoadGameActivity.class.toString(), "DATABASE UNAVAILABLE");
        }

    }

    public void gamesNoFound(){
        Log.d(LoadGameActivity.class.toString(),"GAMESNOFOUND");
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cursor.close();
        db.close();
    }
}
