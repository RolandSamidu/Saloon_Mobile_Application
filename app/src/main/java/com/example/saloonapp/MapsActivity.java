package com.example.saloonapp;

import androidx.fragment.app.FragmentActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LocationManager mLocationManager;
    private LocationListener mLocationListener;
    private MarkerOptions mMarkerOptions;
    private LatLng mOrigin;
    private LatLng mDestination;
    private Polyline mPolyline;
    String cordinationResult = "";
    String location = "";
    String part111 = "del";
    String part112 = "dell";
    String part1,part2;
    String part12;
    String shoplocation,v11,v12;
    String intentlocation;
    double part1D = 0.00;
    double part2D = 0.00;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        isConnected();
        SelectGPSLocation();

        part1 = "hello";

        part1 = part111.toString();
        part2 = part112.toString();
       // part1D = Double.parseDouble(part1); // NOT WORKING WITH GOOGLE MAP
       // part2D =Double.parseDouble(part2);


        //intentlocation = getIntent().getStringExtra("INTENT_LOCATION");
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        getMyLocation();
       // SelectGPSLocation();



      //  part2 = v12.toString();
       // Toast.makeText(getApplicationContext(),"VALUES :"+part1 + ""+part2,Toast.LENGTH_LONG).show();
  // Add a marker in Sydney and move the camera

      //String stringParts =  result;

      //--------------------------------------

       //String part11 = split1(result);
     //  String part12 = split2(result);


    //  String[] partsA = stringParts.split(",",2);
     //   part11 = partsA[0].substring(1).trim(); // 004

     //  partsA[1] = partsA[1].trim();

      //  part12 = partsA[1]; // 034556
      ///  String  part2RemovedLast = methodRemoveEndChar(part12);



       // float part1D = Float.parseFloat(part1);
        //double part1D =Double.parseDouble(part1);
        //double part2D =Double.parseDouble(part2RemovedLast);
        //float part2D = Float.parseFloat(part2);

    //    double part1D =Double.parseDouble(part1);
     //   double part2D =Double.parseDouble(part2);


    //    LatLng sydney = new LatLng(Double.valueOf(part1.trim()), Double.valueOf(part2.trim()));
        LatLng sydney3 = new LatLng(6.86558, 79.90411);
        LatLng sydney4 = new LatLng(6.84681, 79.89313);
    //    mMap.addMarker(new MarkerOptions().position(sydney).title(locationarray[0].toString()));
        mMap.addMarker(new MarkerOptions().position(sydney3).title("Inspired Biusness 3"));
        mMap.addMarker(new MarkerOptions().position(sydney4).title("Inspired Biusness 4"));

      //  mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney3));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney4));



    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {

        if (requestCode == 100){
            if (!verifyAllPermissions(grantResults)) {
                Toast.makeText(getApplicationContext(),"No sufficient permissions",Toast.LENGTH_LONG).show();
            }else{
                getMyLocation();
            }
        }else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private boolean verifyAllPermissions(int[] grantResults) {

        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    private void getMyLocation(){

        // Getting LocationManager object from System Service LOCATION_SERVICE
        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                mOrigin = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mOrigin,12));
                if(mOrigin != null & mDestination != null)
                drawRoute();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        int currentApiVersion = Build.VERSION.SDK_INT;
        if (currentApiVersion >= Build.VERSION_CODES.M) {

            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_DENIED) {
                mMap.setMyLocationEnabled(true);
                mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,10000,0,mLocationListener);

                mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                    @Override
                    public void onMapLongClick(LatLng latLng) {
                        mDestination = latLng;
                        mMap.clear();
                        mMarkerOptions = new MarkerOptions().position(mDestination).title("Inspired Buisness Solution");
                        mMap.addMarker(mMarkerOptions);
                        if(mOrigin != null & mDestination != null)
                        drawRoute();
                    }
                });

            }else{
                requestPermissions(new String[]{
                        android.Manifest.permission.ACCESS_FINE_LOCATION
                },100);
            }
        }
    }


    private void drawRoute(){

        // Getting URL to the Google Directions API
        String url = getDirectionsUrl(mOrigin, mDestination);

        DownloadTask downloadTask = new DownloadTask();

        // Start downloading json data from Google Directions API
        downloadTask.execute(url);
    }


    private String getDirectionsUrl(LatLng origin,LatLng dest){

        // Origin of route
        String str_origin = "origin="+origin.latitude+","+origin.longitude;

        // Destination of route
        String str_dest = "destination="+dest.latitude+","+dest.longitude;

        // Key
        String key = "key=" + getString(R.string.google_maps_key);

        // Building the parameters to the web service
        String parameters = str_origin+"&amp;"+str_dest+"&amp;"+key;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters;

        return url;
    }

    /** A method to download json data from url */
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb  = new StringBuffer();

            String line = "";
            while( ( line = br.readLine())  != null){
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        }catch(Exception e){
            Log.d("Exception on download", e.toString());
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    /** A class to download data from Google Directions URL */
    private class DownloadTask extends AsyncTask<String, Void, String> {

        // Downloading data in non-ui thread
        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try{
                // Fetching the data from web service
                data = downloadUrl(url[0]);
                Log.d("DownloadTask","DownloadTask : " + data);
            }catch(Exception e){
                Log.d("Background Task",e.toString());
            }
            return data;
        }

        // Executes in UI thread, after the execution of
        // doInBackground()
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);
        }
    }

    /** A class to parse the Google Directions in JSON format */
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>> >{

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try{
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                // Starts parsing data
                routes = parser.parse(jObject);
            }catch(Exception e){
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points = null;
            PolylineOptions lineOptions = null;

            // Traversing through all the routes
            for(int i=0;i<result.size();i++){
                points = new ArrayList<LatLng>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for(int j=0;j<path.size();j++){
                    HashMap<String,String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(8);
                lineOptions.color(Color.RED);
            }

            // Drawing polyline in the Google Map for the i-th route
            if(lineOptions != null) {
                if(mPolyline != null){
                    mPolyline.remove();
                }
                mPolyline = mMap.addPolyline(lineOptions);

            }else
                Toast.makeText(getApplicationContext(),"No route is found", Toast.LENGTH_LONG).show();
        }
    }


   //read data from online database
    public void SelectGPSLocation(){
        class SendPostReqAsyncTask extends AsyncTask<String,Void,String> {

            @Override
            public String doInBackground(String... strings) {

                try {
                    URL url = new URL("https://www.nawagamuwadevalaya.com/SalonSelectLocation.php");
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "utf-8"));

                    String line = null;
                    StringBuilder sb = new StringBuilder();

                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    cordinationResult = sb.toString();
                    Log.e("pass 2", "connection success ");
                } catch (Exception e) {
                    Log.e("Please check connection", e.toString());
                }

                try {
                    JSONObject json_data = new JSONObject(cordinationResult);
                    shoplocation = (json_data.getString("Location"));
                    v11 = (json_data.getString("V1"));
                    v12 = (json_data.getString("V2"));
                    part111 = v11;
                    part112 = v12;

                } catch (Exception e) {
                    Log.e("Connection failed", e.toString());

                }
                return shoplocation.toString();
            }

        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute();
    }

    public boolean isConnected() {
        ConnectivityManager manager = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            // Toast.makeText(SignUp.this,"Internet Successful!", Toast.LENGTH_LONG).show();
            return true;
        } else {
            // Toast.makeText(SignUp.this,"Internet Fail!", Toast.LENGTH_LONG).show();
            return false;

        }



    }

    public String methodRemoveEnter(String str) {
        if (str != null && str.length() > 0 && str.charAt(str.length() - 1) == '\n') {
            str = str.substring(0, str.length() - 1);
        }

        return str;
    }

    public String methodRemoveEndChar(String str) {
        if (str != null && str.length() > 0 && str.charAt(str.length() - 1) == '"') {
            str = str.substring(0, str.length() - 1);
        }

        return str;
    }


    public String split1(String spl){
        String[] partsA = spl.split(",");
        return partsA[0];
    }
    public String split2(String spl){
        String[] partsB = spl.split(",");
        return partsB[1];
    }


}