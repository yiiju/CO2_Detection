package com.ncku_tainan.co2_detection;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity{

    private ImageButton CO2_concentrration_button;
    private ImageButton pH_button;
    private ImageButton temperature_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide(); //隱藏標題
        //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN); //隱藏狀態

        CO2_concentrration_button = findViewById(R.id.CO2_concentrration_button);
        pH_button = findViewById(R.id.pH_button);
        temperature_button = findViewById(R.id.temperature_button);

        CO2_concentrration_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this , Concentration.class);
                startActivity(intent);
            }
        });
        pH_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this , pH_value.class);
                startActivity(intent);
            }
        });
        temperature_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this , Temperature.class);
                startActivity(intent);
            }
        });
    }
}
