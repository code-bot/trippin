package soundcloud.example.com.soundcloudapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.concurrent.ExecutionException;

public class MainActivity extends Activity implements AdapterView.OnItemSelectedListener {
    private String genre;
    public final static String CITY = "com.mycompany.myfirstapp.MESSAGE";
    public final static String GENRE = "com.mycompany.myfirstapp.MESSAGE2";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner spinner = (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.genres, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        genre = parent.getItemAtPosition(pos).toString();
    }

    public void onNothingSelected(AdapterView<?> parent) {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void goToMain(View view) {
        Intent intent = new Intent(this, MusicActivity.class);
        EditText latET = (EditText) findViewById(R.id.editText);
        EditText lonET = (EditText) findViewById(R.id.editText2);
        Double lat = Double.parseDouble(latET.getText().toString());
        Double lon = Double.parseDouble(lonET.getText().toString());
        String city = findClosestCity(lat, lon);
        intent.putExtra(GENRE, genre);
        intent.putExtra(CITY, city);
        startActivity(intent);
        finish();
    }


    public void goToMain2(View view) throws ExecutionException, InterruptedException {
        Intent intent = new Intent(this, MusicActivity.class);
        EditText vin = (EditText) findViewById(R.id.editText3);
        System.out.println(vin);
        Double[] loc = new GMAsync(vin.getText().toString()).execute().get();

        double lat = loc[0];
        double lon = loc[1];
        System.out.println(lat);
        System.out.println(lon);
        String city = findClosestCity(lat, lon);
        intent.putExtra(GENRE, genre);
        intent.putExtra(CITY, city);
        startActivity(intent);
        finish();
    }


    //FIND CLOSEST CITY
    private static double ToRadians(double degrees) {
        double radians = degrees * (Math.PI / 180);
        return radians;
    }

    // returns distance in meters as crow flies
    public static double DistanceBetween(double latOneDeg, double longOneDeg, double latTwoDeg, double longTwoDeg) {
        double radius = 6371000.0; // radius of Earth in meters
        double latOneRad = ToRadians(latOneDeg);
        double longOneRad = ToRadians(longOneDeg);
        double latTwoRad = ToRadians(latTwoDeg);
        double longTwoRad = ToRadians(longTwoDeg);
        double deltaLat = latTwoRad - latOneRad;
        double deltaLong = longTwoRad - longOneRad;
        double a = Math.pow(Math.sin(deltaLat / 2),2) + Math.cos(latOneRad) * Math.cos(latTwoRad) * Math.pow(Math.sin(deltaLong / 2),2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double distance = radius * c;
        return distance;
    }

    public static String findClosestCity(double latDeg, double longDeg) {
        String[] cityList = {"New York", "Atlanta", "Seattle", "Portland", "Denver", "Dallas", "Salt Lake City", "Philadelphia", "Washington DC", "Los Angeles"};
        double[] latitudeList = {40.7, 33.8, 47.6, 45.5, 39.7, 32.8, 40.8, 40.0, 39.0, 36.1};
        double[] longitudeList = {74.0, 84.4, 122.3, 122.7, 105.0, 97.8, 111.9, 75.1, 77.0, 115.2};
        if (cityList.length != latitudeList.length || latitudeList.length != longitudeList.length) {
            throw new RuntimeException("Application error: array sizes don't match");
        }
        double leastDistance = Double.POSITIVE_INFINITY;
        int arrayPos = -1;
        for (int i = 0; i < 10; i++) {
            double currentDifference = DistanceBetween(latDeg, longDeg, latitudeList[i], longitudeList[i]);
            if (currentDifference < leastDistance) {
                leastDistance = currentDifference;
                arrayPos = i;
            }
        }
        return cityList[arrayPos];
    }

}
