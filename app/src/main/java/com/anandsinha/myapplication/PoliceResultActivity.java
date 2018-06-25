package com.anandsinha.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PoliceResultActivity extends AppCompatActivity {
    /**
     * Called when the activity is first created.
     */

    private TextView name, comp, maker, regd, regauth, fuel, vehage, insval, fitness, engine, chassis, vehcls;
    private String vehno;
    private TextView reg;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_police_result);

        reg = (TextView)findViewById(R.id.regno);

        Intent i = this.getIntent();
        vehno = i.getStringExtra("vehno");
        reg.setText(vehno);

        comp = (TextView)findViewById(R.id.complaint);
        name = (TextView) findViewById(R.id.displayoname);
        maker = (TextView) findViewById(R.id.resmaker);
        regd = (TextView) findViewById(R.id.resregidate);
        regauth = (TextView) findViewById(R.id.resregiauth);
        fuel = (TextView) findViewById(R.id.resfuel);
        vehage = (TextView) findViewById(R.id.resvehage);
        insval = (TextView) findViewById(R.id.resinsval);
        fitness = (TextView) findViewById(R.id.resfitval);
        engine = (TextView) findViewById(R.id.resengno);
        chassis = (TextView) findViewById(R.id.reschano);
        vehcls = (TextView) findViewById(R.id.resvehcls);
        set();

    }


    public void set(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://savanand-com.stackstaging.com/vehicledel.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            if(obj.getString("exists").equals("yes"))
                            {
                                // startActivity(new Intent(this,PoliceMainPage.class).putExtra("usern",dl));

                                name.setText(obj.getString("oname"));
                                maker.setText(obj.getString("maker"));
                                regd.setText(obj.getString("regdat"));
                                regauth.setText(obj.getString("regauth"));
                                fuel.setText(obj.getString("fuel"));
                                vehage.setText(obj.getString("vehage"));
                                insval.setText(obj.getString("insval"));
                                fitness.setText(obj.getString("fitness"));
                                engine.setText(obj.getString("engno"));
                                chassis.setText(obj.getString("chasno"));
                                vehcls.setText(obj.getString("vehcls"));
                                if(obj.getString("lost").equals("yes")){
                                    String s = "THEFT\nDetails : \n"+obj.getString("comp");
                                    comp.setText(s);
                                    comp.setTextColor(Color.RED);
                                }
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"Retry again.",Toast.LENGTH_SHORT).show();


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
                para.put("vehno",vehno);

                return para;

            }
        };

        RequestQueue r= Volley.newRequestQueue(getApplicationContext());
        r.add(stringRequest);
        Toast.makeText(getApplicationContext(),"sending ...",Toast.LENGTH_SHORT).show();


    }
}

