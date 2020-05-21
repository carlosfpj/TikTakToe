package com.cfpj.tiktaktoe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void exitApp(View view){
        finish();
    }

    public void play(View view){
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }
}
