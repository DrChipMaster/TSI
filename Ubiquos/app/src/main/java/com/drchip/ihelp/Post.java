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
    public int Rating;
    public int Reviews;


    public Post() {

    }

    public Post(long postId, String author, String description, String title) {
        PostId = postId;
        Author = author;
        Description = description;
        Title = title;
        Likes = 0;
        Rating = 0;
        Reviews = 0;
    }

    public Post(long postId, String author, String description, String title, String imagePath, int likes) {
        PostId = postId;
        Author = author;
        Description = description;
        Title = title;
        ImagePath = imagePath;
        Likes = likes;
        Rating = 0;
        Reviews = 0;
    }

    public Post(long postId, String author, String description, String title, String imagePath, String phone, int likes) {
        PostId = postId;
        Author = author;
        Description = description;
        Title = title;
        ImagePath = imagePath;
        Phone = phone;
        Likes = likes;
        Rating = 0;
        Reviews = 0;
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
        Rating = 0;
        Reviews = 0;

    }

}
