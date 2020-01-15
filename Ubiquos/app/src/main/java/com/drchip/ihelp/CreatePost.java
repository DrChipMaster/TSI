package com.drchip.ihelp;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class CreatePost extends AppCompatActivity {

    EditText etTitle, etDescription;

    ImageView ivCreatePost, ivCancel, ivAddQRCode, ivAddImage, ivAddLocation, ivAddPhone, ivQRcode, ivImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);
        ivAddQRCode = findViewById(R.id.ivAddQRCode);
        etTitle = findViewById(R.id.etTitle);
        etDescription = findViewById(R.id.etDescription);
        ivCreatePost = findViewById(R.id.ivCreatePost);
        ivCancel = findViewById(R.id.ivCancel);
        ivAddImage = findViewById(R.id.ivAddImage);
        ivAddLocation = findViewById(R.id.ivAddLocation);
        ivAddPhone = findViewById(R.id.ivAddPhone);
        ivQRcode = findViewById(R.id.ivQRcode);
        ivImage = findViewById(R.id.ivImage);




        ivAddQRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ImageDownloaderTask(ivQRcode).execute("https://api.qrserver.com/v1/create-qr-code/?size=300x300&data=" + "pila");
            }
        });
    }
}
