package com.example.lorentzfactor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


/**
 * This app finds and displays Lorentz Factor for given relative velocity.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView Calculator = (TextView) findViewById(R.id.Calculator);
        Calculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent CalculatorIntent = new Intent(MainActivity.this, CalculatorActivity.class);
                startActivity(CalculatorIntent);
            }
        });

        TextView Quiz = (TextView) findViewById(R.id.Quiz);
        Quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent QuizIntent = new Intent(MainActivity.this, QuizActivity.class);
                startActivity(QuizIntent);
            }
        });
    }
}

