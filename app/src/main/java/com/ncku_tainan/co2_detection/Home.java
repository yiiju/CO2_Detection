package com.ncku_tainan.co2_detection;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;
import android.text.Html;

public class Home extends AppCompatActivity {

    private Button start_button;
    private Button Aboutus_button;
    private TextView Description_textView;
    private ImageView logo_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getSupportActionBar().hide(); //隱藏標題
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN); //隱藏狀態

        Button start_button = (Button)findViewById(R.id.start_button);
        Button Aboutus_button = (Button)findViewById(R.id.Aboutus_button);
        ImageView logo_image = (ImageView)findViewById(R.id.logo_image);

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

        TextView Description_textView = (TextView)findViewById(R.id.Description_textView);
        String source = "We designed a device piping the CO<small>2</small> and converted such carbon source to biomass by integrated a non-native Calvin–Benson–Bassham cycle into <i>E. coli</i>. " +
                "“Of course” is a biological approach to “off” CO<small>2</small> emission through the RuBisCO and PRK genes from <i>Synchococcus sp</i>, " +
                "which encode for major enzymes involved in carbon fixation, are incorporated into our chassis. " +
                "Industrial gases enter the pipe (inlet) at the bottom, flow through a ceramic nozzle and mix with liquids contained <i>E. coli</i> that consume CO<small>2</small> in the cylindrical container " +
                "as well as sensing by P<small>asr</small> promoter for determining the CO<small>2</small> concentration with corresponding value of pH in terms of sfGFP. ";
        Description_textView.setText(Html.fromHtml(source));
    }
}
