package com.drchip.ihelp;

import android.graphics.Bitmap;

public class User {
    public String username;
    public String email;
    public Bitmap profilePic;

    public User(String username, String email, Bitmap profilePic) {
        this.username = username;
        this.email = email;
        this.profilePic = profilePic;
    }

}
