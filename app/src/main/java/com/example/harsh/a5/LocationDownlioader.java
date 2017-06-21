package com.example.harsh.a5;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.security.auth.login.LoginException;

/**
 * Created by harsh on 3/26/17.
 */

class LocationDownlioader extends AsyncTask<String, Void, String>
{
    TextView dataDisplay;
    private MainActivity mainActivity;
    private OfficialActivity officialActivity;
    private int count;
    private static final String TAG = "LocationDownloader";
    private final String dataURL = "https://www.googleapis.com/civicinfo/v2/representatives?key=AIzaSyDUwjkxcRB6KvSz7NvCtfxVoh3OlAM8oZ0&address=";
    ArrayList<Location> backlist = new ArrayList<>();
    ArrayList<Location> position = new ArrayList<>();
    ArrayList<String> backlist1 = new ArrayList<>();
    JSONArray jb11 = new JSONArray();
    ArrayList<Location> locationList = new ArrayList<>();
    ArrayList<Location> locationList1 = new ArrayList<>();
    public LocationDownlioader(MainActivity ma)
    {
        mainActivity = ma;
    }
    String line11 = null, line22 = null, city11 = null, state11 = null, type1 = null, id1 = null, zip11 = null, channeltype = null, channelid = null;
    String address1 = null, address2 = null, address3 = null, address4 = null, address5 = null;
    String posname= null;
    String jb1 = null, address = null, phone = null, website = null, jb2 = null;
    JSONArray jb3 = null;
    JSONObject jb4 = null;

