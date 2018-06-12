package com.example.navar.geocalculator;

import android.content.Intent;
import android.location.Location;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.math.RoundingMode;
import java.util.function.DoubleBinaryOperator;

public class MainActivity extends AppCompatActivity {
    private String currentDistance;
    private String currentBearing;

    private String distanceSetting = "miles";
    private String bearingSetting = "degrees";

    public static final int distanceSelection = 1;
    public static final int bearingSelection = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText latitude1 = (EditText) findViewById(R.id.latitude1);
        EditText latitude2 = (EditText) findViewById(R.id.latitude2);
        EditText longitude1 = (EditText) findViewById(R.id.longitude1);
        EditText longitude2 = (EditText) findViewById(R.id.longitude2);

        Button calculate = (Button) findViewById(R.id.calculate);
        Button clear = (Button) findViewById(R.id.clear);

        TextView distance = (TextView) findViewById(R.id.distance);
        TextView bearing = (TextView) findViewById(R.id.bearing);

        clear.setOnClickListener(v->{
            latitude1.setText("", EditText.BufferType.EDITABLE);
            latitude2.setText("", EditText.BufferType.EDITABLE);
            longitude1.setText("", EditText.BufferType.EDITABLE);
            longitude2.setText("", EditText.BufferType.EDITABLE);

            distance.setText("");
            bearing.setText("");
        });

        calculate.setOnClickListener(v->
        {
            if(checkFields())
            {
                double lat1 = Double.parseDouble(latitude1.getText().toString());
                double lat2 = Double.parseDouble(latitude2.getText().toString());
                double long1 = Double.parseDouble(longitude1.getText().toString());
                double long2 = Double.parseDouble(longitude2.getText().toString());
                Double dist = calcDistance(lat1,lat2,long1,long2);
                Double bear = calcBearing(lat1,long1,lat2,long2);

                distance.setText(dist + " " + distanceSetting);
                bearing.setText(bear + " " + bearingSetting);
            }

            else
            {
                Snackbar.make(latitude1, "Please fill out all fields", Snackbar.LENGTH_LONG).show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        TextView distance = (TextView) findViewById(R.id.distance);
        TextView bearing = (TextView) findViewById(R.id.bearing);

        if(resultCode == distanceSelection)
        {
            distanceSetting = data.getStringExtra("distance");
            bearingSetting = data.getStringExtra("bearing");
            changeSettings();
        }
    }

    public boolean checkFields()
    {
        EditText latitude1 = (EditText) findViewById(R.id.latitude1);
        EditText latitude2 = (EditText) findViewById(R.id.latitude2);
        EditText longitude1 = (EditText) findViewById(R.id.longitude1);
        EditText longitude2 = (EditText) findViewById(R.id.longitude2);

        Button calculate = (Button) findViewById(R.id.calculate);
        Button clear = (Button) findViewById(R.id.clear);

        TextView distance = (TextView) findViewById(R.id.distance);
        TextView bearing = (TextView) findViewById(R.id.bearing);

        if(latitude1.getText().length() == 0 || latitude2.getText().length() == 0 || longitude1.getText().length() == 0 || longitude2.getText().length() == 0)
        {
            return false;
        }

        return true;
    }

    public Double calcDistance(double la1, double la2, double lo1, double lo2)
    {
        Location loc1 = new Location("");
        loc1.setLatitude(la1);
        loc1.setLongitude(lo1);

        Location loc2 = new Location("");
        loc2.setLatitude(la2);
        loc2.setLongitude(lo2);

        float distanceInMeters = loc1.distanceTo(loc2);


        double finalDistance;
        if(distanceSetting == "miles")
        {
            finalDistance = round(distanceInMeters/1609.34,2);
        }
        else
        {
            finalDistance = round(distanceInMeters/1000,2);
        }

        return finalDistance;
    }

    protected double calcBearing(double lat1, double lon1, double lat2, double lon2){
        double longitude1 = lon1;
        double longitude2 = lon2;
        double latitude1 = Math.toRadians(lat1);
        double latitude2 = Math.toRadians(lat2);
        double longDiff= Math.toRadians(longitude2-longitude1);
        double y= Math.sin(longDiff)*Math.cos(latitude2);
        double x=Math.cos(latitude1)*Math.sin(latitude2)-Math.sin(latitude1)*Math.cos(latitude2)*Math.cos(longDiff);

        double finalBearing = round((Math.toDegrees(Math.atan2(y, x))+360)%360,2);
        if(bearingSetting == "degrees")
        {
            return finalBearing;
        }
        else
            {
                return round(Math.toRadians(finalBearing), 2);
        }

        }


    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public void changeSettings()
    {
        EditText latitude1 = (EditText) findViewById(R.id.latitude1);
        EditText latitude2 = (EditText) findViewById(R.id.latitude2);
        EditText longitude1 = (EditText) findViewById(R.id.longitude1);
        EditText longitude2 = (EditText) findViewById(R.id.longitude2);

        Button calculate = (Button) findViewById(R.id.calculate);
        Button clear = (Button) findViewById(R.id.clear);

        TextView distance = (TextView) findViewById(R.id.distance);
        TextView bearing = (TextView) findViewById(R.id.bearing);

        if(checkFields())
        {


            double lat1 = Double.parseDouble(latitude1.getText().toString());
            double lat2 = Double.parseDouble(latitude2.getText().toString());
            double long1 = Double.parseDouble(longitude1.getText().toString());
            double long2 = Double.parseDouble(longitude2.getText().toString());
            Double dist = calcDistance(lat1,lat2,long1,long2);
            Double bear = calcBearing(lat1,long1,lat2,long2);

            distance.setText(dist + " "+ distanceSetting);
            bearing.setText(bear + " " + bearingSetting);
        }

        else
        {
            Snackbar.make(latitude1, "Please fill out all fields", Snackbar.LENGTH_LONG).show();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        Intent intent = new Intent(MainActivity.this, Settings.class);
        //startActivityForResult(intent, distanceSelection);
        startActivityForResult(intent, bearingSelection);

        return true;
    }

}
