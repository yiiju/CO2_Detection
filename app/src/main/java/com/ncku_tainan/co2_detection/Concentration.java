package com.ncku_tainan.co2_detection;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Concentration extends AppCompatActivity implements ChildEventListener {

    SwipeRefreshLayout mySwipeRefreshLayout;
    private TextView textView;
    SimpleDateFormat sdf = new SimpleDateFormat("MM");
    String sdfmonth = sdf.format(new java.util.Date());
    SimpleDateFormat sdf2 = new SimpleDateFormat("HH");
    String hour = sdf2.format(new java.util.Date());
    SimpleDateFormat sdf3 = new SimpleDateFormat("dd");
    String date = sdf3.format(new java.util.Date());
    SimpleDateFormat sdf4 = new SimpleDateFormat("ss");
    String second = sdf4.format(new java.util.Date());
    SimpleDateFormat sdf5 = new SimpleDateFormat("ss");
    String minute = sdf5.format(new java.util.Date());

    ConnectivityManager cm;
    NetworkInfo NetInfo;

    FirebaseDatabase fireDB;
    DatabaseReference month;

    LineChart mChart;
    int temphour;
    int tempdate;
    int tempsec;
    int tempmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concentration);
        getSupportActionBar().hide(); // hide the title
        //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN); // hide the state

        textView = findViewById(R.id.textView);
        checkNet();

        mySwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        mySwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                myUpdateOperation();
            }
        });

        // assign color of progress animation
        // color will loop in order
        mySwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light,
                android.R.color.holo_blue_light,
                android.R.color.holo_purple
        );

        getData();
        mChart = (LineChart) findViewById(R.id.chart);
        initChart();
        mChart.setNoDataText("No chart data available or press the screen to see chart.");
    }

    private void checkNet() {
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
    }

    private void myUpdateOperation()
    {
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                mySwipeRefreshLayout.setRefreshing(false);
                checkNet();
                getData();
            }
        }, 3000);    // 3 second
    }

    private void getData() {
        fireDB = FirebaseDatabase.getInstance();
        month = fireDB.getReference(sdfmonth);
        month.addChildEventListener(this);
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        String concentration;
        String source;
        if (NetInfo == null) {
            textView.setText("Not connected to the internet.");
        }
        else {
            if ((dataSnapshot.getKey().equals(date)) && dataSnapshot.child(hour + ":" + minute + ":" + second).child("concentration").getValue() != null) {
                concentration = dataSnapshot.child(hour + ":" + minute + ":" + second).child("concentration").getValue() + "";
                source = "CO<small>2</small> concentration：" + concentration + "%";
                textView.setText(Html.fromHtml(source));
                if(Float.parseFloat(concentration) > 5) {
                    noticed();
                }
            } else {
                source = "There is no real time data.";
                textView.setText(source);
            }
        }
        initData(dataSnapshot);
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

    private void initData(DataSnapshot dataSnapshot) {
        temphour = Integer.valueOf(hour);
        tempdate = Integer.valueOf(date);
        tempmin = Integer.valueOf(minute);
        tempsec = Integer.valueOf(second);
        final ArrayList<String> xVals = new ArrayList<>();
        ArrayList<Entry> yAXESconcentration = new ArrayList<>();
        String concentration;
        String chDate;
        String chHour;
        String chMin;
        String chSec;
        int numDataPoints = 100;
        if (NetInfo == null) {
            mChart.setNoDataText("Not connected to the network.");
        }
        else {
            for(int i=0;i < numDataPoints-1 ;i++) {
                if(tempsec < 10) {
                    chSec = "0" + Integer.toString(tempsec);
                } else chSec = Integer.toString(tempsec);
                if(tempmin < 10) {
                    chMin = "0" + Integer.toString(tempmin);
                } else chMin = Integer.toString(tempmin);
                if(tempdate < 10) {
                    chDate = "0" + Integer.toString(tempdate);
                } else chDate = Integer.toString(tempdate);
                if(temphour < 10) {
                    chHour = "0" + Integer.toString(temphour);
                } else chHour = Integer.toString(temphour);
                if(tempsec > 10) tempsec -= 10;
                else {
                    if(tempmin > 1) {
                        tempsec = 50;
                        tempmin--;
                    }
                    else {
                        if(temphour > 1) {
                            tempmin = 59;
                            temphour--;
                        }
                        else {
                            temphour = 23;
                            tempdate--;
                        }
                    }
                }
            }
            for(int i = 0; i < numDataPoints; i++) {
                if(tempsec < 10) {
                    chSec = "0" + Integer.toString(tempsec);
                } else chSec = Integer.toString(tempsec);
                if(tempmin < 10) {
                    chMin = "0" + Integer.toString(tempmin);
                } else chMin = Integer.toString(tempmin);
                if(tempdate < 10) {
                    chDate = "0" + Integer.toString(tempdate);
                } else chDate = Integer.toString(tempdate);
                if(temphour < 10) {
                    chHour = "0" + Integer.toString(temphour);
                } else chHour = Integer.toString(temphour);

                if((dataSnapshot.getKey().equals(chDate)) && (dataSnapshot.child(chHour + ":" + chMin + ":" + chSec).child("concentration").getValue() != null)) {
                    concentration = dataSnapshot.child(chHour + ":" + chMin + ":" + chSec).child("concentration").getValue() + "";
                    xVals.add(chHour + ":" + chMin + ":" + chSec);
                    yAXESconcentration.add(new Entry(i, Float.parseFloat(concentration)));
                }
                else {
                    xVals.add(chHour + ":" + chMin + ":" + chSec);
                    yAXESconcentration.add(new Entry(i, 0));
                }
                if(tempsec < 50) tempsec += 10;
                else {
                    if (tempmin < 59) {
                        tempsec = 00;
                        tempmin++;
                    } else {
                        if (temphour < 23) {
                            tempmin = 00;
                            temphour++;
                        } else {
                            temphour = 00;
                            tempdate++;
                        }
                    }
                }
            }
        }

        ArrayList<ILineDataSet> lineDataSets = new ArrayList<>();

        LineDataSet lineDataSet1 = new LineDataSet(yAXESconcentration,"CO2 concentration");
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

        // the labels that should be drawn on the XAxis
        IAxisValueFormatter formatter = new IAxisValueFormatter() {

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return xVals.get((int) value);
            }
        };
        XAxis xAxis = mChart.getXAxis();
        xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
        xAxis.setValueFormatter(formatter);
    }

    private void noticed() {
        NotificationManager manager = (NotificationManager)
                getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this);
        Intent notificationIntent = new Intent(this,Concentration.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent contentIntent = PendingIntent.getActivity(this,0,notificationIntent,0);

        builder.setDefaults(Notification.DEFAULT_ALL);
        String text = "CO<sub>2</sub> Concentration";
        builder.setSmallIcon(R.drawable.wiki_logo)
                .setWhen(System.currentTimeMillis())
                .setContentTitle(Html.fromHtml(text) + " Warning")
                .setContentText(Html.fromHtml(text) + " is abnormal in " + hour + " o'clock.")
                .setContentIntent(contentIntent);

        Notification notification = builder.build();
        // The notification will disappear automatically when user clicks it
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        manager.notify(3, notification);
        //manager.cancel(1);
    }
}
