package com.method.gorbovmethod;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.method.gorbovmethod.controllers.PartOne;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickButtonStartTest(View view) {
        Intent intent = new Intent(this, PartOne.class);
        startActivity(intent);
    }
}
