package com.anandsinha.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ResideMenu resideMenu;
    private ResideMenuItem itemPolhome, itemPolprofile,itemPolSearch,itemPubSearch;
    private ResideMenuItem itemPubhome, itemPubprofile, itemSignUpPolice, itemSignUpPublic;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        setUpMenu();

        isNetworkConnectionAvailable();      //for internet connection
        isLocationEnabled(this);

        if( savedInstanceState == null )
            changeFragment(new HomeFragment());
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    private void setUpMenu() {

        // attach to current activity;
        resideMenu = new ResideMenu(this);

        resideMenu.setBackground(R.drawable.daulu);
        resideMenu.attachToActivity(this);
        //valid scale factor is between 0.0f and 1.0f. leftmenu'width is 150dip.
        resideMenu.setScaleValue(0.6f);

        // create menu items;
        itemPolhome     = new ResideMenuItem(this, R.drawable.icon_home,     "Home");
        itemSignUpPolice = new ResideMenuItem(this, R.drawable.signup_icon, "Sign Up");
        itemPolprofile  = new ResideMenuItem(this, R.drawable.police_cap1,  "Police");
        itemPubhome = new ResideMenuItem(this, R.drawable.icon_home, "Home");
        itemSignUpPublic = new ResideMenuItem(this, R.drawable.signup_icon, "Sign Up");
        itemPubprofile = new ResideMenuItem(this, R.drawable.conference, "Public");
        itemPolSearch = new ResideMenuItem(this,R.drawable.search,"Search");
        itemPubSearch = new ResideMenuItem(this,R.drawable.search,"Search");

        itemPolhome.setOnClickListener(this);
        itemPubhome.setOnClickListener(this);
        itemPolprofile.setOnClickListener(this);
        itemPubprofile.setOnClickListener(this);
        itemSignUpPublic.setOnClickListener(this);
        itemSignUpPolice.setOnClickListener(this);
        itemPolSearch.setOnClickListener(this);
        itemPubSearch.setOnClickListener(this);

        resideMenu.addMenuItem(itemPolprofile, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemPolSearch,ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemPolhome, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemPubprofile, ResideMenu.DIRECTION_RIGHT);
        resideMenu.addMenuItem(itemPubSearch,ResideMenu.DIRECTION_RIGHT);
        resideMenu.addMenuItem(itemPubhome, ResideMenu.DIRECTION_RIGHT);
        resideMenu.addMenuItem(itemSignUpPublic, ResideMenu.DIRECTION_RIGHT);
        resideMenu.addMenuItem(itemSignUpPolice, ResideMenu.DIRECTION_LEFT);


        // You can disable a direction by setting ->
        // resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);

        findViewById(R.id.policeBar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
            }
        });
        findViewById(R.id.publicBar).setOnClickListener(new View.OnClickListener() {
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

        if (view == itemPolhome){
            changeFragment(new HomeFragment());
        }else if (view == itemPolprofile){
            changeFragment(new PolDetailFragment());
        }else if (view == itemPubhome){
            changeFragment(new HomeFragment());
        }else if (view == itemPubprofile){
            changeFragment(new PubDetailFragment());
        }else if( view  == itemSignUpPolice) {
            changeFragment(new SignPoliceFragment());
        }else if(view == itemSignUpPublic) {
            changeFragment(new SignPublicFragment());
        }else if(view == itemPolSearch){
            changeFragment(new PublicSearchFragment());
        }else if(view == itemPubSearch){
            changeFragment(new PublicSearchFragment());
        }

        resideMenu.closeMenu();
    }

////Checking for network Connectivity.........................................
    public void checkNetworkConnection(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("No internet Connection");
        builder.setMessage("Please turn on internet connection to continue");
        builder.setNegativeButton("close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public boolean isNetworkConnectionAvailable(){
        ConnectivityManager cm =
                (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnected();
        if(isConnected) {
            Log.d("Network", "Connected");
            return true;
        }
        else{
            checkNetworkConnection();
            Log.d("Network","Not Connected");
            return false;
        }
    }


/////Checking for GPS Map connection on.........................................

    public boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);

            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
                return false;
            }

            if( locationMode != Settings.Secure.LOCATION_MODE_OFF){
                return true;
            }else
            {
                checkLocationEnabled();
                return false;
            }

        }else{
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            if(!TextUtils.isEmpty(locationProviders)){
                return true;
            }else{
                Toast.makeText(this, "Requires Android v. 4.4.4 or higher", Toast.LENGTH_SHORT).show();
                return false;
            }
        }


    }

    public void checkLocationEnabled(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Location Not Enabled");
        builder.setMessage("Please enable your location to continue");
        builder.setNegativeButton("close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void changeFragment(Fragment targetFragment){
        resideMenu.clearIgnoredViewList();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment, targetFragment, "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    // What good method is to access resideMenu？
    public ResideMenu getResideMenu(){
        return resideMenu;
    }
}

