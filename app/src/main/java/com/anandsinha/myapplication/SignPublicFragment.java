package com.anandsinha.myapplication;

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


public class SignPublicFragment extends Fragment {

    private EditText Contact, password, confPassword, email1;
    private Button publicSignUp;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View signpublic= inflater.inflate(R.layout.fragment_sign_public, container, false);

        Contact = (EditText) signpublic.findViewById(R.id.dlno);
        email1 = (EditText) signpublic.findViewById(R.id.pubemail);
        password = (EditText) signpublic.findViewById(R.id.publicpassword);
        confPassword = (EditText) signpublic.findViewById(R.id.publicconfpassword);

        publicSignUp = (Button) signpublic.findViewById(R.id.publicsubmit);

        publicSignUp.setOnClickListener(new View.OnClickListener() {
           public void onClick(View view) {
               if(password.getText().toString().equals(confPassword.getText().toString())){
                   set();
               }
               else{
                   Toast.makeText(getContext(),"Password Doesn't Match",Toast.LENGTH_SHORT).show();
               }
           }
        });

        return signpublic;
    }

    public void set(){
        final String poid = Contact.getText().toString();
        final String pass = password.getText().toString();
    final String email = email1.getText().toString();
        //final String pass = policeID.getText().toString();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://savanand-com.stackstaging.com/pubsignin.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            if(obj.getString("exists").equals("yes"))
                            {
                                if(obj.getString("ins").equals("yes")){
                                    Toast.makeText(getContext(),"You Are Registered Successfully.",Toast.LENGTH_SHORT).show();
                                }
                                else if(obj.getString("ins").equals("not")){
                                    Toast.makeText(getContext(),"You Are Already Registered.",Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(getContext(),"Some Error Occur.",Toast.LENGTH_SHORT).show();
                                }
                            }
                            else{
                                Toast.makeText(getContext(),"Phone Number doesn't exists\nPlease Register Your Phone Number.",Toast.LENGTH_SHORT).show();
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

                para.put("pass",pass);
                para.put("phone",poid);
                para.put("email",email);
                return para;

            }
        };

        RequestQueue r= Volley.newRequestQueue(getContext());
        r.add(stringRequest);
        Toast.makeText(getContext(),"sending ...",Toast.LENGTH_SHORT).show();


    }
}
