package com.drchip.ihelp;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class CreatePost extends AppCompatActivity {

    EditText etTitle, etDescription;

    ImageView ivAddQRCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);
        ivAddQRCode = findViewById(R.id.ivAddQRCode);

        ivAddQRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
