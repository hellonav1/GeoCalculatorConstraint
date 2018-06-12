package com.example.navar.geocalculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.lang.reflect.Array;

public class Settings extends AppCompatActivity {

    private String diSelection = "Miles";
    private String beSelection = "Degrees";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("distance", diSelection);
                intent.putExtra("bearing", beSelection);

                setResult(MainActivity.distanceSelection, intent);
                finish();
            }
        });

        Spinner dSpinner = (Spinner) findViewById(R.id.distanceSpinner);
        Spinner bSpinner = (Spinner) findViewById(R.id.bearingSpinner);

        ArrayAdapter<CharSequence> dAdapter = ArrayAdapter.createFromResource(this, R.array.distanceUnits,
                android.R.layout.simple_spinner_item);

        ArrayAdapter<CharSequence> bAdapter = ArrayAdapter.createFromResource(this, R.array.bearingUnits,
                android.R.layout.simple_spinner_item);

        dAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        dSpinner.setAdapter(dAdapter);
        bSpinner.setAdapter(bAdapter);

        dSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                diSelection = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        bSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                beSelection = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

}
