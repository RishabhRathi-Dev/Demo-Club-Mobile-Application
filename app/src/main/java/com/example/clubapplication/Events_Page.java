package com.example.clubapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Events_Page extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private LinearLayout main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_page);

        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://club-application-64693-default-rtdb.asia-southeast1.firebasedatabase.app/");// This is deleted to avoid misuse
        FirebaseUser user =  mAuth.getCurrentUser();
        String userId = user.getUid();

        DatabaseReference mRef =  database.getReference().child("Events");

        mRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {

                ArrayList EventList = new ArrayList<String>();

                if (task.isSuccessful()){

                    if (task.getResult().exists()){
                        DataSnapshot dataSnapshot = task.getResult();

                        for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                            String eventkey  = dsp.getKey();
                            String eventname   = dsp.child("Name").getValue().toString();
                            String eventdate = dsp.child("Date").getValue().toString();
                            EventList.add(eventkey); //add result into array list

                            Log.d("Events", eventname);
                            Log.d("Events Date", eventdate);

                            createPanel(eventname, eventdate, eventkey);

                        }
                    }

                }

            }
        });;
    }

    public void createPanel(String eventname, String eventdate, String eventkey){
        LinearLayout main = findViewById(R.id.Events);

        // Container Layout
        LinearLayout top = new LinearLayout(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(15, 15, 15, 15);
        top.setLayoutParams(params);
        top.setOrientation(LinearLayout.VERTICAL);
        top.setPadding(20, 20, 20, 20);
        top.setGravity(Gravity.CENTER);
        GradientDrawable shape =  new GradientDrawable();
        shape.setCornerRadius(30);
        shape.setColor(Color.rgb(51, 187, 255));
        top.setBackground(shape);

        // Event Name
        TextView eventName = new TextView(this);
        eventName.setText(eventname);
        eventName.setTextSize(20);
        eventName.setPadding(5, 5, 5, 5);
        eventName.setGravity(Gravity.CENTER);
        eventName.setTypeface(null, Typeface.BOLD);

        // Event Date
        TextView eventDate = new TextView(this);
        eventDate.setText(eventdate);
        eventDate.setTextSize(17);
        eventDate.setPadding(5, 5, 5, 5);
        eventDate.setGravity(Gravity.CENTER);

        // Register Button
        Button register = new Button(this);
        register.setText("Register");
        register.setPadding(20, 10, 20, 10);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("Event Name", eventname);
                Log.d("Event Date", eventdate);
                FirebaseDatabase database = FirebaseDatabase.getInstance("https://club-application-64693-default-rtdb.asia-southeast1.firebasedatabase.app/");// This is deleted to avoid misuse
                FirebaseUser user =  mAuth.getCurrentUser();
                String userId = user.getUid();

                DatabaseReference mRef =  database.getReference().child("Users").child(userId).child("Events").child(eventkey);
                mRef.child("Date").setValue(eventdate);
                mRef.child("Name").setValue(eventname);
            }
        });

        // Compiling
        top.addView(eventName);
        top.addView(eventDate);
        top.addView(register);

        main.addView(top);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.profile:
                startActivity(new Intent(Events_Page.this, Profile_Page.class));
                finish();
                return true;

            case R.id.events:
                startActivity(new Intent(Events_Page.this, Events_Page.class));
                finish();
                return true;

            case R.id.yourevents:
                startActivity(new Intent(Events_Page.this, Your_Events_Page.class));
                finish();
                return true;

            case R.id.aboutus:
                startActivity(new Intent(Events_Page.this, About_Us_Page.class));
                finish();
                return true;

            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Events_Page.this, Login_Page.class));
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}