package com.anandsinha.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class PoliceProfile extends Fragment {

    private Button ChangePassword;
    private TextView Name, polid, contact, Email, batch;
    private String usern;
    //ListView policeName;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View policeParentView = inflater.inflate(R.layout.fragment_police_profile, container, false);
        Name = (TextView) policeParentView.findViewById(R.id.displayname);
        polid = (TextView) policeParentView.findViewById(R.id.displaypoliceid);
        contact = (TextView) policeParentView.findViewById(R.id.displaycontacto);
        Email = (TextView) policeParentView.findViewById(R.id.displayemail);
        batch = (TextView) policeParentView.findViewById(R.id.displaybatch);


        Intent i = getActivity().getIntent();
        usern = i.getStringExtra("usern");

        ChangePassword = (Button) policeParentView.findViewById(R.id.ButtonPaswd);

        ChangePassword.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view){

                startActivity(new Intent(getActivity(), ChangePassword.class).putExtra("PolUsername",usern).putExtra("PubUsername","no"));
            }

        });
        //items = new ArrayList<String>();



        set();
        return policeParentView;
    }



    public void set(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://savanand-com.stackstaging.com/polprofile.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                                    if(obj.getString("exists").equals("yes")) {
                                        Toast.makeText(getContext(), "Retriving Data.", Toast.LENGTH_SHORT).show();
                                        //startActivity(new Intent(Main,Main4Activity.class));
                                        Name.setText(obj.getString("name"));
                                        polid.setText(obj.getString("poid"));
                                        Email.setText(obj.getString("email"));
                                        contact.setText(obj.getString("phone"));
                                        batch.setText(obj.getString("badgeno"));
                                    }else
                                    {
                                        Toast.makeText(getContext(), "lololololololol", Toast.LENGTH_SHORT).show();

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
                para.put("dl",AppGlobalData.user);
                return para;
            }
        };

        RequestQueue r = Volley.newRequestQueue(getContext());
        r.add(stringRequest);
    

    }
}
