package com.example.lorentzfactor;

import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class SpiActivity extends AppCompatActivity {
    Calendar now = Calendar.getInstance();
    int minute;
    int hour;
    int second;
    String spi;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spi);
        displayValue(spi());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void Time() {
        minute = now.get(Calendar.MINUTE);
        hour = now.get(Calendar.HOUR);
        second = now.get(Calendar.SECOND);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public int hourFact() {
        int i;
        int fact = 1;
        for(i=1;i<=hour;i++) {
            fact = fact*i;
        }
        return fact;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String spi() {
        Time();
        spi = String.valueOf((long) (hourFact() / ((Math.pow(minute,3)) + second )));
        return spi;
    }

    private void displayValue(String Value) {
        TextView ValueTextView = (TextView) findViewById(R.id.Spi_Factor_Value);
        ValueTextView.setText(Value);
    }
}
