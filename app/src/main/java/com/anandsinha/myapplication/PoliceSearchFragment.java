package com.anandsinha.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.special.ResideMenu.ResideMenu;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class PoliceSearchFragment extends Fragment {

    EditText e;

    private ResideMenu resideMenu;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.police_search, container, false);

        e = (EditText)view.findViewById(R.id.pubvehicleno1);

        view.findViewById(R.id.searchbutton).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                set();
            }
        });

        return view;
    }



    public void set(){
        final String vehno = e.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://savanand-com.stackstaging.com/checkvehical.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            if(obj.getString("exists").equals("yes"))
                            {
                                startActivity(new Intent(getActivity(), PoliceResultActivity.class).putExtra("vehno", vehno));

                            }
                            else{
                                Toast.makeText(getContext(),"No Such Number.",Toast.LENGTH_SHORT).show();

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
                para.put("dl",vehno);
               // para.put("pass",ps);
                return para;

            }
        };
        RequestQueue r = Volley.newRequestQueue(getContext());
        r.add(stringRequest);
        Toast.makeText(getContext(),"sending ...",Toast.LENGTH_SHORT).show();
    }
}
