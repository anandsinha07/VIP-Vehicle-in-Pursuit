package com.anandsinha.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

public class PoliceMainPage extends AppCompatActivity implements View.OnClickListener {


    private ResideMenu resideMenu;
    private ResideMenuItem itemAboutUs, itemShare, itemProfile;
    private ResideMenuItem itemSearch, itemTow, itemTerms,itemSignout,itemContact,itemQR;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_police_main_page);
        setUpMenu();
        if(savedInstanceState == null)
            changeFragment(new QRScanningFragment());
    }

    private void setUpMenu() {

        // attach to current activity;
        resideMenu = new ResideMenu(this);

        resideMenu.setBackground(R.drawable.menu_background);
        resideMenu.attachToActivity(this);
        //resideMenu.setMenuListener(menuListener);
        //valid scale factor is between 0.0f and 1.0f. leftmenu'width is 150dip.
        resideMenu.setScaleValue(0.6f);

        // create menu items;
        itemAboutUs     = new ResideMenuItem(this, R.drawable.aboutus,     "About Us");
        itemShare = new ResideMenuItem(this, R.drawable.share, "Share");
        itemProfile  = new ResideMenuItem(this, R.drawable.police,  "Profile");
        itemSignout = new ResideMenuItem(this,R.drawable.signout, "Sign Out");
        itemSearch = new ResideMenuItem(this, R.drawable.search, "Search");
        itemTow = new ResideMenuItem(this, R.drawable.tow, "Tow");
        itemTerms = new ResideMenuItem(this, R.drawable.terms, "Terms of use");
        itemContact = new ResideMenuItem(this, R.drawable.contact,"Contact Us");
        itemQR = new ResideMenuItem(this,R.drawable.scanqr,"Scan QR");

        itemAboutUs.setOnClickListener(this);
        itemShare.setOnClickListener(this);
        itemProfile.setOnClickListener(this);
        itemSearch.setOnClickListener(this);
        itemTow.setOnClickListener(this);
        itemTerms.setOnClickListener(this);
        itemSignout.setOnClickListener(this);
        itemContact.setOnClickListener(this);
        itemQR.setOnClickListener(this);

        resideMenu.addMenuItem(itemProfile, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemSearch, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemTow, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemQR, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemSignout, ResideMenu.DIRECTION_LEFT);



        resideMenu.addMenuItem(itemAboutUs, ResideMenu.DIRECTION_RIGHT);
        resideMenu.addMenuItem(itemShare, ResideMenu.DIRECTION_RIGHT);
        resideMenu.addMenuItem(itemContact, ResideMenu.DIRECTION_RIGHT);
        resideMenu.addMenuItem(itemTerms, ResideMenu.DIRECTION_RIGHT);


        // You can disable a direction by setting ->
        // resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);

        findViewById(R.id.PolLogo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
            }
        });
        findViewById(R.id.PolBar).setOnClickListener(new View.OnClickListener() {
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

        if (view == itemAboutUs){
            changeFragment(new AboutusFragment());
        }else if (view == itemShare){
            String shareBody = "https://play.google.com/store/apps/details?id=com.whatsapp&hl=en";
            startActivity(new Intent(Intent.ACTION_SEND)
                    .setType("text/plain")
                    .putExtra(android.content.Intent.EXTRA_SUBJECT, "APP NAME (Open it in Google Play Store to Download the Application)")
                    .putExtra(android.content.Intent.EXTRA_TEXT, shareBody));
        }else if (view == itemProfile){
            changeFragment(new PoliceProfile());
        }else if (view == itemSearch){
            changeFragment(new PoliceSearchFragment());
        }else if( view  == itemTow) {
            changeFragment(new PoliceTowFragment());
        }else if(view == itemTerms) {
            changeFragment(new TermsFragment());
        }else if(view == itemSignout) {
            //changeFragment((new HomeFragment()));
           // onDestroy();

            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }else if(view == itemContact) {
            changeFragment(new ContactusFragment());
        }else if(view == itemQR) {
            changeFragment(new QRScanningFragment());
        }

        resideMenu.closeMenu();
    }

    private void changeFragment(Fragment targetFragment){
        resideMenu.clearIgnoredViewList();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment_1, targetFragment, "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("TAG",String.valueOf(resultCode));
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(result != null){
            if(result.getContents()==null) {
           Toast.makeText(this,"You cancelled the scanning",Toast.LENGTH_LONG).show();
            }
            else {
                String s = (String) result.getContents();
                //Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();

                Log.d("TAG",String.valueOf(resultCode));
                Intent t = new Intent(PoliceMainPage.this,PoliceResultQRActivity.class);
               // t.putExtra("vehno",result.getContents());
                AppGlobalData.vehNo=result.getContents();
                startActivity(t);


            }
        }
        else {

            super.onActivityResult(requestCode, resultCode, data);
        }
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

    public void onDestroy(){
        super.onDestroy();
        finish();

    }
    // What good method is to access resideMenu？
    public ResideMenu getResideMenu(){
        return resideMenu;
    }

}
