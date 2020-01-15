package com.drchip.ihelp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileNotFoundException;
import java.io.InputStream;

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

                message.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // etMessage.setText(etReleaseMessage.getText());
                    }
                });
                message.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                message.show();

            }
        });


        ivAddLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder message = new AlertDialog.Builder(CreatePost.this);
                LayoutInflater inflater = getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.adress_input, null);
                final EditText etAdress = dialogView.findViewById(R.id.etAdrress);

                message.setView(dialogView);
                message.setTitle("Adrress");
                message.setMessage("Enter your adress here here!!");

                message.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // etMessage.setText(etReleaseMessage.getText());
                    }
                });
                message.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                message.show();
            }
        });



        ivAddQRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ImageDownloaderTask(ivQRcode).execute("https://api.qrserver.com/v1/create-qr-code/?size=300x300&data=" + "pila");
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE) {
            if (data == null) {
                //Display an error
                return;
            }
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                ivImage.setImageBitmap(selectedImage);

                // mDatabase.child("users").child(ApplicationClass.currentUser.getUid()).setValue(new User(ApplicationClass.currentUser.getDisplayName(), ApplicationClass.currentUser.getEmail(), selectedImage));

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            //TODO: action
        }
    }
}
