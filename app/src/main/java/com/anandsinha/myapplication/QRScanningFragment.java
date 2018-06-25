package com.anandsinha.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class QRScanningFragment extends Fragment {
    private Button scan_btn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_qrscanning, container, false);
        scan_btn = (Button) view.findViewById(R.id.scan_btn);
        final Activity activity = getActivity();
        scan_btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                IntentIntegrator integrator = new IntentIntegrator(getActivity());
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt("scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("TAG",String.valueOf(resultCode));
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(result != null){
            if(result.getContents()==null) {
//                Toast.makeText(this,"You cancelled the scanning",Toast.LENGTH_LONG).show();
            }
            else {
                String s = (String) result.getContents();
                //Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();

                Log.d("TAG",String.valueOf(resultCode));
                Intent t = new Intent(new QRScanningFragment().getActivity().getApplicationContext(),PoliceResultQRActivity.class);
                t.putExtra("vehno",result.getContents());
                startActivity(t);


            }
        }
        else {

            super.onActivityResult(requestCode, resultCode, data);
        }
    }


}
