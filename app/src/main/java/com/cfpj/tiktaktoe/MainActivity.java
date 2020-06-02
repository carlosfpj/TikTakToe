package com.cfpj.tiktaktoe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView tv_tittle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindViews();
        coloredTitle();
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    public void exitApp(View view){
        finish();
    }

    public void newGame(View view){
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    public void loadGame(View view){
        Intent intent = new Intent(this, LoadGameActivity.class);
        startActivity(intent);
    }

    public void coloredTitle(){
        String text = getResources().getString(R.string.tv_game_name);

        SpannableString ss = new SpannableString(text);

        ForegroundColorSpan fcsBlue = new ForegroundColorSpan(getResources().getColor(R.color.colorCircle));
        ForegroundColorSpan fcsRed = new ForegroundColorSpan(getResources().getColor(R.color.colorCross));

        ss.setSpan(fcsBlue,0,3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(fcsRed,4,7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(CharacterStyle.wrap(fcsBlue),8,11, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_tittle.setText(ss);
    }

    public void bindViews(){
        tv_tittle = findViewById(R.id.tv_game_name);
    }
}
