package com.malte_mueller.dokobo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class GameInputActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameinput);

        // your text box
        EditText valueInput = (EditText) findViewById(R.id.eT_gameValue);

        valueInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                Log.d("poo", v.getText().toString());
                return false;
            }
        });
    }

    public void onGameSubmit(View v){
        //TODO: implement

    }
}
