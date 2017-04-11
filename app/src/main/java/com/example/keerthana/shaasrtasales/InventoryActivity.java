package com.example.keerthana.shaasrtasales;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.HashSet;

import static com.example.keerthana.shaasrtasales.MainActivity.BOOKLETS;

public class InventoryActivity extends AppCompatActivity {

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        preferences = getSharedPreferences("a", MODE_APPEND);
        TextView textView = (TextView) findViewById(R.id.viewinventory);
        textView.setText(displayInventory());
    }

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
}
