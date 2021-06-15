package com.example.lorentzfactor;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


/**
 * This app finds and displays Lorentz Factor for given relative velocity.
 */
public class MainActivity extends AppCompatActivity {
    Double Vel,c,C;
    boolean MeterSec, FeetSec;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * Updates Lorentz's factor value if units of input is changed.
         * Calls calculate and display method.
         */
        RadioButton isMeterSec = (RadioButton) findViewById(R.id.meter_sec);
        MeterSec = isMeterSec.isChecked();
        RadioButton isFeetSec = (RadioButton) findViewById(R.id.feet_sec);
        FeetSec = isFeetSec.isChecked();

        isMeterSec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MeterSec = true;
                FeetSec = false;
                CalculateAndDisplay();
            }
        });

        isFeetSec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FeetSec = true;
                MeterSec = false;
                CalculateAndDisplay();
            }
        });


        /**
         *This method checks if relative velocity has been changed.
         * Updates Lorentz's factor value if relative velocity is changed.
         * Calls calculate and display method.
         */
        EditText velocity = (EditText) findViewById(R.id.velocity);
        velocity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                CalculateAndDisplay();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

}


    /**
     * Checks for unit of relative velocity entered.
     * Calls calculation method.
     */
    public String calculate(boolean MeterSec, boolean FeetSec) {
        EditText velocity = (EditText) findViewById(R.id.velocity);
        if (TextUtils.isEmpty(velocity.getText().toString())) {
            Context context = getApplicationContext();
            CharSequence text = "Enter relative velocity";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

        } else {
            String Velocity = velocity.getText().toString();
            Vel = Double.parseDouble(Velocity);

            if (MeterSec) {
                c = 299792458.0;
                String Value = calculation(Vel, c);
                return (Value);
            } else if (FeetSec) {
                C = 299792458 * (3.280839895);
                String Value = calculation(Vel, C);
                return (Value);
            }

        }

        return null;
    }


    /**
     * *
     * Calculates Lorentz factor.
     * Limits decimal places to 14 digits.
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
            Double ForTrucating = Math.floor(LorentzFactor * Math.pow(10,14));
            Double LorentzFactorTrucated = ForTrucating/(Math.pow(10,14));
            String LF = LorentzFactorTrucated.toString();
            return (LF);

        } else {
            Context context = getApplicationContext();
            CharSequence text = "Check relative velocity \nRelative velocity is out of bound";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

        }

        return "";
    }


    /**
     * This method displays Lorentz's factor value on the screen.
     */
    private void displayValue(String Value) {
        TextView ValueTextView = (TextView) findViewById(R.id.Lorentz_Factor_Value);
        ValueTextView.setText(Value);
    }


    /**
     * This method displays personal message ;) if Lorenz's factor value is on screen.
     */

    public void displayAboutTreat() {
        EditText velocity = (EditText) findViewById(R.id.velocity);
        if (TextUtils.isEmpty(velocity.getText().toString()) || !((0 <= Vel) && (Vel < c))) {
            TextView ValueTextView = (TextView) findViewById(R.id.TextAboutTreat);
            ValueTextView.setText("");

        } else {
            TextView PersonalTextView = (TextView) findViewById(R.id.TextAboutTreat);
            PersonalTextView.setText("Treat eppa Da?!");

        }

    }


    /**
     * calculates and displays message.
     * This method is called when either the unit is changed or relative velocity is changed.
     */

    public void CalculateAndDisplay() {
        String value = calculate(MeterSec, FeetSec);

        String VALUE = value;
        displayValue(VALUE);
        displayAboutTreat();

    }


}

