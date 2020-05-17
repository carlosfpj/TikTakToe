package com.cfpj.tiktaktoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button[][] mgridButtons = new Button[3][3];
    private Button mrestartBoard;
    private boolean player1Turn = true;
    private int count, player1Points, player2Points;
    private TextView player1Score, player2Score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState != null){
            bindViews();
            player1Points = savedInstanceState.getInt("player1Points");
            player2Points = savedInstanceState.getInt("player2Points");
            player1Turn = savedInstanceState.getBoolean("turn");
            count = savedInstanceState.getInt("count");
            updateScore(player1Points, player2Points);
        }
        bindViews();
    }

    private void bindViews() {
        mrestartBoard = findViewById(R.id.btn_reiniciar);
        player1Score = findViewById(R.id.text_view_player1);
        player2Score = findViewById(R.id.text_view_player2);

        mrestartBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for(int i=0; i<3; i++)
                    for (int j=0; j<3; j++){
                        mgridButtons[i][j].setText("");
                        player1Turn = true;
                        count = 0;
                        enableGrid();
                    }
            }
        });

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonIdPrefix = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonIdPrefix, "id", "com.cfpj.tiktaktoe");
                //NumberFormatException
                //mgridButtons[i][j] = findViewById(Integer.valueOf(buttonIdPrefix));
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

        String[][] field = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = mgridButtons[i][j].getText().toString();
            }
        }
    }

    @Override
    public void onClick(View v) {

        if (!((Button) v).getText().toString().equals("")) {
            return;
        }
        if (player1Turn) {
            ((Button) v).setText("X");
        } else {
            ((Button) v).setText("O");
        }
        count++;

        if (checkForWin()){
            if (player1Turn){
                player1Points++;
            }
            else {
                player2Points++;
            }
            playerWins(player1Turn);
            updateScore(player1Points, player2Points);
            disablegrid();
        }
        else if (count == 9) {
            draw();
            disablegrid();
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
                && !field[0][0].equals("")){
            return true;
        }

        if (field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")){
            return true;
        }
        return false;
    }

    private void playerWins(boolean turn){
        if(turn){
            Toast toast = Toast.makeText(this, "Player 1 wins", Toast.LENGTH_SHORT);
            toast.show();
        }
        else{
            Toast toast = Toast.makeText(this, "Player 2 wins", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private void draw(){
        Toast toast = Toast.makeText(this, "Draw", Toast.LENGTH_SHORT);
        toast.show();
    }

    private void updateScore(int score1, int score2){
        String printedScore1 = String.valueOf(score1);
        String printedScore2 = String.valueOf(score2);
        player1Score.setText("Player 1: " + printedScore1 + "Points");
        player2Score.setText("Player 2: "+ printedScore2 + "points");
    }

    private void disablegrid(){
        for (int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                mgridButtons[i][j].setEnabled(false);
            }
        }
    }

    private void enableGrid(){
        for (int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                mgridButtons[i][j].setEnabled(true);
            }
        }
    }
}
