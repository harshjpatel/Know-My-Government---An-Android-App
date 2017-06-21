package com.example.harsh.a5;

/**
 * Created by harsh on 4/12/17.
 */
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog.*;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.JsonReader;
import android.content.DialogInterface;
import android.util.JsonWriter;
import android.util.Log;
import android.view.Menu;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;
import android.content.Intent;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.ScrollingMovementMethod;
import android.util.JsonReader;
import android.util.JsonWriter;
import android.util.Log;
import android.view.Gravity;
import org.json.*;
import java.util.ArrayList;
import java.util.Iterator;
import android.view.Menu;
import java.util.Random;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;
import java.util.ArrayList;
import java.util.List;
import android.text.util.Linkify;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class PhotodetailActivity extends AppCompatActivity
{
    final String TAG = "PhotodetailActivity";
    public TextView ptype, pname, zipcode;
    public Location location, location1;
    private int position;
    private ImageView imageView;
    public List<Location> locationlist = new ArrayList<>();
    private ConstraintLayout CL1, CL2;
    NetworkInfo activeNetworkInfo;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photodetailactivity);
        zipcode = (TextView) findViewById(R.id.tv);
        ptype = (TextView) findViewById(R.id.positiontype);
        pname = (TextView) findViewById(R.id.PositionName);
        imageView = (ImageView) findViewById(R.id.positionphoto);
        Intent intent = getIntent();
        location1 = (Location) intent.getSerializableExtra("myinfo1");
    }

    @Override
    public void onBackPressed()
    {
        //Log.d(TAG, "Working Viewwwwwwwwwwfffffffffffffff");
        Intent intent = new Intent(PhotodetailActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        return;
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        activeNetworkInfo = cm.getActiveNetworkInfo();
        return true;
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        Log.d(TAG, "********************************************");
        Intent intent1 = getIntent();
        if (intent1.hasExtra("myinfo1"))
        {
            Log.d(TAG , location1.toString());
            Log.d(TAG , location1.getPositiontype().toString());
            Log.d(TAG, location1.getPositionname().toString());
            Log.d(TAG, location1.getPhoto().toString());
            Log.d(TAG, location1.getPartytype().toString());
            Log.d(TAG, "********************************************");
            location1 = (Location) intent1.getSerializableExtra("myinfo1");
            zipcode.setText(location1.getLocationname());
            if (location1.getPartytype().equals("Republican"))
            {
                ptype.setText(location1.getPositiontype().toString());
                pname.setText(location1.getPositionname().toString());
                final String imageurl = location1.getPhoto().toString();
                Log.d("ImageUrl:", location1.getPhoto().toString());
                if (isNetworkConnected())
                {
                    if (location1.getPhoto() != null) {
                        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                            final String imageurl1 = location1.getPhoto();
                            Log.d("ImageUrl:", location1.getPhoto().toString());
                            Log.d("1", "1");

                            Picasso picasso = new Picasso.Builder(this).listener(new Picasso.Listener() {
                                @Override
                                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) { // Here we try https if the http image attempt failed
                                    final String changedUrl = imageurl1.replace("http:", "https:");
                                    picasso.load(changedUrl).error(R.drawable.brokenimage)
                                            .placeholder(R.drawable.placeholder).into(imageView);
                                }
                            }).build();
                            picasso.load(imageurl1).error(R.drawable.brokenimage)
                                    .placeholder(R.drawable.placeholder).into(imageView);

                        } else {
                            Picasso.with(this).load(R.drawable.placeholder).into(imageView);

                        }
                    } else {
                        Picasso.with(this).load(R.drawable.missingimage).into(imageView);
                    }
                }
                CL1 = (ConstraintLayout) findViewById(R.id.CL);
                CL1.setBackgroundColor(getResources().getColor(R.color.red));
                CL2 = (ConstraintLayout) findViewById(R.id.ConstraintL);
                CL2.setBackgroundColor(getResources().getColor(R.color.red));
            }
            else if (location1.getPartytype().equals("Democratic") || location1.getPartytype().equals("Democrat"))
            {
                ptype.setText(location1.getPositiontype().toString());
                pname.setText(location1.getPositionname().toString());
                final String imageurl = location1.getPhoto().toString();
                Log.d("ImageUrl:", location1.getPhoto().toString());
                if (isNetworkConnected())
                {
                    if (location1.getPhoto() != null) {
                        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                            final String imageurl1 = location1.getPhoto();
                            Log.d("ImageUrl:", location1.getPhoto().toString());
                            Log.d("1", "1");

                            Picasso picasso = new Picasso.Builder(this).listener(new Picasso.Listener() {
                                @Override
                                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) { // Here we try https if the http image attempt failed
                                    final String changedUrl = imageurl1.replace("http:", "https:");
                                    picasso.load(changedUrl).error(R.drawable.brokenimage)
                                            .placeholder(R.drawable.placeholder).into(imageView);
                                }
                            }).build();
                            picasso.load(imageurl1).error(R.drawable.brokenimage)
                                    .placeholder(R.drawable.placeholder).into(imageView);

                        } else {
                            Picasso.with(this).load(R.drawable.placeholder).into(imageView);

                        }
                    } else {
                        Picasso.with(this).load(R.drawable.missingimage).into(imageView);
                    }
                }
                CL1 = (ConstraintLayout) findViewById(R.id.CL);
                CL1.setBackgroundColor(getResources().getColor(R.color.blue));
                CL2 = (ConstraintLayout) findViewById(R.id.ConstraintL);
                CL2.setBackgroundColor(getResources().getColor(R.color.blue));
            }
            else
                {
                ptype.setText(location1.getPositiontype().toString());
                pname.setText(location1.getPositionname().toString());
                    final String imageurl = location1.getPhoto().toString();
                    Log.d("ImageUrl:", location1.getPhoto().toString());
                    if (isNetworkConnected())
                    {
                        if (location1.getPhoto() != null) {
                            if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                                final String imageurl1 = location1.getPhoto();
                                Log.d("ImageUrl:", location1.getPhoto().toString());
                                Log.d("1", "1");

                                Picasso picasso = new Picasso.Builder(this).listener(new Picasso.Listener() {
                                    @Override
                                    public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) { // Here we try https if the http image attempt failed
                                        final String changedUrl = imageurl1.replace("http:", "https:");
                                        picasso.load(changedUrl).error(R.drawable.brokenimage)
                                                .placeholder(R.drawable.placeholder).into(imageView);
                                    }
                                }).build();
                                picasso.load(imageurl1).error(R.drawable.brokenimage)
                                        .placeholder(R.drawable.placeholder).into(imageView);

                            } else {
                                Picasso.with(this).load(R.drawable.placeholder).into(imageView);

                            }
                        } else {
                            Picasso.with(this).load(R.drawable.missingimage).into(imageView);
                        }
                    }
                    CL1 = (ConstraintLayout) findViewById(R.id.CL);
                    CL1.setBackgroundColor(getResources().getColor(R.color.black));
                    CL2 = (ConstraintLayout) findViewById(R.id.ConstraintL);
                    CL2.setBackgroundColor(getResources().getColor(R.color.black));
            }
        }
    }

}
