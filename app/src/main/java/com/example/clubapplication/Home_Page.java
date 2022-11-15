package com.example.clubapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Console;


public class Home_Page extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private TextView t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        t = findViewById(R.id.textView);

        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://club-application-64693-default-rtdb.asia-southeast1.firebasedatabase.app/");// This is deleted to avoid misuse
        FirebaseUser user =  mAuth.getCurrentUser();
        String userId = user.getUid();

        DatabaseReference mRef =  database.getReference().child("Users").child(userId);

        mRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {

                if (task.isSuccessful()){

                    if (task.getResult().exists()){
                        DataSnapshot dataSnapshot = task.getResult();
                        String fullname = String.valueOf(dataSnapshot.child("fullname").getValue());
                        String email = String.valueOf(dataSnapshot.child("email").getValue());
                        Log.d("Name", fullname);

                        t.setText("Okaerinasai \n"+fullname+" - sama");

                    }

                }

            }
        });;
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
                startActivity(new Intent(Home_Page.this, Profile_Page.class));
                return true;

            case R.id.events:
                startActivity(new Intent(Home_Page.this, Events_Page.class));
                return true;

            case R.id.yourevents:
                startActivity(new Intent(Home_Page.this, Your_Events_Page.class));
                return true;

            case R.id.aboutus:
                startActivity(new Intent(Home_Page.this, About_Us_Page.class));
                return true;

            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Home_Page.this, Login_Page.class));
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}