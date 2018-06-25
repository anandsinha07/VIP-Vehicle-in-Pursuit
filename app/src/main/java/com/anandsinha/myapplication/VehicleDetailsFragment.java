package com.anandsinha.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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

public class VehicleDetailsFragment extends Fragment {

    private ListView List;
    private String phone;
    private ArrayList<String> names;
    private ArrayAdapter arrayAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_vehicle_details, container, false);

        List = (ListView) view.findViewById(R.id.list);

        Intent it = getActivity().getIntent();
        phone = it.getStringExtra("usern");
        set();

        return view;
    }
    public void set(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://savanand-com.stackstaging.com/listveh.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            if(obj.getString("list").equals("yes")) {
                                int i=Integer.parseInt(obj.getString("num"));
                                ///// Toast.makeText(getApplicationContext(),obj.getString("num"),Toast.LENGTH_SHORT).show();
                                names = new ArrayList<>();
                                ////// startActivity(new Intent(PublicMainPage.this, PoliceResultActivity.class).putExtra("vehno", vehno));
                                for(int l=1;l<=i;l++){
                                    names.add(obj.getString(String.valueOf(l)));
                                }

                                arrayAdapter = new ArrayAdapter<>(getActivity(), R.layout.listtext, R.id.di, names);
                                List.setAdapter(arrayAdapter);

                                List.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                        startActivity(new Intent(getActivity(), PublicVehDel.class).putExtra("position",i).putExtra("files",names).putExtra("usern",phone));
                                    }
                                });

                            }
                            else{
                                Toast.makeText(getActivity(),"Error while loading",Toast.LENGTH_SHORT).show();
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
                // para.put("pass",ps);
                return para;

            }
        };
        RequestQueue r = Volley.newRequestQueue(getActivity());
        r.add(stringRequest);
    }
}
