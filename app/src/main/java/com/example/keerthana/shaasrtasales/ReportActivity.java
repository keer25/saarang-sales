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
import android.widget.Checkable;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.zip.Inflater;

import static android.R.attr.id;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static com.example.keerthana.shaasrtasales.MainActivity.BOOKLETS;
import static com.example.keerthana.shaasrtasales.R.id.check;
import static com.example.keerthana.shaasrtasales.SalesActivity.START;

public class ReportActivity extends AppCompatActivity {

    SharedPreferences preferences;
    ArrayList<String> strings;
    Set<String> inventory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getSharedPreferences("a", MODE_APPEND);
        inventory = preferences.getStringSet(MainActivity.BOOKLETS, null);
        strings = new ArrayList<>();
        String start = preferences.getString(START, null);
        Collections.addAll(strings, start.split(","));
        setContentView(R.layout.activity_report);
        HashMap<String, Integer> hashS = new HashMap<String, Integer>();
        HashMap<String, Integer> hashE = new HashMap<String, Integer>();
        HashMap<String, Integer> hashEE = new HashMap<>();
        for (String s : inventory) {
            String[] array = s.split(",");
            hashS.put(array[0], Integer.valueOf(array[1]));
            hashEE.put(array[0], Integer.valueOf(array[2]));
        }
        TextView report = (TextView) findViewById(R.id.reportText);
        int tickets = 0;
//        String display = "Booklets Left\n";
        Set<String> end = preferences.getStringSet(SalesActivity.END, null);
        Log.d("Sunshine", end.toString());
        Log.d("Sunshine", strings.toString());
        for (String s : end) {
            String[] array = s.split(",");
            hashE.put(array[0], Integer.valueOf(array[1]));
        }
        ArrayList<String> clone = new ArrayList<>();
        for (String s : strings) {
            tickets += hashE.get(s) - hashS.get(s) + 1;
            if (hashE.get(s).equals(hashEE.get(s))) {
                clone.add(s);
            }
        }

        Set<String> newInventory = new HashSet<>();
        for (String s : hashS.keySet()) {
            if (!clone.contains(s)) {
                Log.d("Sunshine", clone.toString());
                Log.d("Sunshine", strings.toString());
                Log.d("Sunshine", hashE.toString() + hashS.toString());
                int beg = strings.contains(s) ? hashE.get(s) + 1 : hashS.get(s);
                newInventory.add(s+","+String.valueOf(beg)+","+String.valueOf(hashEE.get(s)));
            }
        }

        //String[] start = preferences.getString(START, null).split(",");


//        if(!start[0].equals(end[0])) {
//            tickets += (Integer.valueOf(end[1]) - hashS.get(end[0]) + 1);
//            tickets += (hashE.get(start[0]) - Integer.valueOf(start[1]) + 1);
//        } else {
//            tickets += Integer.valueOf(end[1]) - Integer.valueOf(start[1]) + 1;
//        }
//        for (String s : hashE.keySet()) {
//            if (!strings.contains(s) &&
//                    !(s.equals(start[0]) && hashS.get(s).equals(Integer.valueOf(start[1]))) &&
//                    !(s.equals(end[0]) && hashE.get(s).equals(Integer.valueOf(end[1])))){
//                display += s + "\n";
//            }
//        }
        int ticketReserve = preferences.getInt("tickets", 0);
        tickets += ticketReserve;
        String display = "\nTickets Sold in this sale : " + String.valueOf(tickets - ticketReserve);
        display += "\nTotal Tickets Sold : " + String.valueOf(tickets);
        report.setText(display);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("tickets", tickets);
        if (newInventory.size() == 0) {
            editor.remove(BOOKLETS);
        }else {
            editor.putStringSet(BOOKLETS, newInventory);
        }
        editor.remove(START);
        editor.remove(SalesActivity.END);
        editor.apply();
        display = "Current Inventory\n" + displayInventory() + display;
        report.setText(display);
    }
//        strings = new ArrayList<String>();
//        setContentView(R.layout.select_booklets);
//        LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayout);
//        Button button = (Button) findViewById(R.id.report);
//        LayoutInflater inflater = LayoutInflater.from(this);
//        if (inventory != null) {
//            for (String s : inventory){
//                if (!(s.split(",")[0].equals(preferences.getString(SalesActivity.START, null).split(",")[0]) ||
//                        s.split(",")[0].equals(preferences.getString(SalesActivity.END, null).split(",")[0]))) {
//                    CheckBox check = (CheckBox) inflater.inflate(R.layout.checked_textview, null);
//                    layout.addView(check);
//                    String title = s.split(",")[0];
//                    check.setText(title);
//                }
//            }
//        }
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
//    }

    public String displayInventory() {
        HashSet<String> lets = (HashSet<String>) preferences.getStringSet(BOOKLETS, null);
        if (lets != null) {
            String display = "";
            for (String s : lets){
                display += s.split(",")[0] + " : " + s.split(",")[1] + " to " + s.split(",")[2]+"\n";
            }
            return display;
        } else {
            return "";
        }
    }

    public void home(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


}
