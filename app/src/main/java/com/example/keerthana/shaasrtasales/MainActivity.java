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

import static android.content.Context.MODE_APPEND;

public class MainActivity extends AppCompatActivity {

    public static String BOOKLETS = "booklets";
    private SharedPreferences preferences = null;
    private Set<String> bookletdata = new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.configure_prompt);

        //Restore Inventory
        preferences = getSharedPreferences("a", MODE_APPEND);
        Set<String> inventory = preferences.getStringSet(BOOKLETS, null);
        if (inventory == null) {
            TextView textView = (TextView) findViewById(R.id.textView);
            Button button = (Button) findViewById(R.id.startSales);
            button.setVisibility(View.GONE);
            textView.setText("You don't seem to have added an inventory. Configure your inventory to start with the Sales.");
        }else {
            bookletdata = inventory;
            Button button = (Button) findViewById(R.id.startSales);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), SalesActivity.class);
                    startActivity(intent);
                }
            });
        }


    }

    //OnCLick of the Configure Inventory Button
    public void configureInventory(View view) {
        //setContentView(R.layout.configure_inventory);
        Intent intent = new Intent(this, ConfigureInventory.class);
        startActivity(intent);
    }

    public void viewInventory(View view) {
        Intent intent = new Intent(this, InventoryActivity.class);
        startActivity(intent);
    }

    public void viewReport(View view) {
        Intent intent = new Intent(this, ViewReport.class);
        startActivity(intent);
    }
}
