package com.ncku_tainan.co2_detection;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class AboutUs extends AppCompatActivity {

    private Button Back_button;
    private ImageView wiki_image;
    private ImageView FB_image;
    private ImageView IG_image;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        getSupportActionBar().hide(); //隱藏標題
        //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN); //隱藏狀態

        textView = findViewById(R.id.textView);
        textView.setText("12345");

        wiki_image = findViewById(R.id.wiki_image);
        wiki_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url="http://2018.igem.org/Team:NCKU_Tainan";
                Intent ie = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(ie);
            }
        });
        FB_image = findViewById(R.id.FB_image);
        FB_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url="https://www.facebook.com/igem.ncku/";
                Intent ie = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(ie);
            }
        });
        IG_image = findViewById(R.id.IG_image);
        IG_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url="https://www.instagram.com/igemncku/";
                Intent ie = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(ie);
            }
        });

        Back_button = findViewById(R.id.Back_button);
        Back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(AboutUs.this, Home.class);
                startActivity(intent);
            }
        });
    }
}
