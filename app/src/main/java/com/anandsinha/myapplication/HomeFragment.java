package com.anandsinha.myapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.special.ResideMenu.ResideMenu;

import com.anandsinha.myapplication.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class HomeFragment extends Fragment {

    private View parentView;
    //private TextView sign;
    //private ResideMenu resideMenu;
    private EditText id, Pass;
    private Button login;
    private TextView passwd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.home, container, false);

        login = (Button)parentView.findViewById(R.id.login);
        passwd = (TextView) parentView.findViewById(R.id.passwd);
        id = (EditText) parentView.findViewById(R.id.id);
        Pass = (EditText) parentView.findViewById(R.id.password);

        passwd.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                if(id.getText().toString().equals("")){
                    Toast.makeText(getContext(),"Please Enter Your User-Name",Toast.LENGTH_SHORT).show();
                }
                else{
                    set1();
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                set();
            }
        });

        return parentView;
    }

    public void set(){
        final String dl = id.getText().toString();
        final String ps = Pass.getText().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://savanand-com.stackstaging.com/login.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            if(obj.getString("exists").equals("police"))
                            {

                                Pass.setText("");
                                id.setText("");
                                startActivity(new Intent(getActivity(),PoliceMainPage.class).putExtra("usern",dl));
                                AppGlobalData.user=dl;
                                getActivity().finish();
                            }
                            else if(obj.getString("exists").equals("public")){

                                Pass.setText("");
                                id.setText("");
                                AppGlobalData.user=dl;
                                startActivity(new Intent(getActivity(),PublicMainPage.class).putExtra("usern",dl).putExtra("Complaint", ""));
                                getActivity().finish();
                            }
                            else{
                                Toast.makeText(getContext(),"Username or Password doesn't match",Toast.LENGTH_SHORT).show();
                                id.setText("");
                                Pass.setText("");
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
                para.put("usern",dl);
                para.put("pass",ps);
                return para;

            }
        };

        RequestQueue r= Volley.newRequestQueue(getContext());
        r.add(stringRequest);
        Toast.makeText(getContext(),"sending ...",Toast.LENGTH_SHORT).show();


    }





    public void set1(){
        final String dl = id.getText().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://savanand-com.stackstaging.com/forgot.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            if(obj.getString("mail").equals("yes")){
                                Toast.makeText(getContext(),"Mail sent on your registered Email ID. ",Toast.LENGTH_SHORT).show();

                            }
                            else
                            {

                                Toast.makeText(getContext(),"Username doesn't Exists.",Toast.LENGTH_SHORT).show();

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
                para.put("usern",dl);

                return para;

            }
        };

        RequestQueue r= Volley.newRequestQueue(getContext());
        r.add(stringRequest);
        Toast.makeText(getContext(),"sending ...",Toast.LENGTH_SHORT).show();


    }



}
