package com.example.clubapplication;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Collections;
import java.util.Date;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

public class User implements Parcelable {

    public String fullname;
    public String email;
    public List eventsParticipated;
    private DatabaseReference mDatabase;


    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String fullname, String email) {
        this.fullname = fullname;
        this.email = email;
        this.eventsParticipated = Collections.emptyList();

    }

    protected User(Parcel in) {
        fullname = in.readString();
        email = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public void addEventsParticipated (String eventName) {
        this.eventsParticipated.add(eventName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.fullname);
        parcel.writeString(this.email);

    }
}
