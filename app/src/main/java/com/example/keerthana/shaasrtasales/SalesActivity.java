package com.example.keerthana.shaasrtasales;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static com.example.keerthana.shaasrtasales.R.id.booklet;
import static com.example.keerthana.shaasrtasales.R.id.textView;
import static com.example.keerthana.shaasrtasales.R.id.ticket;

public class SalesActivity extends AppCompatActivity {

    SharedPreferences preferences;
    public static String START = "start";
    public static String END = "end";
    ArrayList<String> strings;
    Set<String> inventory;
    TextView textView;
    EditText booklet;
    EditText ticket;
    Button button;
    ArrayList<String> clone = null;
    private Set<String> bookletdata = new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //textView = (TextView) findViewById(R.id.salesText);
        //booklet = (EditText) findViewById(R.id.bookletSales);
        //ticket = (EditText) findViewById(R.id.ticket);
        //button = (Button) findViewById(R.id.salesButton);
        preferences = getSharedPreferences("a", MODE_APPEND);
        strings = new ArrayList<String>();
        String start = preferences.getString(START, null);
        inventory = preferences.getStringSet(MainActivity.BOOKLETS, null);
        if (start == null) {
            setContentView(R.layout.select_booklets);
            LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayout);
            Button button = (Button) findViewById(R.id.report);
            LayoutInflater inflater = LayoutInflater.from(this);
            if (inventory != null) {
                for (String s : inventory){
                        CheckBox check = (CheckBox) inflater.inflate(R.layout.checked_textview, null);
                        layout.addView(check);
                        String title = s.split(",")[0];
                        check.setText(title);
                }
            }
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferences.Editor editor = preferences.edit();
                    String data = "";
                    for (int i=0; i<strings.size(); i++){
                        data += strings.get(i);
                        if (i != strings.size() - 1) {
                            data += ",";
                        }
                    }
                    clone = (ArrayList<String>) strings.clone();
                    editor.putString(START, data);
                    editor.apply();
                    doRest();
                }
                });

//            textView.setText("Enter the data for the Sales");
//            button.setText("Start");
//            button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    SharedPreferences.Editor editor = preferences.edit();
//                    editor.putString(START, booklet.getText().toString() + "," +
//                            ticket.getText().toString());
//                    editor.apply();
//                    doRest(booklet.getText().toString(), ticket.getText().toString());
//                }
//            });
        }else {
            Collections.addAll(strings, start.split(","));
            clone = (ArrayList<String>) strings.clone();
            doRest();
        }
    }

    private void doRest(){
        setContentView(R.layout.end_sale);
        Button button = (Button) findViewById(R.id.addbutton);
        TextView booklet = (TextView) findViewById(R.id.booklet);
        Log.d("Sunshine", String.valueOf(clone.size()));
        booklet.setText(clone.get(0));
        //Set<String> bookletdata = new HashSet<>();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    TextView booklet = (TextView) findViewById(R.id.booklet);
                    EditText start = (EditText) findViewById(R.id.start);
                    Log.d("Sunshine", clone.toString());
                bookletdata.add(booklet.getText().toString() + "," +
                        start.getText().toString());
                String display = booklet.getText().toString() + " : " + start.getText().toString();
                TextView textView = (TextView) findViewById(R.id.add);
                display = textView.getText().toString() + "\n" + display;
                textView.setText(display);
                 clone.remove(0);

                    //booklet.setText("");
                    start.setText("");
                if (clone.isEmpty()){
                    gotoNext();
                }else {
                    booklet.setText(clone.get(0));
                }

            }
        });

//        Button done = (Button) findViewById(R.id.done);
//        done.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                gotoNext();
//            }
//        });


    }
//        textView.setText("Start Data = " + boo +  " : " + tic
//                 + "\n" +
//                "Enter data and generate report");
//        booklet.setText(""); ticket.setText("");
//        button.setText("Generate Report");
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                SharedPreferences.Editor editor = preferences.edit();
//                editor.putString(END, booklet.getText().toString() + "," +
//                        ticket.getText().toString());
//                editor.apply();
//                Intent intent = new Intent(getApplicationContext(), ReportActivity.class);
//                startActivity(intent);
//            }
//        });

    public void gotoNext() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putStringSet(END, bookletdata);
        editor.apply();
        Intent intent = new Intent(getBaseContext(), ReportActivity.class);
        startActivity(intent);
    }

    public void click(View view) {
        //Toast.makeText(getApplicationContext(), "yo", Toast.LENGTH_SHORT);
        if (((CheckBox) view).isChecked()) {
            ((CheckBox) view).setChecked(true);
            strings.add(((CheckBox) view).getText().toString());
        }else {
            ((CheckBox) view).setChecked(false);
            strings.remove(((CheckBox) view).getText().toString());
        }
    }
}
