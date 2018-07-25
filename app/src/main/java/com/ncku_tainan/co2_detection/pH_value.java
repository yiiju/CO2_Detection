package com.ncku_tainan.co2_detection;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

public class pH_value  extends AppCompatActivity {

    LineChart mChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p_h_value);
        getSupportActionBar().hide(); //隱藏標題
        //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN); //隱藏狀態

        mChart = (LineChart) findViewById(R.id.chart);
        mChart.setViewPortOffsets(10,10,10,10);
        XAxis xAxis = mChart.getXAxis();

        ArrayList<String> xAXES = new ArrayList<>();
        ArrayList<Entry> yAXEScos = new ArrayList<>();
        double x = 0;
        int numDataPoints = 500;
        for(int i=0;i<numDataPoints;i++) {
            float cosFunction = Float.parseFloat(String.valueOf(Math.cos(x)));
            x = x + 0.5;
            yAXEScos.add(new Entry(i,cosFunction));
            xAXES.add(i,String.valueOf(x));
        }
        String[] xaxes = new String[xAXES.size()];
        for(int i=0;i<xAXES.size();i++) {
            xaxes[i] = xAXES.get(i).toString();
        }
        ArrayList<ILineDataSet> lineDataSets = new ArrayList<>();

        LineDataSet lineDataSet1 = new LineDataSet(yAXEScos,"cos");
        lineDataSet1.setDrawCircles(true);
        lineDataSet1.setColor(0xFFFFA036);
        lineDataSet1.setCircleColor(0xFFFFA036);
        lineDataSet1.setValueTextColor(Color.WHITE);
        lineDataSet1.setValueTextSize(12f);
        lineDataSet1.setLineWidth(2.5f);

        lineDataSets.add(lineDataSet1);

        mChart.setData(new LineData(lineDataSets));
        mChart.setVisibleXRangeMaximum(65f);

    }
}

