package com.drchip.ihelp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class CreatePost extends AppCompatActivity {

    EditText etTitle, etDescription;
    String imagePath, contact, adress;

    public static final int PICK_IMAGE = 1;
    ImageView ivCreatePost, ivCancel, ivAddQRCode, ivAddImage, ivAddLocation, ivAddPhone, ivQRcode, ivImage;
    DatabaseReference mDatabase;
    long postid;
    String formattedDate;
    boolean image;
    Uri imageUri;
    InputStream imageStream;

    Bitmap selectedImage;
    private StorageReference mStorageRef;
    boolean finished;
    ProgressDialog progressDialog;
    Location loc;
    private FusedLocationProviderClient clientLoc;


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
        mDatabase = FirebaseDatabase.getInstance().getReference();
        clientLoc = LocationServices.getFusedLocationProviderClient(this);

        getLocation(clientLoc);
        imagePath = "null";
        contact = "null";
        adress = "null";

        progressDialog = new ProgressDialog(this, R.style.AppCompatAlertDialogStyle);

        ivAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");

                startActivityForResult(photoPickerIntent, PICK_IMAGE);
            }
        });


        DatabaseReference ref = mDatabase.child("LastPost");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                postid = dataSnapshot.getValue(long.class);
                postid++;

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        mStorageRef = FirebaseStorage.getInstance().getReference();

        ivCreatePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finished = false;

                if (etTitle.getText().toString().isEmpty() || etDescription.getText().toString().isEmpty()) {
                    Toast.makeText(CreatePost.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                } else {

                    progressDialog.setTitle("Posting post");
                    progressDialog.setMessage("Connecting with server...please wait");
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                    formattedDate = df.format(c.getTime());


                            String id = postid + "";
                            if (image) imagePath = id;
                    Post newPost = new Post(postid, ApplicationClass.currentUser.getUid(), etDescription.getText().toString(), etTitle.getText().toString(), imagePath, contact, adress, formattedDate, 0, loc);
                            DatabaseReference trans = mDatabase.child("Posts").child(id);
                            DatabaseReference ref = mDatabase.child("LastPost");
                            ref.setValue(postid);


                            trans.setValue(newPost);
                    if (image) {
                        StorageReference riversRef = mStorageRef.child("post/" + postid + "/image.jpg");
                        riversRef.putFile(imageUri)
                                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        // Get a URL to the uploaded content
                                        Toast.makeText(CreatePost.this, "Done", Toast.LENGTH_SHORT).show();
                                        if (finished) {
                                            progressDialog.hide();
                                            startActivity(new Intent(CreatePost.this, MainActivity.class));
                                            CreatePost.this.finish();
                                        } else finished = true;
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                        // Handle unsuccessful uploads
                                        // ...
                                    }
                                });
                    }
                    NetworkInfo info = new NetworkInfo(CreatePost.this);

                    String[] macs = info.getBssidList_toString();

                    DatabaseReference user = mDatabase.child("UserPosts").child(ApplicationClass.currentUser.getUid()).push();
                    user.setValue(postid);

                    for (String mac : macs) {
                        DatabaseReference aux = mDatabase.child("Mac").child(mac).push();
                        aux.setValue(postid).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (finished) {
                                    progressDialog.hide();
                                    startActivity(new Intent(CreatePost.this, MainActivity.class));
                                    CreatePost.this.finish();
                                } else finished = true;
                            }
                        });

                    }

                    String PathGPS = (int) (loc.getLatitude() * 10000) + " " + (int) (loc.getLongitude() * 10000);

                    PathGPS = PathGPS.replace('-', 'A').replace('.', '_');
                    DatabaseReference local = mDatabase.child("GPS").child(PathGPS).push();
                    local.setValue(postid);













                }

            }
        });

        ivAddPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder message = new AlertDialog.Builder(CreatePost.this);
                LayoutInflater inflater = getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.phone_number, null);
                final EditText etPhoneNumber = dialogView.findViewById(R.id.etPhoneNumber);

                message.setView(dialogView);
                message.setTitle("Contact");
                message.setMessage("Enter your contact here!!");

                message.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        contact = etPhoneNumber.getText().toString();

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

                        adress = etAdress.getText().toString();
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
                new ImageDownloaderTask(ivQRcode).execute("https://api.qrserver.com/v1/create-qr-code/?size=300x300&data=" + postid);
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
                imageUri = data.getData();
                imageStream = getContentResolver().openInputStream(imageUri);
                selectedImage = BitmapFactory.decodeStream(imageStream);
                ivImage.setImageBitmap(selectedImage);


                image = true;



                // mDatabase.child("users").child(ApplicationClass.currentUser.getUid()).setValue(new User(ApplicationClass.currentUser.getDisplayName(), ApplicationClass.currentUser.getEmail(), selectedImage));

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            //TODO: action
        }
    }

    private void getLocation(FusedLocationProviderClient client) {
        if (ActivityCompat.checkSelfPermission(CreatePost.this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        client.getLastLocation().addOnSuccessListener(CreatePost.this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    Context context = getApplicationContext();
                    CharSequence text = "Latitude: " + location.getLatitude() + "\nLongitude: " + location.getLongitude();
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    loc = location;

                }
            }
        });
    }

}
