package com.cfpj.tiktaktoe;

import android.content.Intent;
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

            id = new int[cursorCount];
            player1 = new String[cursorCount];
            player2 = new String[cursorCount];
            score1 = new int[cursorCount];
            score2 = new int[cursorCount];

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
                        }
                    }
                    while (cursor.moveToNext());
                }
                 LoadGameAdapter gameAdapter = new LoadGameAdapter(id,player1,player2,score1,score2);
                 RecyclerView gameRecycler = findViewById(R.id.recView_games_loaded);
                 gameRecycler.setAdapter(gameAdapter);
                 RecyclerView.LayoutManager recyclerLayoutManager = new LinearLayoutManager(this);
                 gameRecycler.setLayoutManager(recyclerLayoutManager);
                 gameAdapter.setListener(new LoadGameAdapter.Listener() {
                     @Override
                     public void onClick(int position) {
                         Intent intent = new Intent(getApplicationContext(), GameActivity.class);
                         intent.putExtra("gameId", id[position]);
                         intent.putExtra("player1Name", player1[position]);
                         intent.putExtra("player2Name", player2[position]);
                         intent.putExtra("player1Score", score1[position]);
                         intent.putExtra("player2Score", score2[position]);

                         startActivity(intent);
                     }
                 });

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
