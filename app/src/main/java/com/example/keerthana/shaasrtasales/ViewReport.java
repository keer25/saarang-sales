package com.example.keerthana.shaasrtasales;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ViewReport extends AppCompatActivity {

    private SharedPreferences preferences = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_report);
        preferences = getSharedPreferences("a", MODE_APPEND);
        String display = String.valueOf(preferences.getInt("tickets", 0)) + " tickets sold so far.";
        TextView textView = (TextView) findViewById(R.id.report);
        textView.setText(display);
    }
}
