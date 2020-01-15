package com.drchip.ihelp;

public class Post {

    public String Author;
    public String Description;
    public String Title;
    public String ImagePath;
    public String Phone;
    public String Address;
    public int Likes;


    public Post() {

    }

    public Post(String author, String description, String title) {
        Author = author;
        Description = description;
        Title = title;
        Likes = 0;
    }

    public Post(String author, String description, String title, String imagePath, int likes) {
        Author = author;
        Description = description;
        Title = title;
        ImagePath = imagePath;
        Likes = likes;
    }

    public Post(String author, String description, String title, String imagePath, String phone, int likes) {
        Author = author;
        Description = description;
        Title = title;
        ImagePath = imagePath;
        Phone = phone;
        Likes = likes;
    }

    public Post(String author, String description, String title, String imagePath, String phone, String address, int likes) {
        Author = author;
        Description = description;
        Title = title;
        ImagePath = imagePath;
        Phone = phone;
        Address = address;
        Likes = likes;

    }

}
