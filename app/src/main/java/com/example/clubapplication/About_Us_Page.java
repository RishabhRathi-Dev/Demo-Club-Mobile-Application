package com.example.clubapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

public class About_Us_Page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us_page);
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
                startActivity(new Intent(About_Us_Page.this, Profile_Page.class));
                finish();
                return true;

            case R.id.events:
                startActivity(new Intent(About_Us_Page.this, Events_Page.class));
                finish();
                return true;

            case R.id.yourevents:
                startActivity(new Intent(About_Us_Page.this, Your_Events_Page.class));
                finish();
                return true;

            case R.id.aboutus:
                startActivity(new Intent(About_Us_Page.this, About_Us_Page.class));
                finish();
                return true;

            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(About_Us_Page.this, Login_Page.class));
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}