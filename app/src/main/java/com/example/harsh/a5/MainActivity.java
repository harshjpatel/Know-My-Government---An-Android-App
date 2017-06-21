package com.example.harsh.a5;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.DialogInterface;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Collections;
import android.net.NetworkInfo;
import java.util.HashMap;
import android.text.InputType;
import android.text.InputFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import android.text.util.Linkify;

import static com.example.harsh.a5.R.id.lat;
import static com.example.harsh.a5.R.id.lon;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    public  TextView title, time, description;
    private static final int B_REQ = 1;
    private static final int NEW = 1;
    private static final int EDIT = 2;
    private RecyclerView recyclerView;
    NetworkInfo activeNetworkInfo;
    public TextView cityname;
    OfficialActivity officialActivity;
    private LocationAdapter locationAdapter;
    private static ArrayList<Location> locationList = new ArrayList<>();
    private ArrayList<Location> locationList1 = new ArrayList<>();//Main Content save here
    final String[] et2 = {null};
    private Locator locator;
    private Location location;
    private String zipcode;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        final String TAG = "MainActivity";
        super.onCreate(savedInstanceState);
        int n = 20;
        setContentView(R.layout.activity_main);
        cityname = (TextView) findViewById(R.id.tv);
        recyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
        locationAdapter = new LocationAdapter(locationList, this);
        recyclerView.setAdapter(locationAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        if (isNetworkConnected()) {
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected())
            {
                if(locationList.size() == 0)
                {
                    Toast.makeText(this, "Remember to set GPS or open Maps if using Emulator", Toast.LENGTH_LONG).show();
                    locator = new Locator(this);
                }
            }
            else
            {
                showToast11();
                String nointernet = "No Data for Location";
                cityname.setText(nointernet);
            }
        }
        if(locationList.size() != 0)
        {
            cityname.setText(locationList.get(0).getLocationname());
        }
    }

    public void showToast() {
        Toast.makeText(this, "Refresh!!", Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "Net is connected!", Toast.LENGTH_SHORT).show();
    }

    public void showToast1() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("No Network Connection");
        builder.setMessage("Data cannot be updated without A Network Connection");
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void showToast11() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("No Network Connection");
        builder.setMessage("Data cannot be accessed/loaded"+"\n"+"without an internet connection.");
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void showToast2() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("No Data Foiund!");
        builder.setMessage("No saved Data Found! Sorry ");
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void showToast3() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Dulicate Stock");
        builder.setMessage("Stock Symbol is already displayed");
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void showToast4() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Symbol Not Found: " + et2[0]);
        builder.setMessage("Data for Stock Symbol");
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.action_menu, menu);
        menu.findItem(R.id.menuA).setIcon(drawableResourceId2);;
        menu.findItem(R.id.menuB).setIcon(drawableResourceId1);;
        return true;
    }
    //done menu options

    //LocatorClass

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        activeNetworkInfo = cm.getActiveNetworkInfo();
        return true;
    }

    private int drawableResourceId1 = R.drawable.location;
    private int drawableResourceId2 = R.drawable.help;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menuA) {
            Intent intent1 = new Intent(this, AboutActivity.class);
            this.startActivity(intent1);
            return true;
        }
        if (id == R.id.menuB) {
            if (isNetworkConnected())
            {
                if (activeNetworkInfo != null && activeNetworkInfo.isConnected())
                {
                    LayoutInflater inflater = LayoutInflater.from(this);
                    final View view = inflater.inflate(R.layout.entervalue, null);
                    //alertDilaogBox
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    Log.d(TAG, "Intent Started!!!!!!!!!!!!!!!!!!!!!!!!");
                    builder.setMessage("Enter City, State or Zip Code");
                    builder.setTitle("Enter Location:");
                    builder.setView(view);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int id) {
                            EditText et1 = (EditText) view.findViewById(R.id.textL);
                            if (et1.getText().length() != 0)
                            {
                                et2[0] = et1.getText().toString();
                                Log.d(TAG, "et2 : =" + et2[0]);
                                new LocationDownlioader(MainActivity.this).execute(et2[0]);
                            }
                        }
                    });
                    builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                else
                    {
                    showToast11();
                    String nointernet = "No Data for Location";
                    cityname.setText(nointernet);
                }
            }
            }
        return super.onOptionsItemSelected(item);
    }

    String name;

    public void addLocation(ArrayList<Location> backlist1)
    {
        name = backlist1.get(0).getCity1() + ", " + backlist1.get(0).getState1() + " " + backlist1.get(0).getZip1();
        Log.d(TAG, name);
        cityname.setText(name);
    }


    ArrayList<Location> newList = new ArrayList<>();

    public void addPosition(ArrayList<Location> position)
    {
        if(position.size() != 0)
        {
            cityname.setText(position.get(0).getLocationname());
        }
        locationList.removeAll(locationList);
        //Log.d(TAG, "StockList" + position.size());
        locationList.addAll(position);
        //Log.d(TAG, "StockList" + locationList.toString());
        newList = new ArrayList<>(locationList);
        //Log.d(TAG, "StockList" + newList.toString());
        locationAdapter.notifyDataSetChanged();
    }

    public void setData(double latitude, double longitude) {

        Log.d(TAG, "setData: Lat: " + latitude + ", Lon: " + longitude);
        String address = doAddress(latitude, longitude);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        Log.d(TAG, "onRequestPermissionsResult: CALL: " + permissions.length);
        Log.d(TAG, "onRequestPermissionsResult: PERM RESULT RECEIVED");

        if (requestCode == 5) {
            Log.d(TAG, "onRequestPermissionsResult: permissions.length: " + permissions.length);
            Log.d(TAG, "No permission Granted:");
            for (int i = 0; i < permissions.length; i++) {
                if (permissions[i].equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        Log.d(TAG, "onRequestPermissionsResult: HAS PERM");
                        locator.setUpLocationManager();
                        locator.determineLocation();
                    } else {
                        Toast.makeText(this, "Location permission was denied - cannot determine address", Toast.LENGTH_LONG).show();
                        Log.d(TAG, "onRequestPermissionsResult: NO PERM");
                    }
                }
            }
        }
        Log.d(TAG, "onRequestPermissionsResult: Exiting onRequestPermissionsResult");
    }

    private String doAddress(double latitude, double longitude) {
        Log.d(TAG, "doAddress: Lat: " + latitude + ", Lon: " + longitude);


        List<Address> addresses = null;
        for (int times = 0; times < 3; times++) {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            try {
                Log.d(TAG, "doAddress: Getting address now");


                addresses = geocoder.getFromLocation(latitude, longitude, 1);
                StringBuilder sb = new StringBuilder();

                for (Address ad : addresses) {
                    Log.d(TAG, "doLocation: " + ad);


                    sb.append("\nAddress\n\n");
                    for (int i = 0; i < ad.getMaxAddressLineIndex(); i++)
                        sb.append("\t" + ad.getAddressLine(i) + "\n");

                    sb.append("\t" + ad.getCountryName() + " (" + ad.getCountryCode() + ")\n");
                    String address = ad.getAddressLine(1);
                    Log.d(TAG, "CodeHarsh " + address);
                    String numberOnly = address.replaceAll("[^0-9]", "");
                    Log.d(TAG, "CodeHarsh " + numberOnly);
                    new LocationDownlioader(MainActivity.this).execute(numberOnly);

                }

                return sb.toString();
            } catch (IOException e) {
                Log.d(TAG, "doAddress: " + e.getMessage());

            }
            Toast.makeText(this, "GeoCoder service is slow - please wait", Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(this, "GeoCoder service timed out - please try again", Toast.LENGTH_LONG).show();
        return null;
    }

    public void noLocationAvailable() {
        Toast.makeText(this, "No location providers were available", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy()
    {
        if(locationList.size() == 0)
        {
            if(locator!=null) {
                locator.shutdown();
            }
        }
            //super.onDestroy();
        super.onDestroy();
    }


    @Override
    public void onClick(View v)
    {
        Log.d(TAG, "Working Viewwwwwwwwwwfffffffffffffff");
        int pos = recyclerView.getChildLayoutPosition(v);
        location = locationList.get(pos);
        zipcode = name;
        Intent intent = new Intent(MainActivity.this, OfficialActivity.class);
        intent.putExtra("myinfo", location);
        intent.putExtra("position", pos);
        intent.putExtra("name",name);
        startActivity(intent);
        return;
    }

    @Override
    public void onBackPressed()
    {
        Intent main = new Intent(Intent.ACTION_MAIN);
        main.addCategory(Intent.CATEGORY_HOME);
        main.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(main);
    }
}