package com.ncku_tainan.co2_detection;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button CO2_concentrration_button;
    private Button pH_button;
    private Button Pressure_button;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide(); //隱藏標題
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN); //隱藏狀態

        CO2_concentrration_button = (Button)findViewById(R.id.CO2_concentrration_button);
        pH_button = (Button)findViewById(R.id.pH_button);
        Pressure_button = (Button)findViewById(R.id.Pressure_button);
        textView = (TextView)findViewById(R.id.textView);

        CO2_concentrration_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                textView.setText("123");
            }
        });
        pH_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                textView.setText("456");
            }
        });
        Pressure_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                textView.setText("789");
            }
        });
    }
}
