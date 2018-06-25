package com.anandsinha.myapplication;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static android.content.Context.LOCATION_SERVICE;


public class PoliceTowFragment extends Fragment {

    private View parentView;
private EditText vehno;
    TextView t;
    private Button but;
    String lat,lon;
    private String addr;
    private  Context context;
    LocationManager locationManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.police_tow, container, false);
        vehno =  parentView.findViewById(R.id.pubvehicleno1);
       // but= (Button)parentView.findViewById(R.id.sendEmail);
        context=this.getActivity().getApplicationContext();

        locationManager=(LocationManager) context.getSystemService(LOCATION_SERVICE);// Define a listener that responds to location updates
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
                lat = Double.toString(location.getLatitude());
                lon = Double.toString(location.getLongitude());
                // t = (TextView) findViewById(R.id.add);
                addr="Vehicle Location is:\nLatitude : " + lat + "\nLongitude" + lon+"\n\n"+GetAddress(lat,lon);
                //Toast.makeText(getContext(),addr,Toast.LENGTH_SHORT).show();
                //t.setText(addr);
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {}
            public void onProviderEnabled(String provider) {}
            public void onProviderDisabled(String provider) {}
        };
        // Register the listener with the Location Manager to receive location updates
        try {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        }catch (SecurityException s){}
        but= parentView.findViewById(R.id.sendEmail);

        but.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                // Acquire a reference to the system Location Manager
                locationManager=(LocationManager) context.getSystemService(LOCATION_SERVICE);// Define a listener that responds to location updates
                LocationListener locationListener = new LocationListener() {
                    public void onLocationChanged(Location location) {
                        // Called when a new location is found by the network location provider.
                        lat = Double.toString(location.getLatitude());
                        lon = Double.toString(location.getLongitude());
                       // t = (TextView) findViewById(R.id.add);
                        addr = "Vehicle Location is:\nLatitude : " + lat + "\nLongitude : " + lon+"\n\n"+"Address : "+GetAddress(lat,lon);
                        //t.setText(addr);
                        //Toast.makeText(getContext(),addr,Toast.LENGTH_SHORT).show();

                    }

                    public void onStatusChanged(String provider, int status, Bundle extras) {}
                    public void onProviderEnabled(String provider) {}
                    public void onProviderDisabled(String provider) {}
                };
                // Register the listener with the Location Manager to receive location updates
                try {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
                }catch (SecurityException s){}
                set();
            }
        });



        return parentView;
    }

    public String GetAddress(String lat, String lon)
    {
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
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
        //Toast.makeText(getContext(),ret,Toast.LENGTH_SHORT).show();

        return ret;
    }


    public void set(){
        final String vehno1 = vehno.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://savanand-com.stackstaging.com/towEmail.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            if(obj.getString("exists").equals("yes"))
                            {
                                if(obj.getString("mail").equals("yes")){
                                    Toast.makeText(getContext(),"Mail sent Successfully.",Toast.LENGTH_SHORT).show();
                                }
                                else
                                {

                                    Toast.makeText(getContext(),"No Email registered.",Toast.LENGTH_SHORT).show();
                                }

                            }
                            else{
                                Toast.makeText(getContext(),"Vehicle Number doesn't exists.",Toast.LENGTH_SHORT).show();

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
                para.put("add",addr);
                // para.put("pass",ps);
                return para;

            }
        };
        RequestQueue r = Volley.newRequestQueue(getContext());
        r.add(stringRequest);
        Toast.makeText(getContext(),"sending ...",Toast.LENGTH_SHORT).show();
    }
}
