package com.ncku_tainan.co2_detection;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concentration);
        getSupportActionBar().hide(); //隱藏標題
        //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN); //隱藏狀態

        SimpleDateFormat sdf = new SimpleDateFormat("MM");
        String date = sdf.format(new java.util.Date());

        textView = (TextView)findViewById(R.id.textView);
        FirebaseDatabase fireDB = FirebaseDatabase.getInstance();
        DatabaseReference month = fireDB.getReference(date);
        month.addChildEventListener(this);
    }
    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        int size = (int)dataSnapshot.getChildrenCount();
        String concentration;
        concentration = dataSnapshot.child(hour+":25").child("concentration").getValue()+"";
        String source = "CO<small>2</small> concentration：" + concentration + "%";
        if(concentration == null) source = "目前沒有資料";
        textView.setText(Html.fromHtml(source));
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
