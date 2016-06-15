package br.com.mindbit.controleacesso.dominio;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.mindbit.R;

public class ContagemRegressiva extends ActionBarActivity {
    private TextView txtTimerDay, txtTimerHour, txtTimerMinute, txtTimerSecond;
    private TextView tvEvent;
    private Handler handler;
    private Runnable runnable;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        txtTimerDay = (TextView) findViewById(R.id.textView);
        //tvEvent = (TextView) findViewById(R.id.tvhappyevent);
        countDownStart();
    }
    public void countDownStart() {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 1000);
                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat(
                            "yyyy-MM-dd");
// Please here set your event date//YYYY-MM-DD
                    Date futureDate = dateFormat.parse("2015-12-25");
                    Date currentDate = new Date();
                    if (!currentDate.after(futureDate)) {
                        long diff = futureDate.getTime()
                                - currentDate.getTime();
                        long days = diff / (24 * 60 * 60 * 1000);
                        diff -= days * (24 * 60 * 60 * 1000);
                        txtTimerDay.setText("" + String.format("%02d", days));
                    } else {
                        tvEvent.setVisibility(View.VISIBLE);
                        tvEvent.setText("The event started!");
                        textViewGone();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable, 1 * 1000);
    }
    public void textViewGone() {
        findViewById(R.id.textView).setVisibility(View.GONE);
       // findViewById(R.id.textView2).setVisibility(View.GONE);
    }
}

