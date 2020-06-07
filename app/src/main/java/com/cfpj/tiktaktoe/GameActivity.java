package com.cfpj.tiktaktoe;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity implements View.OnClickListener, SetNamesDialogFragment.iSetNamesDialogListener {

    private Button[][] mgridButtons = new Button[3][3];
    private Button mrestartBoard, mrestartGame;
    private boolean player1Turn = true;
    private int count, player1Points, player2Points;
    private TextView player1Score, player2Score;
    private boolean isGridLocked;
    String player1Name, player2Name;
    private SQLiteDatabase db;
    private int gameId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        bindViews();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(getIntent().getExtras() != null){

            gameId = getIntent().getExtras().getInt("gameId");
            player1Name = getIntent().getStringExtra("player1Name");
            player2Name = getIntent().getStringExtra("player2Name");
            player1Points = getIntent().getExtras().getInt("player1Score");
            player2Points = getIntent().getExtras().getInt("player2Score");
            updateScore(player1Points, player2Points);
        }

        if (savedInstanceState != null) {
            bindViews();
            player1Points = savedInstanceState.getInt("player1Points");
            player2Points = savedInstanceState.getInt("player2Points");
            player1Turn = savedInstanceState.getBoolean("turn");
            count = savedInstanceState.getInt("count");
            updateScore(player1Points, player2Points);
            isGridLocked = savedInstanceState.getBoolean("isGridLocked");
            if (isGridLocked) {
                lockGrid();
            }
        }
    }

    private void bindViews() {
        mrestartBoard = findViewById(R.id.btn_restart_board);
        mrestartGame = findViewById(R.id.btn_restart_game);
        player1Score = findViewById(R.id.text_view_player1);
        player2Score = findViewById(R.id.text_view_player2);

        mrestartBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int i = 0; i < 3; i++)
                    for (int j = 0; j < 3; j++) {
                        mgridButtons[i][j].setText("");
                        player1Turn = true;
                        count = 0;
                        unlockGrid();
                    }
            }
        });

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonIdPrefix = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonIdPrefix, "id", "com.cfpj.tiktaktoe");
                mgridButtons[i][j] = findViewById(resID);
                mgridButtons[i][j].setOnClickListener(this);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);

        bundle.putInt("player1Points", player1Points);
        bundle.putInt("player2Points", player2Points);
        bundle.putBoolean("turn", player1Turn);
        bundle.putInt("count", count);
        bundle.putBoolean("isGridLocked", isGridLocked);

        String[][] field = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = mgridButtons[i][j].getText().toString();
            }
        }
    }

    @Override
    public void onClick(View v) {
        //If button has a different string than "", do nothing
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }
        if (player1Turn) {
            ((Button)v).setTextColor(getResources().getColor(R.color.colorCross));
            ((Button) v).setText("X");
        } else {
            ((Button)v).setTextColor(getResources().getColor(R.color.colorCircle));
            ((Button) v).setText("O");
        }
        count++;

        if (checkForWin()) {
            if (player1Turn) {
                player1Points++;
            } else {
                player2Points++;
            }
            playerWins(player1Turn);
            updateScore(player1Points, player2Points);
            lockGrid();
        } else if (count == 9) {
            draw();
            lockGrid();
        }
        player1Turn = !player1Turn;
    }

    private boolean checkForWin() {

        String[][] field = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = mgridButtons[i][j].getText().toString();
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")) {
                return true;
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")) {
                return true;
            }
        }

        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")) {
            return true;
        }

        if (field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")) {
            return true;
        }
        return false;
    }

    private void playerWins(boolean turn) {
        if (turn) {
            Toast toast = Toast.makeText(this, player1Name + " wins", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            Toast toast = Toast.makeText(this, player2Name + " wins", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private void draw() {
        Toast toast = Toast.makeText(this, "Draw", Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(getIntent().getExtras() == null) {
            showDialogFragment();
        }
    }

    private void showDialogFragment(){
        DialogFragment dialogFragment = new SetNamesDialogFragment();
        dialogFragment.show(getSupportFragmentManager(), "MyDialog");
    }

    private void updateScore(int score1, int score2) {

        String printedScore1 = String.valueOf(score1);
        String printedScore2 = String.valueOf(score2);

        if (score1 == 0 && score2 == 0) {
            player1Score.setText(player1Name + ": " + printedScore1 + " puntos");
            player2Score.setText(player2Name + ": " + printedScore2 + " puntos");
        } else {
            player1Score.setText(player1Name + ": " + printedScore1 + " puntos");
            player2Score.setText(player2Name + ": " + printedScore2 + " puntos");
        }
    }

    private boolean lockGrid() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                mgridButtons[i][j].setEnabled(false);
            }
        }
        isGridLocked = true;
        return isGridLocked;
    }

    private boolean unlockGrid() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                mgridButtons[i][j].setEnabled(true);
            }
        }
        isGridLocked = false;
        return isGridLocked;
    }

    public void restartGame(View view) {
        player1Points = 0;
        player2Points = 0;
        updateScore(player1Points, player2Points);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                mgridButtons[i][j].setText("");
            }
        }
        unlockGrid();
    }

    public void onDialogAcceptClick(DialogFragment dialog, String name1, String name2) {
        player1Name = name1;
        player2Name = name2;

        player1Score.setText(player1Name + " 0 puntos");
        player2Score.setText(player2Name + " 0 puntos");
        dialog.dismiss();
    }

    @Override
    protected void onStop() {
        super.onStop();

        ContentValues gameValues = new ContentValues();
        gameValues.put("PLAYER1", player1Name);
        gameValues.put("PLAYER2", player2Name);
        gameValues.put("SCORE1", player1Points);
        gameValues.put("SCORE2", player2Points);


        try{
            SQLiteOpenHelper gameDbHelper = new GameDbHelper(this);

            db = gameDbHelper.getWritableDatabase();

            Cursor cursor = db.query("GAMES", new String[]{"_id"}, null, null, null, null, null, null);
            int cursorCount = cursor.getCount();
            int[] cursorId = new int[cursorCount];

            if(cursor.moveToFirst()) {
                do {
                    for(int i=0; i<cursorCount; i++){
                        cursorId[i] = cursor.getInt(cursor.getColumnIndex("_id"));
                        cursor.moveToNext();
                    }
                }while(cursor.moveToNext());
            }

            if (cursorCount == 0){
                db.insert("GAMES", null, gameValues);
            }else {
                if(cursor.moveToFirst()){
                    for(int i=0; i<cursorCount; i++){
                        if (cursorId[i] != gameId && gameId > 0){
                            cursor.moveToNext();
                        }else if (cursorId[i] == gameId && gameId > 0){
                            ContentValues contentValues = new ContentValues();
                            contentValues.put("SCORE1", player1Points);
                            contentValues.put("SCORE2", player2Points);
                            db.update("GAMES", contentValues, "_id = ?", new String[]{String.valueOf(gameId)});
                        }else if (gameId == -1) {
                            db.insert("GAMES", null, gameValues);
                            break;
                        }
                    }
                }
            }
        }
        catch (SQLiteException e){
            Log.d(GameActivity.class.toString(), "DATABASE UNAVAILABLE AT CATCHEXCEPTION LINE 318");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }
}