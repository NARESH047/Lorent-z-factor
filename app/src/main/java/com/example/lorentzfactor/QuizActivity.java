package com.example.lorentzfactor;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


/**
 * This app finds and displays Lorentz Factor for given relative velocity.
 */
public class QuizActivity extends AppCompatActivity {
    Double Vel, c, C;
    int Score = 0;
    int hs;
    SharedPreferences highScore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checker);
        highScore = getSharedPreferences("High Score", 0);

    }


    /**
     * This method checks if CHECK button is clicked.
     * Checks if relative velocity, obtained answer has been provided if not informs the same through toast.
     * Checks if answered Lorentz's factor is correct.
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void Check(View V) {

        EditText velocity = (EditText) findViewById(R.id.velocity);
        EditText GivenAnswer = (EditText) findViewById(R.id.GiveAnswer);

        if (TextUtils.isEmpty(velocity.getText().toString())) {
            Context context = getApplicationContext();
            CharSequence text = "Enter relative velocity";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

        } else if (TextUtils.isEmpty(GivenAnswer.getText().toString())) {
            Context context = getApplicationContext();
            CharSequence text = "Enter your answer";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

        } else {
            Double given = Double.parseDouble(GivenAnswer.getText().toString());
            Double ForTrucating = Math.floor(given * Math.pow(10, 3));
            Double givenTrucated = ForTrucating / (Math.pow(10, 3));
            String given_rounded = String.format("%.3f",givenTrucated);
            String givenAnswer = given_rounded;
            String CorrectAnswer = calculate();


            if (givenAnswer.equals(CorrectAnswer)) {

                LinearLayout DuringGame = (LinearLayout) findViewById(R.id.DuringGame);
                DuringGame.setVisibility(View.GONE);

                velocity.setText("");
                GivenAnswer.setText("");

                LinearLayout AfterCorrect = (LinearLayout) findViewById(R.id.AfterCorrect);
                AfterCorrect.setVisibility(View.VISIBLE);
                Score = Score + 1;
                DisplayScoreDuringGame(Score);


            } else {
                hs = highScore.getInt("highScore", 0);
                if (Score > hs) {
                    highScore.edit().putInt("highScore", Score).commit();
                    DisplayHighScoreAfterGame(Score);
                } else {
                    DisplayHighScoreAfterGame(hs);
                }

                LinearLayout DuringGame = (LinearLayout) findViewById(R.id.DuringGame);
                DuringGame.setVisibility(View.GONE);

                LinearLayout AfterWrong = (LinearLayout) findViewById(R.id.AfterWrong);
                AfterWrong.setVisibility(View.VISIBLE);
                displayValue(CorrectAnswer);
                displayGivenAnswer(givenAnswer);

                velocity.setText("");
                GivenAnswer.setText("");


                DisplayScoreAfterGame(Score);

                Vibrator w = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    w.vibrate(VibrationEffect.createOneShot(800, VibrationEffect.DEFAULT_AMPLITUDE));
                } else {
                    w.vibrate(800);
                }

            }
        }
    }


    /**
     * Checks for unit of relative velocity entered.
     * Calls calculation method.
     */
    public String calculate() {
        RadioButton isMeterSec = (RadioButton) findViewById(R.id.meter_sec);
        boolean MeterSec = isMeterSec.isChecked();
        RadioButton isFeetSec = (RadioButton) findViewById(R.id.feet_sec);
        boolean FeetSec = isFeetSec.isChecked();

        EditText velocity = (EditText) findViewById(R.id.velocity);
        if (TextUtils.isEmpty(velocity.getText().toString())) {
            Context context = getApplicationContext();
            CharSequence text = "Enter relative velocity";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

        } else{
            String Velocity = velocity.getText().toString();
            Vel = Double.parseDouble(Velocity);

            if (MeterSec) {
                c = 299792458.0;
                String Value = calculation(Vel, c);
                return (Value);
            } else if (FeetSec) {
                C = 299792458 * 3.280839895;
                String Value = calculation(Vel, C);
                return (Value);
            }
            return "1";
        }

        return "1";
    }


    /**
     * *
     * Calculates Lorentz factor.
     * Limits decimal places to 3 digits.
     *
     * @param Vel
     * @param c
     * @return String for display
     */
    public String calculation(Double Vel, Double c) {
        if ((0 <= Vel) && (Vel < c)) {
            Double LorentzFactor;
            Double z = Math.pow((Vel / c), 2);
            Double t = (1 - z);
            LorentzFactor = (Math.pow(t, -0.5));
            Double ForTrucating = Math.floor(LorentzFactor * Math.pow(10, 3));
            Double LorentzFactorTrucated = ForTrucating / (Math.pow(10, 3));
            String LF_rounded = String.format("%.3f",LorentzFactorTrucated);
            return (LF_rounded);

        } else {
            Context context = getApplicationContext();
            CharSequence text = "Check relative velocity \nRelative velocity is out of bound";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

        }

        return null;
    }


    /**
     * This method displays Lorentz's factor value on the screen.
     */
    private void displayValue(String Value) {
        TextView ValueTextView = (TextView) findViewById(R.id.CorrectAnswerDisplay);
        ValueTextView.setText("Correct Answer: "+ Value);
    }


    /**
     * This method displays given answer on the screen.
     */
    private void displayGivenAnswer(String givenanswer) {
        TextView ValueTextView = (TextView) findViewById(R.id.GivenAnswer);
        ValueTextView.setText("Given Answer: "+givenanswer);
    }


    /**
     * This method displays Score on the screen during game.
     */
    private void DisplayScoreDuringGame(int num) {
        TextView ValueTextView = (TextView) findViewById(R.id.DisplayScoreDuringGame);
        ValueTextView.setText("Score: " + num);
    }


    /**
     * This method displays Score on the screen after game.
     */
    private void DisplayScoreAfterGame(int num) {
        TextView ValueTextView = (TextView) findViewById(R.id.DisplayScoreAfterGame);
        ValueTextView.setText("Score: " + num);
    }

    /**
     * This method displays High Score on the screen after game.
     */
    private void DisplayHighScoreAfterGame(int num) {
        TextView ValueTextView = (TextView) findViewById(R.id.DisplayHighScoreAfterGame);
        ValueTextView.setText("High Score: " + num);
    }


    /**
     * This method is called when NEXT button is clicked.
     * Makes After correct layout disappear and during game layout to appear on screen.
     */
    public void Next(View v) {

        TextView scoreTextView = (TextView) findViewById(R.id.DisplayScoreDuringGame);
        scoreTextView.setText("");

        LinearLayout AfterCorrect = (LinearLayout) findViewById(R.id.AfterCorrect);
        AfterCorrect.setVisibility(View.GONE);

        LinearLayout DuringGame = (LinearLayout) findViewById(R.id.DuringGame);
        DuringGame.setVisibility(View.VISIBLE);

    }


    /**
     * This method is called when TRY AGAIN button is clicked.
     * Makes After wrong layout disappear and during game layout appear on screen.
     */
    public void TryAgain(View v) {

        TextView scoreTextView = (TextView) findViewById(R.id.DisplayScoreDuringGame);
        scoreTextView.setText(" ");

        Score = 0;

        LinearLayout AfterWrong = (LinearLayout) findViewById(R.id.AfterWrong);
        AfterWrong.setVisibility(View.GONE);

        LinearLayout DuringGame = (LinearLayout) findViewById(R.id.DuringGame);
        DuringGame.setVisibility(View.VISIBLE);


    }

}
