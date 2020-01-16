package com.drchip.ihelp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karan.churi.PermissionManager.PermissionManager;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.security.Permission;

public class MainActivity extends AppCompatActivity {

    PermissionManager permissionManager;

    private AppBarConfiguration mAppBarConfiguration;
    public static final int PICK_IMAGE = 1;
    ImageView ivPhoto, ivMenuLogo;
    DatabaseReference mDatabase;
    File localFile = null;
    private StorageReference mStorageRef;
    TextView tvEmail, tvName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ivMenuLogo = findViewById(R.id.ivMenuLogo);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fabCreatePost = findViewById(R.id.fabCreatePost);

        fabCreatePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CreatePost.class));
                MainActivity.this.finish();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        View headerView = navigationView.getHeaderView(0);
        mDatabase = FirebaseDatabase.getInstance().getReference();
       // mDatabase.child("users").child(ApplicationClass.currentUser.getUid()).setValue(new User("",ApplicationClass.currentUser.getEmail(),selectedImage));
        mStorageRef = FirebaseStorage.getInstance().getReference();
        ivPhoto= headerView.findViewById(R.id.ivPhoto);
        tvName = headerView.findViewById(R.id.tvName);
        tvEmail = headerView.findViewById(R.id.tvEmail);
        mDatabase.child("users").child(ApplicationClass.currentUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ApplicationClass.mainuser = dataSnapshot.getValue(User.class);
                if (ApplicationClass.mainuser != null) {
                    String name = ApplicationClass.mainuser.username;

                    if (name.isEmpty())
                        tvName.setText("Click here to add your name");
                    else {
                        tvName.setText(name);
                    }

                    tvEmail.setText(ApplicationClass.mainuser.email);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        ivPhoto.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");

               // Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
                //chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

                startActivityForResult(photoPickerIntent, PICK_IMAGE);
            }
        });


        StorageReference profile = mStorageRef.child("images/" + ApplicationClass.currentUser.getUid() + "/profile.jpg");


        try {
            localFile = File.createTempFile("profile", "jpg");
            profile.getFile(localFile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            final Uri imageUri = Uri.parse(localFile.toString());

                            //final Uri imageUri = finalLocalFile.toURI();
                            try {
                                Picasso.get().load(String.valueOf(localFile.toURL())).into(ivPhoto);

                                //Bitmap ola = Picasso.load(String.valueOf(localFile.toURL())).get();
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                                Toast.makeText(MainActivity.this, "Error getting the profile picture", Toast.LENGTH_SHORT).show();
                            }


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle failed download
                    // ...
                    Toast.makeText(MainActivity.this, "Error getting the profile photo", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.scanola: {
                startActivity(new Intent(getApplicationContext(), ScanCodeActivity.class));
                MainActivity.this.finish();

            }
                break;
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this, Login.class));
                MainActivity.this.finish();
                break;


        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
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
                ivPhoto.setImageBitmap(selectedImage);
                StorageReference riversRef = mStorageRef.child("images/"+ApplicationClass.currentUser.getUid()+"/profile.jpg");
                riversRef.putFile(imageUri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                // Get a URL to the uploaded content
                                Toast.makeText(MainActivity.this, "Done", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle unsuccessful uploads
                                // ...
                            }
                        });
                // mDatabase.child("users").child(ApplicationClass.currentUser.getUid()).setValue(new User(ApplicationClass.currentUser.getDisplayName(), ApplicationClass.currentUser.getEmail(), selectedImage));

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            //TODO: action
        }
    }
}
