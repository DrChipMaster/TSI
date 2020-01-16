package com.drchip.ihelp;

import android.app.Application;

import com.google.firebase.auth.FirebaseUser;

public class ApplicationClass extends Application {
    public static FirebaseUser currentUser;
    public static User mainuser;
    public static Post PostClicked;
    //  public static

    public void onCreate() {


        super.onCreate();

    }

}
