package com.ncku_tainan.co2_detection;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

public class Home extends AppCompatActivity {

    private Button start_button;
    private Button Aboutus_button;
    private TextView Description_textView;
    private ImageView logo_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Button start_button = (Button)findViewById(R.id.start_button);
        Button Aboutus_button = (Button)findViewById(R.id.Aboutus_button);
        ImageView logo_image = (ImageView)findViewById(R.id.logo_image);

        //logo_image.setImageResource(R.drawable.logo);

        start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Home.this , MainActivity.class);
                startActivity(intent);
            }
        });
        Aboutus_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Home.this , AboutUs.class);
                startActivity(intent);
            }
        });

        Description_textView = (TextView)findViewById(R.id.Description_textView);
        Description_textView.setText("     Global carbon emission grew by 1.4% only in 2017, " +
                "and continuously increasing in the past 20 years  indicates the efforts to cope with carbon emission are still insufficient. " +
                "Most of climate disaster comes from the extreme weather caused by the greenhouse effect and gas. " +
                "This year, iGEM NCKU_Tainan designed a device piping the CO2 and converted such carbon source to biomass by integrated a non-native Calvin–Benson–Bassham cycle into E. coli. " +
                "“Of course” is a biological approach to “off” CO2 emission through the RuBisCO and PRK genes from Synchococcus sp, " +
                "which encode for major enzymes involved in carbon fixation,are incorporated into our chassis. Industrial gases enter the pipe (inlet) at the bottom, " +
                "flow through a ceramic nozzle and mix with liquids contained E. coli that consume CO2 in the cylindrical container " +
                "as well as sensing by Pasr promoter for determining the CO2 concentration with corresponding value of pH in terms of sfGFP. " +
                "Finally, our novel approach will  reduce CO2 and hope to slow down global climate change.");
    }
}
