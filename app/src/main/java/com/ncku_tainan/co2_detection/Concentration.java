package com.ncku_tainan.co2_detection;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.text.SimpleDateFormat;

public class Concentration extends AppCompatActivity  implements ChildEventListener{

    private TextView textView;
    SimpleDateFormat sdf2 = new SimpleDateFormat("HH");
    String hour = sdf2.format(new java.util.Date());
    SimpleDateFormat sdf3 = new SimpleDateFormat("DD");
    String date = sdf3.format(new java.util.Date());

    ConnectivityManager cm;
    NetworkInfo NetInfo;

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
}
