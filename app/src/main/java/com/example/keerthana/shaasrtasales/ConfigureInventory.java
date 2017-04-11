package com.example.keerthana.shaasrtasales;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashSet;
import java.util.Set;

import static com.example.keerthana.shaasrtasales.MainActivity.BOOKLETS;

public class ConfigureInventory extends AppCompatActivity {

    private SharedPreferences preferences = null;
    private Set<String> bookletdata = new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getSharedPreferences("a", MODE_APPEND);
        Set<String> inventory = preferences.getStringSet(BOOKLETS, null);
        if (inventory != null) {
            bookletdata = inventory;
        }
        setContentView(R.layout.configure_inventory);
        Button button = (Button) findViewById(R.id.addbutton);
        //Set<String> bookletdata = new HashSet<>();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText booklet = (EditText) findViewById(R.id.booklet);
                EditText start = (EditText) findViewById(R.id.start);
                EditText end = (EditText) findViewById(R.id.end);

                bookletdata.add(booklet.getText().toString()+ "," +
                        start.getText().toString()+ "," +
                        end.getText().toString());
                String display = booklet.getText().toString()+ " : " + start.getText().toString() + " to " +
                        end.getText().toString();
                TextView textView = (TextView) findViewById(R.id.add);
                display = textView.getText().toString() + "\n" + display;
                textView.setText(display);
                booklet.setText(""); start.setText(""); end.setText("");
            }
        });

        Button done = (Button) findViewById(R.id.done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putStringSet(BOOKLETS, bookletdata);
                editor.apply();
                Intent intent = new Intent(getBaseContext(), SalesActivity.class);
                startActivity(intent);
            }
        });
    }
}
