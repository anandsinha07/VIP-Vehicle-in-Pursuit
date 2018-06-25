package com.anandsinha.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
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
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

public class PublicMainPage extends AppCompatActivity implements View.OnClickListener {

    private ListView lv;
    private ArrayList<String> names;
    private ArrayAdapter adp;
    private String phone;
    private ResideMenu resideMenu;

    private ResideMenuItem itemAboutUs, itemShare, itemProfile;
    private ResideMenuItem itemSearch, itemComplaint, itemTerms,itemSignout, itemContact, itemVehicleDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_public_main_page);
        setUpMenu();

        if(getIntent().getStringExtra("Complaint").equals("TakeComplainwa"))
            changeFragment(new ComplaintFragment());
        else if(savedInstanceState == null)
            changeFragment(new VehicleDetailsFragment());
    }
    private void setUpMenu() {
        // attach to current activity;
        resideMenu = new ResideMenu(this);

        resideMenu.setBackground(R.drawable.menu_background);
        resideMenu.attachToActivity(this);
        //valid scale factor is between 0.0f and 1.0f. leftmenu'width is 150dip.
        resideMenu.setScaleValue(0.6f);

        // create menu items;
        itemAboutUs = new ResideMenuItem(this, R.drawable.aboutus, "About Us");
        itemShare = new ResideMenuItem(this, R.drawable.share, "Share");
        itemProfile  = new ResideMenuItem(this, R.drawable.icon_profile,  "Profile");
        itemSignout = new ResideMenuItem(this,R.drawable.signout, "Sign Out");
        itemSearch = new ResideMenuItem(this, R.drawable.search, "Search");
        itemComplaint = new ResideMenuItem(this, R.drawable.comp, "Complaint");
        itemTerms = new ResideMenuItem(this, R.drawable.terms, "Terms of use");
        itemContact = new ResideMenuItem(this, R.drawable.contact,"Contact Us");
        itemVehicleDetails = new ResideMenuItem(this, R.drawable.vehdel, "Vehicle Details");


        itemAboutUs.setOnClickListener(this);
        itemShare.setOnClickListener(this);
        itemProfile.setOnClickListener(this);
        itemSearch.setOnClickListener(this);
        itemComplaint.setOnClickListener(this);
        itemTerms.setOnClickListener(this);
        itemSignout.setOnClickListener(this);
        itemContact.setOnClickListener(this);
        itemVehicleDetails.setOnClickListener(this);

        resideMenu.addMenuItem(itemProfile, ResideMenu.DIRECTION_RIGHT);
        resideMenu.addMenuItem(itemSearch, ResideMenu.DIRECTION_RIGHT);
       // resideMenu.addMenuItem(itemComplaint, ResideMenu.DIRECTION_RIGHT);
        resideMenu.addMenuItem(itemVehicleDetails, ResideMenu.DIRECTION_RIGHT);
        resideMenu.addMenuItem(itemSignout, ResideMenu.DIRECTION_RIGHT);

        resideMenu.addMenuItem(itemAboutUs, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemShare, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemContact, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemTerms, ResideMenu.DIRECTION_LEFT);



        // You can disable a direction by setting ->
        // resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);

        findViewById(R.id.Pub_Logo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
            }
        });
        findViewById(R.id.PubBar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resideMenu.openMenu(ResideMenu.DIRECTION_RIGHT);
            }
        });


    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return resideMenu.dispatchTouchEvent(ev);
    }

    @Override
    public void onClick(View view) {
        //About Us Public Page
        if (view == itemAboutUs){
            changeFragment(new AboutusFragment());
        }else if (view == itemShare){
            String shareBody = "https://play.google.com/store/apps/details?id=com.whatsapp&hl=en";
            startActivity(new Intent(Intent.ACTION_SEND)
                    .setType("text/plain")
                    .putExtra(android.content.Intent.EXTRA_SUBJECT, "APP NAME (Open it in Google Play Store to Download the Application)")
                    .putExtra(android.content.Intent.EXTRA_TEXT, shareBody));
        }else if (view == itemProfile){
            changeFragment(new PublicProfileFragment());
        }else if (view == itemSearch){
            changeFragment(new PublicSearchFragment());
        } else if(view == itemTerms) {
            changeFragment(new TermsFragment());
        }else if(view == itemSignout) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }else if(view == itemContact) {
            changeFragment(new ContactusFragment());
        }else if(view == itemVehicleDetails){
            changeFragment(new VehicleDetailsFragment());
        }
        resideMenu.closeMenu();
    }
    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press BACK again to Exit", Toast.LENGTH_LONG).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 3000);
    }

    private void changeFragment(Fragment targetFragment){
        resideMenu.clearIgnoredViewList();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment_2, targetFragment, "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    public void onDestroy(){
        super.onDestroy();
        finish();

    }
    // What good method is to access resideMenu？
    public ResideMenu getResideMenu(){
        return resideMenu;
    }

}
