package com.example.lorentzfactor;

import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;

public class SpiActivity extends AppCompatActivity {
    int minute;
    int hour;
    int second;
    double spi;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spi);
        Timer timer1 = new Timer();
        Timer timer2 = new Timer();

        timer1.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Time();
                        TextView ValueTextView = (TextView) findViewById(R.id.Spi_Factor_Value);
                        ValueTextView.setText(null);
                        displayValue(spi());
                        displaySecond(Second());
                        displayMinute(Minute());
                    }
                });
            }
        },0,1000);

        timer2.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Time();
                        TextView ValueTextView = (TextView) findViewById(R.id.Spi_Factor_Value);
                        ValueTextView.setText(null);
                        displayHour(Hour());
                    }
                });
            }
        },0,3600000);

    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    public void Time() {
        LocalDateTime rn = LocalDateTime.now();
        minute = rn.getMinute();
        hour = rn.getHour();
        if(hour>12){
            hour=hour-12;
        }
        second = rn.getSecond();
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
        spi =  (hourFact() / ((Math.pow(minute,3)) + second ));
        return String.valueOf(spi);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String Hour() {
        String Hour = String.valueOf(hour);
        return Hour;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String Minute() {
        String Minute = String.valueOf(minute);
        return Minute;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String Second() {
        String Second = String.valueOf(second);
        return Second;
    }

    private void displayValue(String Value) {
        TextView ValueTextView = (TextView) findViewById(R.id.Spi_Factor_Value);
        ValueTextView.setText(Value);
    }

    private void displayHour(String Value) {
        TextView displayHour = (TextView) findViewById(R.id.CurrentHour);
        displayHour.setText("Hour: " + Value);
    }

    private void displayMinute(String Value) {
        TextView displayMinute = (TextView) findViewById(R.id.CurrentMinute);
        displayMinute.setText("Minute: " + Value);
    }

    private void displaySecond(String Value) {
        TextView displaySecond = (TextView) findViewById(R.id.CurrentSecond);
        displaySecond.setText("Second: " + Value);
    }
}
