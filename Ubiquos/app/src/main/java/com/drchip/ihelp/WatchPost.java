package com.drchip.ihelp;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

public class WatchPost extends AppCompatActivity implements OnMapReadyCallback {

    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
    private MapView mMapView;
    ImageView ivWPImage, ivQRcode;
    TextView tvTitle;
    RatingBar rbWatchPost;
    TextView tvReviewValue, tvDescription, tvContent, tvContact;
    LinearLayout linContact;
    private StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_post);
        ivWPImage = findViewById(R.id.ivWPImage);
        tvTitle = findViewById(R.id.tvTitle);
        rbWatchPost = findViewById(R.id.rbWatchPost);
        tvReviewValue = findViewById(R.id.tvReviewValue);
        tvDescription = findViewById(R.id.tvDescription);
        tvContent = findViewById(R.id.tvContent);
        tvContact = findViewById(R.id.tvContact);
        linContact = findViewById(R.id.linContact);
        ivQRcode = findViewById(R.id.ivQRcode);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        new ImageDownloaderTask(ivQRcode).execute("https://api.qrserver.com/v1/create-qr-code/?size=300x300&data=" + ApplicationClass.PostClicked.PostId);

        if (ApplicationClass.PostClicked.Phone.equals("null")) {
            linContact.setVisibility(View.GONE);
        } else tvContact.setText(ApplicationClass.PostClicked.Phone);

        // *** IMPORTANT ***
        // MapView requires that the Bundle you pass contain _ONLY_ MapView SDK
        // objects or sub-Bundles.
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }
        mMapView = findViewById(R.id.mapView);
        mMapView.onCreate(mapViewBundle);


        //

        tvTitle.setText(ApplicationClass.PostClicked.Title);
        tvContent.setText(ApplicationClass.PostClicked.Description);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        if (!ApplicationClass.PostClicked.ImagePath.equals("null")) {
            mStorageRef = FirebaseStorage.getInstance().getReference();
            StorageReference profile = mStorageRef.child("post/" + ApplicationClass.PostClicked.ImagePath + "" + "/image.jpg");

            try {
                final File finalFileImage = File.createTempFile("image", ".jpg");
                profile.getFile(finalFileImage)
                        .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                final Uri imageUri = Uri.parse(finalFileImage.toString());

                                try {
                                    Picasso.get().load(String.valueOf(finalFileImage.toURL())).into(ivWPImage);

                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                }


                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle failed download
                        // ...
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

        }


        //tvContent.setText(ApplicationClass.PostClicked.);


        mMapView.getMapAsync(this);
        new ImageDownloaderTask(ivQRcode).execute("https://api.qrserver.com/v1/create-qr-code/?size=300x300&data=" + ApplicationClass.PostClicked.PostId);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }

        mMapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mMapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mMapView.onStop();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        map.addMarker(new MarkerOptions().position(new LatLng(ApplicationClass.PostClicked.Latitude, ApplicationClass.PostClicked.Longitude)).title("Marker"));
        float zoomlever = 16.0f;
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(ApplicationClass.PostClicked.Latitude, ApplicationClass.PostClicked.Longitude), zoomlever));
        // map.addMarker(new MarkerOptions().position(new LatLng()));
    }

    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}



