package com.anandsinha.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

public class ComplaintFragment extends Fragment {
    View view;
    EditText ed;
    Button addc ;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

              view=  inflater.inflate(R.layout.fragment_complaint, container, false);
        ed = (EditText)view.findViewById(R.id.details);
        addc = (Button) view.findViewById(R.id.comp);
        addc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                set();
            }
        });


        return view;
    }



    public void set(){

        final String comp = ed.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://savanand-com.stackstaging.com/comp.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                                if(obj.getString("ins").equals("yes")){
                                    Toast.makeText(getContext(),"Complaint Recorded Successfully.",Toast.LENGTH_SHORT).show();
                                    ed.setText("");
                                   // startActivity(getActivity().getC,PublicMainPage.class);

                                }
                                else{
                                    Toast.makeText(getContext(),"Some Problem Occure.",Toast.LENGTH_SHORT).show();
                                }

                        }catch (Exception e){

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(),"No Internet Connection.",Toast.LENGTH_SHORT).show();

                    }
                }
        ){
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> para = new HashMap<>();

                para.put("vehno",AppGlobalData.vehNo);
                para.put("comp",comp);
                return para;

            }
        };

        RequestQueue r= Volley.newRequestQueue(getActivity());
        r.add(stringRequest);


    }

}
