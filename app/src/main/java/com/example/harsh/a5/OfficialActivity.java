package com.example.harsh.a5;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * Created by harsh on 4/10/17.
 */

public class OfficialActivity extends AppCompatActivity {
    final String TAG = "OfficialActivithy";
    public TextView ptype, pname, party, addresstext, phonetext, emailtext, websitetext, zipcode;
    public Location location, location1;
    private int position;
    ImageView imageView, youtube, googleplus, facebook, twitter;
    public List<Location> locationlist = new ArrayList<>();
    public List<Location> locationlist1 = new ArrayList<>();
    private ConstraintLayout CL1, CL2;
    NetworkInfo activeNetworkInfo;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.officialactivity);
        zipcode = (TextView) findViewById(R.id.tv);
        ptype = (TextView) findViewById(R.id.positiontype);
        pname = (TextView) findViewById(R.id.PositionName);
        party = (TextView) findViewById(R.id.PartyType);
        imageView = (ImageView) findViewById(R.id.positionphoto);
        addresstext = (TextView) findViewById(R.id.AddressText);
        phonetext = (TextView) findViewById(R.id.phoneNumber);
        emailtext = (TextView) findViewById(R.id.EmailText);
        websitetext = (TextView) findViewById(R.id.WebsiteText);
        youtube = (ImageView) findViewById(R.id.youtube);
        googleplus = (ImageView) findViewById(R.id.googleplus);
        facebook = (ImageView) findViewById(R.id.facebook);
        twitter = (ImageView) findViewById(R.id.twitter);
        Intent intent = getIntent();
        location = (Location) intent.getSerializableExtra("myinfo");
        //location1 = (Location) intent.getSerializableExtra("myinfo1");
        position = intent.getIntExtra("position", 0);
        showIcon();
    }

    private void showIcon() {
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        activeNetworkInfo = cm.getActiveNetworkInfo();
        return true;
    }


    public void addLocation(ArrayList<Location> backlist) {
        String name = backlist.get(0).getCity1() + ", " + backlist.get(0).getState1() + " " + backlist.get(0).getZip1();
        Log.d(TAG, name);
        zipcode.setText(name);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "********************************************");
        Intent intent1 = getIntent();
        if (intent1.hasExtra("myinfo"))
        {
            location = (Location) intent1.getSerializableExtra("myinfo");
            Log.d("ImageUrl:", "1");
            Log.d(TAG, location.getPartytype());

            if (location.getPartytype().equals("Republican"))
            {
                Log.d("Check", String.valueOf(location.getPartytype() == "Republican"));
                CL1 = (ConstraintLayout) findViewById(R.id.CL);
                CL1.setBackgroundColor(getResources().getColor(R.color.red));
                CL2 = (ConstraintLayout) findViewById(R.id.ConstraintL);
                CL2.setBackgroundColor(getResources().getColor(R.color.red));
                ptype.setText(location.getPositiontype().toString());
                pname.setText(location.getPositionname().toString());

                {
                    if(!location.getPartytype().equals("Unknown"))
                    {
                        party.setText("(" + location.getPartytype().toString() + ")");
                    }
                }

                if (location.getPhoto() != null)
                {
                    final String imageurl = location.getPhoto();
                    Log.d("ImageUrl:", location.getPhoto().toString());
                }
                //Picasso.with(this)
                //      .load(imageurl)
                //    .into(imageView);
                if (isNetworkConnected())
                {
                    if (location.getPhoto() != null) {
                        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                            final String imageurl = location.getPhoto();
                            Log.d("ImageUrl:", location.getPhoto().toString());
                            Log.d("1", "1");

                            Picasso picasso = new Picasso.Builder(this).listener(new Picasso.Listener() {
                                @Override
                                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) { // Here we try https if the http image attempt failed
                                    final String changedUrl = imageurl.replace("http:", "https:");
                                    picasso.load(changedUrl).error(R.drawable.brokenimage)
                                            .placeholder(R.drawable.placeholder).into(imageView);
                                }
                            }).build();
                            picasso.load(imageurl).error(R.drawable.brokenimage)
                                    .placeholder(R.drawable.placeholder).into(imageView);
                            imageView.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(OfficialActivity.this, PhotodetailActivity.class);
                                    intent.putExtra("myinfo1", location);
                                    startActivity(intent);
                                }
                            });
                        } else {
                            Picasso.with(this).load(R.drawable.placeholder).into(imageView);
                            imageView.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(OfficialActivity.this, PhotodetailActivity.class);
                                    intent.putExtra("myinfo1", location);
                                    startActivity(intent);
                                }
                            });
                        }
                    } else {
                        Picasso.with(this).load(R.drawable.missingimage).into(imageView);
                    }
                }
                zipcode.setText(location.getLocationname());

                if (location.getAddress1() != null && location.getAddress2() != null && location.getAddress3() != null && location.getAddress4() != null && location.getAddress5() != null && location.getAddress6() == null) {
                    addresstext.setText(location.getAddress1().toString() + ",\n" + location.getAddress2().toString() + ",\n" + location.getAddress3().toString() + "," + location.getAddress4().toString() + " " + location.getAddress5().toString());
                    addresstext.setLinkTextColor(Color.WHITE);
                    Linkify.addLinks(((TextView) findViewById(R.id.AddressText)), Linkify.MAP_ADDRESSES);
                } else if (location.getAddress2() == null && location.getAddress1() != null && location.getAddress3() != null && location.getAddress4() != null && location.getAddress5() != null)
                {
                    String address = location.getAddress1().toString() + ",\n" + location.getAddress3().toString() + "," + location.getAddress4().toString() + " " + location.getAddress5().toString();
                    Log.d(TAG, address);
                    addresstext.setText(address);
                    addresstext.setLinkTextColor(Color.WHITE);
                    Linkify.addLinks(((TextView) findViewById(R.id.AddressText)), Linkify.MAP_ADDRESSES);
                } else if (location.getAddress2() == null && location.getAddress1() == null && location.getAddress3() == null && location.getAddress4() == null && location.getAddress5() == null) {
                    addresstext.setText("No Data Provided");
                    addresstext.setLinkTextColor(Color.WHITE);
                }
                else if (location.getAddress2() == null && location.getAddress1() == null && location.getAddress3() == null && location.getAddress4() == null && location.getAddress5() == null) {
                    addresstext.setText("No Data Provided");
                    addresstext.setLinkTextColor(Color.WHITE);
                }
                else if (location.getAddress1() != null && location.getAddress2() != null && location.getAddress3() != null && location.getAddress4() != null && location.getAddress5() != null && location.getAddress6() != null)
                {
                    addresstext.setText(location.getAddress1().toString() + ",\n" + location.getAddress6().toString() + ",\n" +location.getAddress2().toString() + ",\n" + location.getAddress3().toString() + "," + location.getAddress4().toString() + " " + location.getAddress5().toString());
                    addresstext.setLinkTextColor(Color.WHITE);
                    Linkify.addLinks(((TextView) findViewById(R.id.AddressText)), Linkify.MAP_ADDRESSES);
                }
                else
                    addresstext.setText("No Data Provided");
                addresstext.setLinkTextColor(Color.WHITE);

                {
                    if (location.getPhone() != null)
                    {
                        phonetext.setText(location.getPhone().toString());
                        phonetext.setLinkTextColor(Color.WHITE);
                        Linkify.addLinks(((TextView) findViewById(R.id.phoneNumber)), Linkify.PHONE_NUMBERS);
                    } else
                        phonetext.setText("No Data Provided");
                    phonetext.setLinkTextColor(Color.WHITE);
                }

                {
                    if (location.getEmail() != null)
                    {
                        emailtext.setText(location.getEmail().toString());
                        emailtext.setLinkTextColor(Color.WHITE);
                        Linkify.addLinks(((TextView) findViewById(R.id.EmailText)), Linkify.EMAIL_ADDRESSES);
                    } else
                        emailtext.setText("No Data Provided");
                    emailtext.setLinkTextColor(Color.WHITE);
                }

                {
                    if (location.getWebsite() != null)
                    {
                        websitetext.setText(location.getWebsite().toString());
                        websitetext.setLinkTextColor(Color.WHITE);
                        Linkify.addLinks(((TextView) findViewById(R.id.WebsiteText)), Linkify.WEB_URLS);
                    }
                    else
                        websitetext.setText("No Data Provided");
                    websitetext.setLinkTextColor(Color.WHITE);
                }

                if (location.getFacebook() != null) {
                    Log.d("FB", location.getFacebook());
                }
                if (location.getYoutube() != null) {
                    Log.d("YT", location.getYoutube());
                }
                if (location.getTwitter() != null) {
                    Log.d("TW", location.getTwitter());
                }
                if (location.getGooglePlus() != null) {
                    Log.d("G+", location.getGooglePlus());
                }

                {
                    if (location.getFacebook() != null) {
                        facebook.setVisibility(View.VISIBLE);
                        facebook.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String FACEBOOK_URL = "https://www.facebook.com/" + location.getFacebook();
                                Log.d(TAG, FACEBOOK_URL);
                                String urlToUse;
                                PackageManager packageManager = getPackageManager();
                                try {
                                    int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
                                    if (versionCode >= 3002850) { //newer versions of fb app
                                        urlToUse = "fb://facewebmodal/f?href=" + FACEBOOK_URL;
                                        Log.d(TAG + "1", urlToUse);
                                    } else { //older versions of fb app
                                        urlToUse = "fb://page/" + location.getFacebook();
                                        Log.d(TAG + "2", urlToUse);
                                    }
                                } catch (PackageManager.NameNotFoundException e) {
                                    urlToUse = FACEBOOK_URL; //normal web url
                                }
                                Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
                                facebookIntent.setData(Uri.parse(urlToUse));
                                startActivity(facebookIntent);
                            }
                        });
                    } else
                        facebook.setVisibility(View.INVISIBLE);
                }

                {
                    if (location.getTwitter() != null) {
                        twitter.setVisibility(View.VISIBLE);
                        twitter.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = null;
                                String name = location.getTwitter();
                                try {
                                    // get the Twitter app if possible
                                    getPackageManager().getPackageInfo("com.twitter.android", 0);
                                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=" + name));
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                } catch (Exception e) {
                                    // no Twitter app, revert to browser
                                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/" + name));
                                }
                                startActivity(intent);
                            }
                        });
                    } else
                        twitter.setVisibility(View.INVISIBLE);
                }

                {
                    if (location.getGooglePlus() != null) {
                        googleplus.setVisibility(View.VISIBLE);
                        googleplus.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                {
                                    String name = location.getGooglePlus();
                                    Intent intent = null;
                                    try {
                                        intent = new Intent(Intent.ACTION_VIEW);
                                        intent.setClassName("com.google.android.apps.plus", "com.google.android.apps.plus.phone.UrlGatewayActivity");
                                        intent.putExtra("customAppUri", name);
                                        startActivity(intent);
                                    } catch (ActivityNotFoundException e) {
                                        startActivity(new Intent(Intent.ACTION_VIEW,
                                                Uri.parse("https://plus.google.com/" + name)));
                                    }
                                }
                            }
                        });
                    } else
                        googleplus.setVisibility(View.INVISIBLE);
                    }
                    {
                    if (location.getYoutube() != null) {
                        Log.d(TAG, "youtube visible");
                        youtube.setVisibility(View.VISIBLE);
                        youtube.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                {
                                    String name = location.getYoutube();
                                    Intent intent = null;
                                    try {
                                        intent = new Intent(Intent.ACTION_VIEW);
                                        intent.setPackage("com.google.android.youtube");
                                        intent.setData(Uri.parse("https://www.youtube.com/" + name));
                                        startActivity(intent);
                                    } catch (ActivityNotFoundException e) {
                                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/" + name)));
                                    }
                                }

                            }
                        });
                    }
                    else
                        youtube.setVisibility(View.INVISIBLE);
                }
            }
            //Democratic
            else if(location.getPartytype().equals("Democratic") || location.getPartytype().equals("Democrat"))
            {
                Log.d(TAG, "Democratic");
                ptype.setText(location.getPositiontype().toString());
                pname.setText(location.getPositionname().toString());
                {
                    if(!location.getPartytype().equals("Unknown"))
                    {
                        party.setText("(" + location.getPartytype().toString() + ")");
                    }
                }
                if (location.getPhoto() != null)
                {
                    final String imageurl = location.getPhoto();
                    Log.d("ImageUrl:", location.getPhoto().toString());
                }
                //Picasso.with(this)
                //      .load(imageurl)
                //    .into(imageView);
                if (isNetworkConnected())
                {
                    if (location.getPhoto() != null) {
                        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                            final String imageurl = location.getPhoto();
                            Log.d("ImageUrl:", location.getPhoto().toString());
                            Log.d("1", "1");

                            Picasso picasso = new Picasso.Builder(this).listener(new Picasso.Listener() {
                                @Override
                                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) { // Here we try https if the http image attempt failed
                                    final String changedUrl = imageurl.replace("http:", "https:");
                                    picasso.load(changedUrl).error(R.drawable.brokenimage)
                                            .placeholder(R.drawable.placeholder).into(imageView);
                                }
                            }).build();
                            picasso.load(imageurl).error(R.drawable.brokenimage)
                                    .placeholder(R.drawable.placeholder).into(imageView);
                            imageView.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(OfficialActivity.this, PhotodetailActivity.class);
                                    intent.putExtra("myinfo1", location);
                                    startActivity(intent);
                                }
                            });
                        } else {
                            Picasso.with(this).load(R.drawable.placeholder).into(imageView);
                            imageView.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(OfficialActivity.this, PhotodetailActivity.class);
                                    intent.putExtra("myinfo1", location);
                                    startActivity(intent);
                                }
                            });
                        }
                    } else {
                        Picasso.with(this).load(R.drawable.missingimage).into(imageView);
                    }
                }

                zipcode.setText(location.getLocationname());

                if (location.getAddress1() != null && location.getAddress2() != null && location.getAddress3() != null && location.getAddress4() != null && location.getAddress5() != null && location.getAddress6() == null) {
                    addresstext.setText(location.getAddress1().toString() + ",\n" + location.getAddress2().toString() + ",\n" + location.getAddress3().toString() + "," + location.getAddress4().toString() + " " + location.getAddress5().toString());
                    addresstext.setLinkTextColor(Color.WHITE);
                    Linkify.addLinks(((TextView) findViewById(R.id.AddressText)), Linkify.MAP_ADDRESSES);
                } else if (location.getAddress2() == null && location.getAddress1() != null && location.getAddress3() != null && location.getAddress4() != null && location.getAddress5() != null)
                {
                    String address = location.getAddress1().toString() + ",\n" + location.getAddress3().toString() + ", " + location.getAddress4().toString() + " " + location.getAddress5().toString();
                    Log.d(TAG, address);
                    addresstext.setText(address);
                    addresstext.setLinkTextColor(Color.WHITE);
                    Linkify.addLinks(((TextView) findViewById(R.id.AddressText)), Linkify.MAP_ADDRESSES);
                } else if (location.getAddress2() == null && location.getAddress1() == null && location.getAddress3() == null && location.getAddress4() == null && location.getAddress5() == null) {
                    addresstext.setText("No Data Provided");
                    addresstext.setLinkTextColor(Color.WHITE);
                }
                else if (location.getAddress2() == null && location.getAddress1() == null && location.getAddress3() == null && location.getAddress4() == null && location.getAddress5() == null) {
                    addresstext.setText("No Data Provided");
                    addresstext.setLinkTextColor(Color.WHITE);
                }
                else if (location.getAddress1() != null && location.getAddress2() != null && location.getAddress3() != null && location.getAddress4() != null && location.getAddress5() != null && location.getAddress6() != null)
                {
                    String address = location.getAddress1().toString() + ",\n" + location.getAddress6().toString() + ",\n"  + location.getAddress3().toString() + "," + location.getAddress2().toString() + "" + location.getAddress4().toString() + "," + location.getAddress5().toString();
                    Log.d(TAG, address);
                    addresstext.setText(address);
                    //addresstext.setText(location.getAddress1().toString() + ",\n" + location.getAddress6().toString() + ",\n" +location.getAddress2().toString() + ",\n" + location.getAddress3().toString() + "," + location.getAddress4().toString() + " " + location.getAddress5().toString());
                    addresstext.setLinkTextColor(Color.WHITE);
                    Linkify.addLinks(((TextView) findViewById(R.id.AddressText)), Linkify.MAP_ADDRESSES);
                }
                else
                    addresstext.setText("No Data Provided");
                addresstext.setLinkTextColor(Color.WHITE);

                {
                    if (location.getPhone() != null)
                    {
                        phonetext.setText(location.getPhone().toString());
                        phonetext.setLinkTextColor(Color.WHITE);
                        Linkify.addLinks(((TextView) findViewById(R.id.phoneNumber)), Linkify.PHONE_NUMBERS);
                    } else
                        phonetext.setText("No Data Provided");
                    phonetext.setLinkTextColor(Color.WHITE);
                }

                {
                    if (location.getEmail() != null)
                    {
                        emailtext.setText(location.getEmail().toString());
                        emailtext.setLinkTextColor(Color.WHITE);
                        Linkify.addLinks(((TextView) findViewById(R.id.EmailText)), Linkify.EMAIL_ADDRESSES);
                    } else
                        emailtext.setText("No Data Provided");
                    emailtext.setLinkTextColor(Color.WHITE);
                }

                {
                    if (location.getWebsite() != null)
                    {
                        websitetext.setText(location.getWebsite().toString());
                        websitetext.setLinkTextColor(Color.WHITE);
                        Linkify.addLinks(((TextView) findViewById(R.id.WebsiteText)), Linkify.WEB_URLS);
                    }
                    else
                        websitetext.setText("No Data Provided");
                    websitetext.setLinkTextColor(Color.WHITE);
                }


                if (location.getFacebook() != null) {
                    Log.d("FB", location.getFacebook());
                }
                if (location.getYoutube() != null) {
                    Log.d("YT", location.getYoutube());
                }
                if (location.getTwitter() != null) {
                    Log.d("TW", location.getTwitter());
                }
                if (location.getGooglePlus() != null) {
                    Log.d("G+", location.getGooglePlus());
                }

                {
                    if (location.getFacebook() != null) {
                        facebook.setVisibility(View.VISIBLE);
                        facebook.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String FACEBOOK_URL = "https://www.facebook.com/" + location.getFacebook();
                                Log.d(TAG, FACEBOOK_URL);
                                String urlToUse;
                                PackageManager packageManager = getPackageManager();
                                try {
                                    int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
                                    if (versionCode >= 3002850) { //newer versions of fb app
                                        urlToUse = "fb://facewebmodal/f?href=" + FACEBOOK_URL;
                                        Log.d(TAG + "1", urlToUse);
                                    } else { //older versions of fb app
                                        urlToUse = "fb://page/" + location.getFacebook();
                                        Log.d(TAG + "2", urlToUse);
                                    }
                                } catch (PackageManager.NameNotFoundException e) {
                                    urlToUse = FACEBOOK_URL; //normal web url
                                }
                                Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
                                facebookIntent.setData(Uri.parse(urlToUse));
                                startActivity(facebookIntent);
                            }
                        });
                    } else
                        facebook.setVisibility(View.INVISIBLE);
                }

                {
                    if (location.getTwitter() != null) {
                        twitter.setVisibility(View.VISIBLE);
                        twitter.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = null;
                                String name = location.getTwitter();
                                try {
                                    // get the Twitter app if possible
                                    getPackageManager().getPackageInfo("com.twitter.android", 0);
                                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=" + name));
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                } catch (Exception e) {
                                    // no Twitter app, revert to browser
                                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/" + name));
                                }
                                startActivity(intent);
                            }
                        });
                    } else
                        twitter.setVisibility(View.INVISIBLE);
                }

                {
                    if (location.getGooglePlus() != null) {
                        googleplus.setVisibility(View.VISIBLE);
                        googleplus.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                {
                                    String name = location.getGooglePlus();
                                    Intent intent = null;
                                    try {
                                        intent = new Intent(Intent.ACTION_VIEW);
                                        intent.setClassName("com.google.android.apps.plus", "com.google.android.apps.plus.phone.UrlGatewayActivity");
                                        intent.putExtra("customAppUri", name);
                                        startActivity(intent);
                                    } catch (ActivityNotFoundException e) {
                                        startActivity(new Intent(Intent.ACTION_VIEW,
                                                Uri.parse("https://plus.google.com/" + name)));
                                    }
                                }
                            }
                        });
                    } else
                        googleplus.setVisibility(View.INVISIBLE);
                }
                {
                    if (location.getYoutube() != null) {
                        Log.d(TAG, "youtube visible");
                        youtube.setVisibility(View.VISIBLE);
                        youtube.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                {
                                    String name = location.getYoutube();
                                    Intent intent = null;
                                    try {
                                        intent = new Intent(Intent.ACTION_VIEW);
                                        intent.setPackage("com.google.android.youtube");
                                        intent.setData(Uri.parse("https://www.youtube.com/" + name));
                                        startActivity(intent);
                                    } catch (ActivityNotFoundException e) {
                                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/" + name)));
                                    }
                                }

                            }
                        });
                    }
                    else
                        youtube.setVisibility(View.INVISIBLE);
                }
                CL1 = (ConstraintLayout) findViewById(R.id.CL);
                CL1.setBackgroundColor(getResources().getColor(R.color.blue));
                CL2 = (ConstraintLayout) findViewById(R.id.ConstraintL);
                CL2.setBackgroundColor(getResources().getColor(R.color.blue));
            }

            //Unknown
            else
            {
                ptype.setText(location.getPositiontype().toString());
                pname.setText(location.getPositionname().toString());
                {
                    if(!location.getPartytype().equals("Unknown"))
                    {
                        party.setText("(" + location.getPartytype().toString() + ")");
                    }
                }
                if (location.getPhoto() != null)
                {
                    final String imageurl = location.getPhoto();
                    Log.d("ImageUrl:", location.getPhoto().toString());
                }
                //Picasso.with(this)
                //      .load(imageurl)
                //    .into(imageView);
                if (isNetworkConnected())
                {
                    if (location.getPhoto() != null) {
                        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                            final String imageurl = location.getPhoto();
                            Log.d("ImageUrl:", location.getPhoto().toString());
                            Log.d("1", "1");

                            Picasso picasso = new Picasso.Builder(this).listener(new Picasso.Listener() {
                                @Override
                                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) { // Here we try https if the http image attempt failed
                                    final String changedUrl = imageurl.replace("http:", "https:");
                                    picasso.load(changedUrl).error(R.drawable.brokenimage)
                                            .placeholder(R.drawable.placeholder).into(imageView);
                                }
                            }).build();
                            picasso.load(imageurl).error(R.drawable.brokenimage)
                                    .placeholder(R.drawable.placeholder).into(imageView);
                            imageView.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(OfficialActivity.this, PhotodetailActivity.class);
                                    intent.putExtra("myinfo1", location);
                                    startActivity(intent);
                                }
                            });
                        } else {
                            Picasso.with(this).load(R.drawable.placeholder).into(imageView);
                            imageView.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(OfficialActivity.this, PhotodetailActivity.class);
                                    intent.putExtra("myinfo1", location);
                                    startActivity(intent);
                                }
                            });
                        }
                    } else {
                        Picasso.with(this).load(R.drawable.missingimage).into(imageView);
                    }
                }
                zipcode.setText(location.getLocationname());

                if (location.getAddress1() != null && location.getAddress2() != null && location.getAddress3() != null && location.getAddress4() != null && location.getAddress5() != null && location.getAddress6() == null) {
                    addresstext.setText(location.getAddress1().toString() + ",\n" + location.getAddress2().toString() + ",\n" + location.getAddress3().toString() + "," + location.getAddress4().toString() + " " + location.getAddress5().toString());
                    addresstext.setLinkTextColor(Color.WHITE);
                    Linkify.addLinks(((TextView) findViewById(R.id.AddressText)), Linkify.MAP_ADDRESSES);
                } else if (location.getAddress2() == null && location.getAddress1() != null && location.getAddress3() != null && location.getAddress4() != null && location.getAddress5() != null)
                {
                    String address = location.getAddress1().toString() + ",\n" + location.getAddress3().toString() + "," + location.getAddress4().toString() + " " + location.getAddress5().toString();
                    Log.d(TAG, address);
                    addresstext.setText(address);
                    addresstext.setLinkTextColor(Color.WHITE);
                    Linkify.addLinks(((TextView) findViewById(R.id.AddressText)), Linkify.MAP_ADDRESSES);
                } else if (location.getAddress2() == null && location.getAddress1() == null && location.getAddress3() == null && location.getAddress4() == null && location.getAddress5() == null) {
                    addresstext.setText("No Data Provided");
                    addresstext.setLinkTextColor(Color.WHITE);
                }
                else if (location.getAddress2() == null && location.getAddress1() == null && location.getAddress3() == null && location.getAddress4() == null && location.getAddress5() == null) {
                    addresstext.setText("No Data Provided");
                    addresstext.setLinkTextColor(Color.WHITE);
                }
                else if (location.getAddress1() != null && location.getAddress2() != null && location.getAddress3() != null && location.getAddress4() != null && location.getAddress5() != null && location.getAddress6() != null)
                {
                    addresstext.setText(location.getAddress1().toString() + ",\n" + location.getAddress6().toString() + ",\n" + location.getAddress3().toString() + "," + location.getAddress2().toString() + "," + location.getAddress4().toString() + " " + location.getAddress5().toString());
                    addresstext.setLinkTextColor(Color.WHITE);
                    Linkify.addLinks(((TextView) findViewById(R.id.AddressText)), Linkify.MAP_ADDRESSES);
                }
                else
                    addresstext.setText("No Data Provided");
                addresstext.setLinkTextColor(Color.WHITE);

                {
                    if (location.getPhone() != null)
                    {
                        phonetext.setText(location.getPhone().toString());
                        phonetext.setLinkTextColor(Color.WHITE);
                        Linkify.addLinks(((TextView) findViewById(R.id.phoneNumber)), Linkify.PHONE_NUMBERS);
                    } else
                        phonetext.setText("No Data Provided");
                    phonetext.setLinkTextColor(Color.WHITE);
                }

                {
                    if (location.getEmail() != null)
                    {
                        emailtext.setText(location.getEmail().toString());
                        emailtext.setLinkTextColor(Color.WHITE);
                        Linkify.addLinks(((TextView) findViewById(R.id.EmailText)), Linkify.EMAIL_ADDRESSES);
                    } else
                        emailtext.setText("No Data Provided");
                    emailtext.setLinkTextColor(Color.WHITE);
                }

                {
                    if (location.getWebsite() != null)
                    {
                        websitetext.setText(location.getWebsite().toString());
                        websitetext.setLinkTextColor(Color.WHITE);
                        Linkify.addLinks(((TextView) findViewById(R.id.WebsiteText)), Linkify.WEB_URLS);
                    }
                    else
                        websitetext.setText("No Data Provided");
                        websitetext.setLinkTextColor(Color.WHITE);
                }

                if (location.getFacebook() != null) {
                    Log.d("FB", location.getFacebook());
                }
                if (location.getYoutube() != null) {
                    Log.d("YT", location.getYoutube());
                }
                if (location.getTwitter() != null) {
                    Log.d("TW", location.getTwitter());
                }
                if (location.getGooglePlus() != null) {
                    Log.d("G+", location.getGooglePlus());
                }

                {
                    if (location.getFacebook() != null) {
                        facebook.setVisibility(View.VISIBLE);
                        facebook.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String FACEBOOK_URL = "https://www.facebook.com/" + location.getFacebook();
                                Log.d(TAG, FACEBOOK_URL);
                                String urlToUse;
                                PackageManager packageManager = getPackageManager();
                                try {
                                    int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
                                    if (versionCode >= 3002850) { //newer versions of fb app
                                        urlToUse = "fb://facewebmodal/f?href=" + FACEBOOK_URL;
                                        Log.d(TAG + "1", urlToUse);
                                    } else { //older versions of fb app
                                        urlToUse = "fb://page/" + location.getFacebook();
                                        Log.d(TAG + "2", urlToUse);
                                    }
                                } catch (PackageManager.NameNotFoundException e) {
                                    urlToUse = FACEBOOK_URL; //normal web url
                                }
                                Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
                                facebookIntent.setData(Uri.parse(urlToUse));
                                startActivity(facebookIntent);
                            }
                        });
                    } else
                        facebook.setVisibility(View.INVISIBLE);
                }

                {
                    if (location.getTwitter() != null) {
                        twitter.setVisibility(View.VISIBLE);
                        twitter.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = null;
                                String name = location.getTwitter();
                                try {
                                    // get the Twitter app if possible
                                    getPackageManager().getPackageInfo("com.twitter.android", 0);
                                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=" + name));
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                } catch (Exception e) {
                                    // no Twitter app, revert to browser
                                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/" + name));
                                }
                                startActivity(intent);
                            }
                        });
                    } else
                        twitter.setVisibility(View.INVISIBLE);
                }

                {
                    if (location.getGooglePlus() != null) {
                        googleplus.setVisibility(View.VISIBLE);
                        googleplus.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                {
                                    String name = location.getGooglePlus();
                                    Intent intent = null;
                                    try {
                                        intent = new Intent(Intent.ACTION_VIEW);
                                        intent.setClassName("com.google.android.apps.plus", "com.google.android.apps.plus.phone.UrlGatewayActivity");
                                        intent.putExtra("customAppUri", name);
                                        startActivity(intent);
                                    } catch (ActivityNotFoundException e) {
                                        startActivity(new Intent(Intent.ACTION_VIEW,
                                                Uri.parse("https://plus.google.com/" + name)));
                                    }
                                }
                            }
                        });
                    } else
                        googleplus.setVisibility(View.INVISIBLE);
                }
                {
                    if (location.getYoutube() != null) {
                        Log.d(TAG, "youtube visible");
                        youtube.setVisibility(View.VISIBLE);
                        youtube.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                {
                                    String name = location.getYoutube();
                                    Intent intent = null;
                                    try {
                                        intent = new Intent(Intent.ACTION_VIEW);
                                        intent.setPackage("com.google.android.youtube");
                                        intent.setData(Uri.parse("https://www.youtube.com/" + name));
                                        startActivity(intent);
                                    } catch (ActivityNotFoundException e) {
                                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/" + name)));
                                    }
                                }

                            }
                        });
                    }
                    else
                        youtube.setVisibility(View.INVISIBLE);
                }
                CL1 = (ConstraintLayout) findViewById(R.id.CL);
                CL1.setBackgroundColor(getResources().getColor(R.color.black));
                CL2 = (ConstraintLayout) findViewById(R.id.ConstraintL);
                CL2.setBackgroundColor(getResources().getColor(R.color.black));
            }


        }
    }

}
