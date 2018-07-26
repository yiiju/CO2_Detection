package com.ncku_tainan.co2_detection;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Concentration extends AppCompatActivity implements ChildEventListener {

    private TextView textView;
    SimpleDateFormat sdf2 = new SimpleDateFormat("HH");
    String hour = sdf2.format(new java.util.Date());
    SimpleDateFormat sdf3 = new SimpleDateFormat("DD");
    String date = sdf3.format(new java.util.Date());

    ConnectivityManager cm;
    NetworkInfo NetInfo;

    LineChart mChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concentration);
        getSupportActionBar().hide(); //隱藏標題
        //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN); //隱藏狀態

        textView = findViewById(R.id.textView);

        cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetInfo = cm.getActiveNetworkInfo();
        if (NetInfo == null) {
            Toast.makeText(getApplicationContext(), "Offline status", Toast.LENGTH_SHORT).show();
            textView.setText("Not connected to the internet.");
        } else {
            if (NetInfo.isConnected()) {
                Toast.makeText(getApplicationContext(), "Connect to the internet", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Offline", Toast.LENGTH_SHORT).show();
            }
        }

        SimpleDateFormat sdf = new SimpleDateFormat("MM");
        String date = sdf.format(new java.util.Date());

        FirebaseDatabase fireDB = FirebaseDatabase.getInstance();
        DatabaseReference month = fireDB.getReference(date);
        month.addChildEventListener(this);

        mChart = (LineChart) findViewById(R.id.chart);
        initChart();
        initData();
    }
    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        //int size = (int)dataSnapshot.getChildrenCount();
        String concentration;
        String source;
        if (NetInfo == null) {
            textView.setText("Not connected to the internet.");
        }
        else {
            if (dataSnapshot.child(hour + ":25").child("concentration").getValue() != null) {
                concentration = dataSnapshot.child(hour + ":25").child("concentration").getValue() + "";
                source = "CO<small>2</small> concentration：" + concentration + "%";
                textView.setText(Html.fromHtml(source));
            } else {
                source = "There is no data.";
                textView.setText(source);
            }
        }
    }
    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) { }
    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) { }
    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) { }
    @Override
    public void onCancelled(DatabaseError databaseError) { }

    private void initChart() {
        Description description = new Description();
        description.setText("CO2 concentration");
        description.setTextColor(Color.WHITE);
        mChart.setDescription(description);
        mChart.setBorderColor(Color.GRAY);
        mChart.setBorderWidth(1f);
        mChart.animateXY(1000, 1000);

        //set description of the line
        Legend legend = mChart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setTextColor(Color.WHITE);
        legend.setWordWrapEnabled(true);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisLineColor(Color.WHITE);
        xAxis.setTextColor(Color.WHITE);

        YAxis axisLeft = mChart.getAxisLeft();
        axisLeft.setAxisLineColor(Color.WHITE);
        axisLeft.setTextColor(Color.WHITE);
        YAxis axisRight = mChart.getAxisRight();
        axisRight.setEnabled(false);
    }

    private void initData() {
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
        lineDataSet1.setHighLightColor(Color.WHITE);
        mChart.setData(new LineData(lineDataSets));
        mChart.setVisibleXRangeMaximum(25f);
    }
}
