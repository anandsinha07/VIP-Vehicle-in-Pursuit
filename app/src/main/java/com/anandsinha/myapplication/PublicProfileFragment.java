package com.anandsinha.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.HashMap;
import java.util.Map;

public class PublicProfileFragment extends Fragment {

    String phone;
    private TextView name, dlNumber, contact, address, email;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View publicProfile = inflater.inflate(R.layout.public_profile, container, false);
        Toast.makeText(getContext(), "Opened.", Toast.LENGTH_SHORT).show();

        name = (TextView) publicProfile.findViewById(R.id.displaypubname);
        dlNumber = (TextView) publicProfile.findViewById(R.id.displaydlno);
        contact = (TextView) publicProfile.findViewById(R.id.pubdisplaycontacto);
        address = (TextView) publicProfile.findViewById(R.id.displaypubaddr);
        email = (TextView) publicProfile.findViewById(R.id.pubdisplayemail);
        Intent i = getActivity().getIntent();
        phone = i.getStringExtra("usern");
       // Toast.makeText(getContext(), "Phone Number:"+phone, Toast.LENGTH_SHORT).show();

        publicProfile.findViewById(R.id.pubChangePwd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getActivity(),ChangePassword.class).putExtra("PubUsername",phone).putExtra("PolUsername","no"));
            }
        });
        set();
        return publicProfile;
    }

    public void set(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://savanand-com.stackstaging.com/publicprofile.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            if(obj.getString("exists").equals("yes")) {
                                Toast.makeText(getContext(), "Retriving Data.", Toast.LENGTH_SHORT).show();
                                //startActivity(new Intent(Main,Main4Activity.class));
                                name.setText(obj.getString("name"));
                                dlNumber.setText(obj.getString("dl"));
                                email.setText(obj.getString("email"));
                                contact.setText(obj.getString("phone"));
                                address.setText(obj.getString("address"));
                                Toast.makeText(getContext(), "Data Retrived.", Toast.LENGTH_SHORT).show();

                            }else
                            {
                                Toast.makeText(getContext(), "Doesn't Found.", Toast.LENGTH_SHORT).show();

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
                para.put("phone",AppGlobalData.user);
                return para;
            }
        };

        RequestQueue r = Volley.newRequestQueue(getContext());
        r.add(stringRequest);


    }
}
