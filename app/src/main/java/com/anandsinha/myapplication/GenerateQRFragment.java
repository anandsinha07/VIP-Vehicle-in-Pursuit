package com.anandsinha.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class GenerateQRFragment extends Fragment {

    private EditText text;
    private Button gen_btn;
    private ImageView image;
    private String txt2qr;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gen_qr, container, false);
        text=(EditText) view.findViewById(R.id.text);
        gen_btn=(Button) view.findViewById(R.id.gen_btn);
        image=(ImageView) view.findViewById(R.id.image);
        gen_btn.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {
                txt2qr = text.getText().toString().trim();
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
            }
        });


        return view;
    }

}
