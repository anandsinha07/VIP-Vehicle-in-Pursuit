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


public class SignPoliceFragment extends Fragment{

    private EditText policeID, password, confpassword;
    private Button submit;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View signPolice = inflater.inflate(R.layout.fragment_sign_police, container, false);

        policeID = (EditText) signPolice.findViewById(R.id.dlno);
        password = (EditText) signPolice.findViewById(R.id.publicpassword);
        confpassword = (EditText) signPolice.findViewById(R.id.publicconfpassword);
        submit = (Button) signPolice.findViewById(R.id.publicsubmit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (password.getText().toString().equals(confpassword.getText().toString())) {
                    set();
                } else{

                    Toast.makeText(getContext(),"Password doesn't match",Toast.LENGTH_SHORT).show();
                }

            }
        });

        return signPolice;
    }

    public void set(){
        final String poid = policeID.getText().toString();
        final String pass = password.getText().toString();

        //final String pass = policeID.getText().toString();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://savanand-com.stackstaging.com/signup.php",
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
                                else
                                {
                                    Toast.makeText(getContext(),"Some Error Occur.",Toast.LENGTH_SHORT).show();

                                }
                            }
                            else{
                                Toast.makeText(getContext(),"PoliceID doesn't exists",Toast.LENGTH_SHORT).show();

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
                para.put("poid",poid);
                return para;

            }
        };

        RequestQueue r= Volley.newRequestQueue(getContext());
        r.add(stringRequest);
        Toast.makeText(getContext(),"sending ...",Toast.LENGTH_SHORT).show();


    }


}
