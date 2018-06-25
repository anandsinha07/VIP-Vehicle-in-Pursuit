package com.anandsinha.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

public class ChangePassword extends AppCompatActivity {

    private EditText NewPassword,CnfPassword;

    private Button SubmitPwd;

    private String getPolUser;

    private String getPubUser;

private String usern ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        getPolUser = getIntent().getStringExtra("PolUsername");
        getPubUser = getIntent().getStringExtra("PubUsername");

        NewPassword = (EditText) findViewById(R.id.newpwd);
        CnfPassword = (EditText) findViewById(R.id.cnfpwd);

        SubmitPwd = (Button) findViewById(R.id.submitpwd);

        SubmitPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(NewPassword.getText().toString().equals(CnfPassword.getText().toString())){

                    if(getPubUser.equals("no")) set();
                    else set1();
                }

                else
                    Toast.makeText(getApplicationContext(),"Password doesn't Match.",Toast.LENGTH_SHORT).show();

            }
        });



    }




    public void set(){
        final String poid = getPolUser;

        final String pass = NewPassword.getText().toString();
        //final String pass = policeID.getText().toString();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://savanand-com.stackstaging.com/polchngpass.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            if(obj.getString("exists").equals("yes")){
                                if(obj.getString("ins").equals("yes")){
                                    Toast.makeText(getApplicationContext(),"Password Changed Successfully.\nPlease LogIn Again.",Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(ChangePassword.this,MainActivity.class));
                                    finish();
                                }
                                else{
                                    Toast.makeText(getApplicationContext(),"Some Problem Occure.",Toast.LENGTH_SHORT).show();
                                }
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"Username doen't Exists.",Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e){
                            Toast.makeText(getApplicationContext(),"No Internet Connection.",Toast.LENGTH_SHORT).show();

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

                para.put("pass",pass);
                para.put("usern",poid);
                return para;

            }
        };

        RequestQueue r= Volley.newRequestQueue(this);
        r.add(stringRequest);


    }

    public void set1(){
        final String poid = getPubUser;

        final String pass = NewPassword.getText().toString();
        //final String pass = policeID.getText().toString();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://savanand-com.stackstaging.com/pubchngpass.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            if(obj.getString("exists").equals("yes")){
                                if(obj.getString("ins").equals("yes")){
                                    Toast.makeText(getApplicationContext(),"Password Changed Successfully.\nPlease LogIn Again.",Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(ChangePassword.this,MainActivity.class));
                                    finish();
                                }
                                else{
                                    Toast.makeText(getApplicationContext(),"Some Problem Occure.",Toast.LENGTH_SHORT).show();
                                }
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"Username doen't Exists.",Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e){
                            Toast.makeText(getApplicationContext(),"No Internet Connection.",Toast.LENGTH_SHORT).show();

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

                para.put("pass",pass);
                para.put("usern",poid);
                return para;

            }
        };

        RequestQueue r= Volley.newRequestQueue(this);
        r.add(stringRequest);


    }
}
