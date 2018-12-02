package com.trainingstracker.trainingstracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private EditText uebungsField;
    private EditText kgField;
    private Button btn;
    private Button startButton;
    private Button plusButton;
    private Button minusButton;
    private ListView itemsList;

    private ArrayList<String> items;
    private ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        uebungsField = findViewById(R.id.uebungsField);
        kgField = findViewById(R.id.kgField);
        btn = findViewById(R.id.addButton);
        itemsList = findViewById(R.id.itemList);
        startButton = findViewById(R.id.startButton);
        plusButton = findViewById(R.id.plusButton);
        minusButton = findViewById(R.id.minusButton);

        items = FileHelper.readData(this);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        itemsList.setAdapter(adapter);

        btn.setOnClickListener(this);
        startButton.setOnClickListener(this);
        plusButton.setOnClickListener(this);
        minusButton.setOnClickListener(this);
        itemsList.setOnItemClickListener(this);
        itemsList.setOnItemLongClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addButton:
                String itemEntered = (uebungsField.getText() + "                    /                    " + kgField.getText());
                adapter.insert(itemEntered, 1);
                uebungsField.setText("");
                kgField.setText("");
                FileHelper.writeData(items, this);
                Toast.makeText(this, "Item Added", Toast.LENGTH_SHORT).show();
                break;
            case R.id.startButton:
                DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
                Date date = new Date();
                String startToken = "___ " + dateFormat.format(date) + " ___";
                adapter.insert(startToken, 0);
                break;
            case R.id.plusButton:
                String strKgP = kgField.getText().toString();
                if(strKgP.isEmpty()){
                    kgField.setText("0.5");
                }
                strKgP = kgField.getText().toString();
                double doubKgP = Double.parseDouble(strKgP) +0.5;
                strKgP = Double.toString(doubKgP);
                kgField.setText(strKgP);
                break;
            case R.id.minusButton:
                String strKgM = kgField.getText().toString();
                if(strKgM.isEmpty()){
                    kgField.setText("1.5");
                }
                strKgM = kgField.getText().toString();
                double doubKgM = Double.parseDouble(strKgM) -0.5;
                if (doubKgM > 0) {
                    strKgP = Double.toString(doubKgM);
                    kgField.setText(strKgP);
                }


                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String test = "/";
        String string = items.get(position);
        if (string.contains(test)) {
            String[] parts = string.split("/");
            String part1 = parts[0];
            String part2 = parts[1];
            uebungsField.setText("");
            uebungsField.setText(part1.trim());

            kgField.setText("");
            kgField.setText(part2.trim());
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        items.remove(position);
        adapter.notifyDataSetChanged();
        FileHelper.writeData(items, this);
        Toast.makeText(this, "Delete", Toast.LENGTH_SHORT).show();
        return true;
    }
}


