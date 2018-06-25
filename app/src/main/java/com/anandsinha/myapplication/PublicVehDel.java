package com.anandsinha.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PublicVehDel extends AppCompatActivity implements View.OnClickListener{

    private TextView name,  maker, regd, regauth, fuel, vehage, insval, fitness, engine, chassis, vehcls;
    private String vehno;
    private TextView reg;
    private ImageView image;
    private Button complain,remcomp;

    public void Complain() {
        startActivity(new Intent(PublicVehDel.this, PublicMainPage.class).putExtra("Complaint", "TakeComplainwa"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_public_veh_del);
        Intent i = getIntent();
        Bundle b= i.getExtras();
        ArrayList<String> l =b.getStringArrayList("files");

        reg = (TextView)findViewById(R.id.pubregno);
        complain = (Button) findViewById(R.id.button3);
       // Intent i = this.getIntent();
        vehno = l.get(b.getInt("position",0));//i.getStringExtra("vehno");
        reg.setText(vehno);
        image=(ImageView) findViewById(R.id.image);

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
        remcomp = (Button) findViewById(R.id.remcomp);
        remcomp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                set1();
            }
        });
        complain.setOnClickListener(this);

        set();
       // Toast.makeText(getApplicationContext(),"yo bro"+l.get(b.getInt("position",0)),Toast.LENGTH_SHORT).show();
    }


    public void set(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://savanand-com.stackstaging.com/vehicledel.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            if(obj.getString("exists").equals("yes"))  {
                              String   txt2qr =vehno;
                                MultiFormatWriter multiFormatWriter= new MultiFormatWriter();
                                try {
                                    BitMatrix bitMatrix= multiFormatWriter.encode(txt2qr, BarcodeFormat.QR_CODE,200,200);
                                    BarcodeEncoder barcodeEncoder= new BarcodeEncoder();
                                    Bitmap bitmap= barcodeEncoder.createBitmap(bitMatrix);
                                    image.setImageBitmap(bitmap);
                                }
                                catch(WriterException e) {
                                    e.printStackTrace();
                                }
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


    public void set1(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://savanand-com.stackstaging.com/compcancel.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            if(obj.getString("ins").equals("yes"))  {
                                Toast.makeText(getApplicationContext(),"Complaint Removed Successfully.",Toast.LENGTH_SHORT).show();

                            }
                            else{
                                Toast.makeText(getApplicationContext(),"No Complaint Registered.",Toast.LENGTH_SHORT).show();
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
        Toast.makeText(getApplicationContext(),"Removing ...",Toast.LENGTH_SHORT).show();
    }


    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.button3) {
            AppGlobalData.vehNo=vehno;
            Complain();
        }
    }

}
