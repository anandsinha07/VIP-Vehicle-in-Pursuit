package com.anandsinha.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PublicSearchResultActivity extends AppCompatActivity {
    private TextView name, drvl, maker, regd, regauth, fuel, vehage, insval, fitness, engine, chassis, vehcls;
    private String vehno;
    private TextView reg;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_public_search_result);

        reg = (TextView)findViewById(R.id.pubregno);

        Intent i = this.getIntent();
        vehno = i.getStringExtra("vehno");
        reg.setText(vehno);

        name = (TextView) findViewById(R.id.pubdisplayoname);
        maker = (TextView) findViewById(R.id.pubresmaker);
        regd = (TextView) findViewById(R.id.pubresregidate);
        regauth = (TextView) findViewById(R.id.pubresregiauth);
        fuel = (TextView) findViewById(R.id.pubresfuel);
        vehage = (TextView) findViewById(R.id.pubresvehage);
        insval = (TextView) findViewById(R.id.pubresinsval);
        fitness = (TextView) findViewById(R.id.pubresfitval);
        engine = (TextView) findViewById(R.id.pubresengno);
        chassis = (TextView) findViewById(R.id.pubreschano);
        vehcls = (TextView) findViewById(R.id.pubresvehcls);
        set();
    }

    public void set(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://savanand-com.stackstaging.com/vehicledel.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            if(obj.getString("exists").equals("yes"))  {
                                name.setText(obj.getString("oname"));
                                maker.setText(obj.getString("maker"));
                                regd.setText(obj.getString("regdat"));
                                regauth.setText(obj.getString("regauth"));
                                fuel.setText(obj.getString("fuel"));
                                vehage.setText(obj.getString("vehage"));
                                insval.setText(obj.getString("insval"));
                                fitness.setText(obj.getString("fitness"));
                                String ss= obj.getString("engno").substring(0,6);
                                engine.setText(ss+"XXXXX");
                                String lol= obj.getString("chasno").substring(0,12);
                                chassis.setText(lol+"XXXXX");
                                vehcls.setText(obj.getString("vehcls"));
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"Try again.",Toast.LENGTH_SHORT).show();
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
