package com.anandsinha.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class PoliceResultQRActivity extends AppCompatActivity implements View.OnClickListener{

    private String lat, lon, Notice;
    private String addr;
    private Context context;
    LocationManager locationManager;
    private TextView name, comp, maker, regd, regauth, fuel, vehage, insval, fitness, engine, chassis, vehcls, address, contact;
    private String vehno1;
    private TextView reg;
    AppGlobalData a ;//= new AppGlobalData();
    Button b;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_police_result_qr);


        reg = (TextView)findViewById(R.id.qrregno);

        Intent i = this.getIntent();
        //vehno = i.getStringExtra("vehno");
        a= new AppGlobalData();
        vehno1 = a.vehNo;
        reg.setText(vehno1);
        context = getApplicationContext();
        b= (Button)findViewById(R.id.mail);
        b.setOnClickListener(this);
        name = (TextView) findViewById(R.id.qrdisplayoname);
        maker = (TextView) findViewById(R.id.qrresmaker);
        regd = (TextView) findViewById(R.id.qrresregidate);
        regauth = (TextView) findViewById(R.id.qrresregiauth);
        fuel = (TextView) findViewById(R.id.qrresfuel);
        vehage = (TextView) findViewById(R.id.qrresvehage);
        comp= (TextView)findViewById(R.id.ComplainDet);
        insval = (TextView) findViewById(R.id.qrresinsval);
        fitness = (TextView) findViewById(R.id.qrresfitval);
        engine = (TextView) findViewById(R.id.qrresengno);
        chassis = (TextView) findViewById(R.id.qrreschano);
        vehcls = (TextView) findViewById(R.id.qrresvehcls);
        contact = (TextView) findViewById(R.id.qrdisplaycontact);
        address = (TextView) findViewById(R.id.qrdisplayadd);
        set();

    }


    public void set(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://savanand-com.stackstaging.com/vehdelQR.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            if(obj.getString("exists").equals("yes"))
                            {
                                // startActivity(new Intent(this,PoliceMainPage.class).putExtra("usern",dl));

                                name.setText(obj.getString("oname"));

                                maker.setText(obj.getString("maker"));
                                regd.setText(obj.getString("regdat"));
                                address.setText(obj.getString("address"));
                                regauth.setText(obj.getString("regauth"));
                                fuel.setText(obj.getString("fuel"));
                                vehage.setText(obj.getString("vehage"));
                                insval.setText(obj.getString("insval"));
                                contact.setText(obj.getString("phone"));
                                fitness.setText(obj.getString("fitness"));
                                engine.setText(obj.getString("engno"));
                                chassis.setText(obj.getString("chasno"));
                                vehcls.setText(obj.getString("vehcls"));
                                if(obj.getString("lost").equals("yes")){
                                    String s = "THEFT\nDetails : \n"+obj.getString("comp");
                                    comp.setText(s);
                                    comp.setTextColor(Color.RED);
                                }
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"Retry again.",Toast.LENGTH_SHORT).show();


                            }
                        }catch (Exception e){

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ){
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> para = new HashMap<>();
                para.put("vehno",vehno1);

                return para;

            }
        };

        RequestQueue r= Volley.newRequestQueue(getApplicationContext());
        r.add(stringRequest);
        Toast.makeText(getApplicationContext(),"sending ...",Toast.LENGTH_SHORT).show();


    }

    public void set1(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://savanand-com.stackstaging.com/ownEmail.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            if(obj.getString("exists").equals("yes"))
                            {
                                if(obj.getString("mail").equals("yes")){
                                    Toast.makeText(getApplicationContext(),"Mail sent Successfully.",Toast.LENGTH_SHORT).show();
                                }
                                else
                                {

                                    Toast.makeText(getApplicationContext(),"No Email registered.",Toast.LENGTH_SHORT).show();
                                }

                            }
                            else{
                                Toast.makeText(getApplicationContext(),"Vehicle Number doesn't exists.",Toast.LENGTH_SHORT).show();

                            }
                        }catch (Exception e){

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
Toast.makeText(getApplicationContext(),"Wait for 5 Seconds and Try Again.",Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> para = new HashMap<>();
                para.put("vehno1",vehno1);
                para.put("add",addr);
                // para.put("pass",ps);
                return para;

            }
        };
        RequestQueue r = Volley.newRequestQueue(getApplicationContext());
        r.add(stringRequest);
        Toast.makeText(getApplicationContext(),"sending ...",Toast.LENGTH_SHORT).show();


    }
    @Override
    public void onClick(View view) {

        locationManager=(LocationManager) context.getSystemService(LOCATION_SERVICE);// Define a listener that responds to location updates
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
                lat = Double.toString(location.getLatitude());
                lon = Double.toString(location.getLongitude());
                // t = (TextView) findViewById(R.id.add);
                addr = "Vehicle Location is:\nLatitude : " + lat + "\nLongitude : " + lon+"\n\n"+"Address : " + GetAddress(lat,lon);

            }

            public void onStatusChanged(String provider, int status, Bundle extras) {}
            public void onProviderEnabled(String provider) {}
            public void onProviderDisabled(String provider) {}
        };
        // Register the listener with the Location Manager to receive location updates
        try {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        }catch (SecurityException s){}

           //// final String vehno1 = vehno.getText().toString();
                set1();

    }


    public String GetAddress(String lat, String lon)
    {
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.ENGLISH);
        String ret = "";
        try {
            List<Address> addresses = geocoder.getFromLocation(Double.parseDouble(lat), Double.parseDouble(lon), 1);
            if(addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("Address:\n");
//                for(int i=0; i<returnedAddress.getMaxAddressLineIndex(); i++) {
//                    Toast.makeText(getContext(),
//                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n"),Toast.LENGTH_SHORT).show();
//                }



                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName(); // Only if availabl
                // ret = strReturnedAddress.toString();
                ret = address+" "+city+" "+" "+state+" "+country+" "+postalCode+" "+knownName;
            }
            else{
                ret = "No Address returned!";
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            ret = "Can't get Address!";
        }
        return ret;
    }

}