    @Override
    protected void onPreExecute()
    {
        Toast.makeText(mainActivity, "Loading Location Data...", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected String doInBackground(String... params) {
        Log.d(TAG, "doInBackground: " + params[0]);

        Uri datauri = Uri.parse(dataURL + params[0]);
        String urlToUse = datauri.toString();

        Log.d(TAG, "doInBackground: " + urlToUse);

        //stringBuilder sb
        StringBuilder sb = new StringBuilder();
        String line, s11;
        try
        {
            URL url = new URL(urlToUse);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            InputStream is = conn.getInputStream();
            BufferedReader reader = new BufferedReader((new InputStreamReader(is)));
            while ((line = reader.readLine()) != null)
            {
                sb.append(line).append('\n');
            }
            Log.d(TAG, "doInBackground: " + sb.toString());
        } catch (FileNotFoundException e)
        {
            ArrayList<String> location = new ArrayList<>();
            Log.e(TAG, "DoException: ", e);
            return String.valueOf(location);
        } catch (Exception e)
        {
            ArrayList<String> location = new ArrayList<>();
            Log.e(TAG, "DoException: ", e);
            return String.valueOf(location);
        }
        ArrayList<Location> location = parseJSON(sb.toString());
        return String.valueOf(location);
        //return null;
    }

    @Override
    protected void onPostExecute(String s)
    {
        Toast.makeText(mainActivity, "Loading Stock Data..", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "postExecute1: " +backlist );
        Log.d(TAG, "postExecute2: " +locationList1 );
        Log.d(TAG, "postExecute2: " +locationList1.size() );
        if(backlist.size() > 0)
        {
            mainActivity.addLocation(backlist);
        }
        if(locationList.size() > 0)
        {
            mainActivity.addPosition(locationList1);
        }
        else
            mainActivity.showToast2();

    }

    private ArrayList<Location> parseJSON(String s)
    {
        try
        {
            JSONObject jr = new JSONObject(s);
            JSONObject response = jr.getJSONObject("normalizedInput");
            count = response.length();
            String city = jr.getJSONObject("normalizedInput").getString("city");
            String state = jr.getJSONObject("normalizedInput").getString("state");
            String zip = jr.getJSONObject("normalizedInput").getString("zip");
            String locationname = city +", "+ state +" "+ zip;
            if(city!=null || state!=null || zip!=null)
            {
                backlist.add(new Location(city, state, zip));
            }
            JSONObject jr1 = new JSONObject(s);
            JSONObject jr4 = new JSONObject(s);
            //Log.d(TAG, "1: " +jr1.getJSONArray("offices"));
            JSONArray response1 = jr1.getJSONArray("offices");
            Log.d(TAG, "21: " +response1.length());

            //Location
            for(int i =0; i<response1.length(); i++)
            {
                JSONObject jb1 = response1.getJSONObject(i);
                String positionType = jb1.getString("name");
                position.add(new Location(positionType));
                //Log.i("positionType:",positionType);
            }
            //Log.d("length", String.valueOf(response1.length()));
            //jr1.getJSONArray("officials");

            //officialindicies
            /*for(int i =0; i<response1.length(); i++)
            {
                JSONObject objJson = response1.getJSONObject(i);
                String nametype = objJson.getString("name");
                Log.d("PositionType[" + i + "]", nametype);
                JSONArray offices = (response1.getJSONObject(i).getJSONArray("officialIndices"));
                Log.d("officiesIndicies" + ": ", offices.toString());
            }*/

            //officials
            for(int i1 =0; i1<response1.length(); i1++)
            {
                String GooglePlus=null, Facebook = null, Twitter = null, Youtube = null;
                Log.d("length", String.valueOf(response1.length()));
                String phone = null, website = null, email = null, photo = null;
                String line11 = null, line22 = null, city11 = null, state11 = null, zip11 = null, line33 = null;
                String address1 = null, address2 = null, address3 = null, address4 = null, address5 = null, address6 = null;
                posname = response1.getJSONObject(i1).getString("name");
                JSONArray offices = (response1.getJSONObject(i1).getJSONArray("officialIndices"));
                Log.d("officiesIndicies1" + ": ", offices.toString());

                for (int j = 0; j < response1.getJSONObject(i1).getJSONArray("officialIndices").length(); j++)
                {
                    int id11 = offices.getInt(j);

                    jb1 = jr1.getJSONArray("officials").getJSONObject(id11).getString("name");
                    try
                    {
                        if(jr1.getJSONArray("officials").getJSONObject(id11).has("party"))
                        {
                            jb2 = jr1.getJSONArray("officials").getJSONObject(id11).getString("party");
                        }
                        else
                            jb2 = "Unknown";
                    }
                    catch (JSONException e)
                    {
                        Log.e(TAG, "doInBackground3: ", e);
                    }
                    if(jr1.getJSONArray("officials").getJSONObject(id11).has("address"))
                    {
                        try
                        {
                            {
                                JSONObject line1 = jr4.getJSONArray("officials").getJSONObject(id11).getJSONArray("address").getJSONObject(0);
                                if (line1.has("line1")) {
                                    line11 = line1.getString("line1");
                                }
                                if (line1.has("line2")) {
                                    line22 = line1.getString("line2");
                                }
                                if (line1.has("city")) {
                                    city11 = line1.getString("city");
                                }
                                if (line1.has("state")) {
                                    state11 = line1.getString("state");
                                }
                                if (line1.has("zip")) {
                                    zip11 = line1.getString("zip");
                                }
                                if (line1.has("line3")) {
                                    line33 = line1.getString("line3");
                                }
                                Log.i("Address", "[" + j + "]" + ":" + line11 + " " + " "+ line33 +  line22 + " " + city11 + " " + state11 + " " + zip11);
                                address1 = line11;
                                address2 = line22;
                                address3 = city11;
                                address4 = state11;
                                address5 = zip11;
                                address6 = line33;
                            }
                        }
                        catch (JSONException e)
                        {
                            Log.e(TAG, "JSONEXCEPTION: ", e);
                        }
                    }
                    if(jr1.getJSONArray("officials").getJSONObject(id11).has("phones"))
                    {
                        {
                            JSONArray phones = (jr1.getJSONArray("officials").getJSONObject(id11).getJSONArray("phones"));
                            Log.i("Phone", "[" + j + "]" + ":" + phones.toString().replaceAll("[^\\d.]", ""));
                            phone = phones.toString().replaceAll("[^\\d.]", "");
                        }
                    }
                    if(jr1.getJSONArray("officials").getJSONObject(id11).has("urls"))
                    {
                        {
                            JSONArray urls = (jr1.getJSONArray("officials").getJSONObject(id11).getJSONArray("urls"));
                            Log.i("Website", "[" + j + "]" + ":" + urls.toString().replace("[", "").replaceAll("[\\-\\+\\\\^,]", "").substring(1, urls.toString().replace("[", "").replaceAll("[\\-\\+\\\\^,]", "").length() - 2));
                            website = urls.toString().replace("[", "").replaceAll("[\\-\\+\\\\^,]", "").substring(1, urls.toString().replace("[", "").replaceAll("[\\-\\+\\\\^,]", "").length() - 2);
                        }
                    }
                    {
                        if(jr1.getJSONArray("officials").getJSONObject(id11).has("photoUrl"))
                        {
                            photo = jr1.getJSONArray("officials").getJSONObject(id11).getString("photoUrl");
                        }
                        else
                            photo = null;
                    }
                    {
                        if (jr1.getJSONArray("officials").getJSONObject(id11).has("emails"))
                        {
                            {
                                JSONArray phones = (jr1.getJSONArray("officials").getJSONObject(id11).getJSONArray("emails"));
                                Log.i("Email", "[" + j + "]" + ":" + phones.toString().replace("[", "").replace("]", "'").substring(1, phones.toString().replace("[", "").replace("]", "'").length() - 2));
                                email = phones.toString().replace("[", "").replace("]", "'").substring(1, phones.toString().replace("[", "").replace("]", "'").length() - 2);
                            }
                        }
                        else
                            email = "No Data Provided";
                    }
                    if(jr1.getJSONArray("officials").getJSONObject(id11).has("channels"))
                    {
                        try
                        {
                            for(int k=0; k<jr4.getJSONArray("officials").getJSONObject(id11).getJSONArray("channels").length(); k++)
                            {
                                JSONObject line1 = jr4.getJSONArray("officials").getJSONObject(id11).getJSONArray("channels").getJSONObject(k);
                                if (line1.has("type"))
                                {
                                    if(line1.getString("type").equals("GooglePlus"))
                                    {
                                        GooglePlus = null;
                                        channeltype = line1.getString("type");
                                        GooglePlus = line1.getString("id");
                                        Log.e("GooglePlus[" + j + "]" + ":" + channeltype, GooglePlus);
                                    }
                                    if(line1.getString("type").equals("Facebook"))
                                    {
                                        Facebook = null;
                                        channeltype = line1.getString("type");
                                        Facebook = line1.getString("id");
                                        Log.e("Facebook[" + j + "]" + ":" + channeltype, Facebook);
                                    }
                                    if(line1.getString("type").equals("Twitter"))
                                    {
                                        Twitter = null;
                                        channeltype = line1.getString("type");
                                        Twitter = line1.getString("id");
                                        Log.e("Twitter[" + j + "]" + ":" + channeltype, Twitter);
                                    }
                                    if(line1.getString("type").equals("YouTube"))
                                    {
                                        Youtube = null;
                                        channeltype = line1.getString("type");
                                        Youtube = line1.getString("id");
                                        Log.e("Youtube[" + j + "]" + ":" + channeltype, Youtube);
                                    }
                                }
                                //Log.i("channel", "[" + j +","+ k +"]" + ":" + channeltype + " " + channelid);
                            }

                        }
                        catch (JSONException e)
                        {
                            Log.e(TAG, "JSONEXCEPTION: ", e);
                        }
                    }
                    locationList.add(new Location(posname, jb1, jb2, photo, address1, address2, address3, address4, address5, address6, phone, email, website, GooglePlus, Facebook, Twitter, Youtube, locationname));
                }
            }


            for(int k = 0; k<locationList.size(); k++)
            {
                Log.d(TAG, "LocationList: [" + k+ "]"+ locationList.get(k).getPositiontype());
                Log.d(TAG, "LocationList: [" + k+ "]"+ locationList.get(k).getPositionname());
                Log.d(TAG, "LocationList: [" + k+ "]"+ locationList.get(k).getPartytype());
                Log.d(TAG, "LocationList: [" + k+ "]"+ locationList.get(k).getPhoto());
                Log.d(TAG, "LocationList: [" + k+ "]"+ locationList.get(k).getAddress1());
                Log.d(TAG, "LocationList: [" + k+ "]"+ locationList.get(k).getAddress2());
                Log.d(TAG, "LocationList: [" + k+ "]"+ locationList.get(k).getAddress3());
                Log.d(TAG, "LocationList: [" + k+ "]"+ locationList.get(k).getAddress4());
                Log.d(TAG, "LocationList: [" + k+ "]"+ locationList.get(k).getAddress5());
                Log.d(TAG, "LocationList: [" + k+ "]"+ locationList.get(k).getPhone());
                Log.d(TAG, "LocationList: [" + k+ "]"+ locationList.get(k).getEmail());
                Log.d(TAG, "LocationList: [" + k+ "]"+ locationList.get(k).getWebsite());
                Log.d(TAG, "G+: [" + k+ "]"+ locationList.get(k).getGooglePlus());
                Log.d(TAG, "FB: [" + k+ "]"+ locationList.get(k).getFacebook());
                Log.d(TAG, "TT: [" + k+ "]"+ locationList.get(k).getTwitter());
                Log.d(TAG, "YT: [" + k+ "]"+ locationList.get(k).getYoutube());
                locationList1.add(new Location(locationList.get(k).getPositiontype(), locationList.get(k).getPositionname(), locationList.get(k).getPartytype(), locationList.get(k).getPhoto() ,locationList.get(k).getAddress1(), locationList.get(k).getAddress2(), locationList.get(k).getAddress3(), locationList.get(k).getAddress4(), locationList.get(k).getAddress5(), locationList.get(k).getAddress6(), locationList.get(k).getPhone(), locationList.get(k).getEmail(),locationList.get(k).getWebsite(), locationList.get(k).getGooglePlus(), locationList.get(k).getFacebook(), locationList.get(k).getTwitter(), locationList.get(k).getYoutube(), locationList.get(k).getLocationname()));
            }

            Log.d(TAG, "Parttype: " + locationList1.get(0).getPartytype());
            Log.d(TAG, "Photo: " + locationList1.get(0).getPhoto());
            Log.d(TAG, "Email: " + locationList1.get(0).getEmail());
            Log.d(TAG, "GooglePlus: " + locationList1.get(0).getGooglePlus());
            Log.d(TAG, "LocationList1Size: " + locationList1.size());
            Log.d(TAG, "stocklist: " + locationList1.toString());
            return locationList1;
            //return null;
        }
        catch (JSONException e)
        {
            Log.d(TAG, "parseJSON: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
