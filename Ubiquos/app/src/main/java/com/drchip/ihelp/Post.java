package com.drchip.ihelp;

public class Post {

    public long PostId;
    public String Author;
    public String Description;
    public String Title;
    public String ImagePath;
    public String Phone;
    public String Address;
    public String Date;
    public int Likes;


    public Post() {

    }

    public Post(long postId, String author, String description, String title) {
        PostId = postId;
        Author = author;
        Description = description;
        Title = title;
        Likes = 0;
    }

    public Post(long postId, String author, String description, String title, String imagePath, int likes) {
        PostId = postId;
        Author = author;
        Description = description;
        Title = title;
        ImagePath = imagePath;
        Likes = likes;
    }

    public Post(long postId, String author, String description, String title, String imagePath, String phone, int likes) {
        PostId = postId;
        Author = author;
        Description = description;
        Title = title;
        ImagePath = imagePath;
        Phone = phone;
        Likes = likes;
    }

    public Post(long postId, String author, String description, String title, String imagePath, String phone, String address, String date, int likes) {
        PostId = postId;
        Author = author;
        Description = description;
        Title = title;
        ImagePath = imagePath;
        Phone = phone;
        Address = address;
        Likes = likes;
        Date = date;

    }

}
