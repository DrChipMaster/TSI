package com.drchip.ihelp.ui.userprofile;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.drchip.ihelp.ApplicationClass;
import com.drchip.ihelp.NetworkInfo;
import com.drchip.ihelp.Post;
import com.drchip.ihelp.PostAdapter;
import com.drchip.ihelp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class UserProfileFragment extends Fragment {
    private UserProfileViewModel userProfileViewModel;
    Button btnLikedPublications;
    Button btnUserPublications;
    TextView tvTitle;
    ImageView ivProfilePic;
    RecyclerView rvUserPublications;
    RecyclerView rvLikedPublications;
    File localFile = null;
    private StorageReference mStorageRef;

    private DatabaseReference mDatabase;
    //RecyclerView recyclerView;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Long> postsID;

    RecyclerView.Adapter likedPostsAdapter;
    RecyclerView.LayoutManager likedPostslayoutManager;
    ArrayList<Post> myPosts;
    ArrayList<Long> likedPostsID;
    ArrayList<Post> myLikedPosts;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        userProfileViewModel = ViewModelProviders.of(this).get(UserProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_user_profile, container, false);

        rvUserPublications = root.findViewById(R.id.rvUserPublications);
        rvUserPublications.setHasFixedSize(false);
        layoutManager = new LinearLayoutManager(getContext());
        rvUserPublications.setLayoutManager(layoutManager);

        rvLikedPublications = root.findViewById(R.id.rvLikedPublications);
        rvLikedPublications.setHasFixedSize(false);
        likedPostslayoutManager = new LinearLayoutManager(getContext());
        rvLikedPublications.setLayoutManager(likedPostslayoutManager);

        btnUserPublications = root.findViewById(R.id.btnPosts);
        btnLikedPublications = root.findViewById(R.id.btnLiked);
        tvTitle = root.findViewById(R.id.tvTitle);
        tvTitle.setText(ApplicationClass.mainuser.username);
        ivProfilePic = root.findViewById(R.id.ivProfilePic);
        mStorageRef = FirebaseStorage.getInstance().getReference();

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
                                Picasso.get().load(String.valueOf(localFile.toURL())).into(ivProfilePic);
                                //Bitmap ola = Picasso.load(String.valueOf(localFile.toURL())).get();
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                                Toast.makeText(getContext(), "Error getting the profile picture", Toast.LENGTH_SHORT).show();
                            }


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle failed download
                    // ...
                    Toast.makeText(getContext(), "Error getting the profile photo", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        btnLikedPublications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnLikedPublications.setBackgroundResource(R.drawable.user_profile_on_left);
                btnUserPublications.setBackgroundResource(R.drawable.user_profile_off_right);
                rvUserPublications.setVisibility(View.GONE);
                rvLikedPublications.setVisibility(View.VISIBLE);
            }
        });

        btnUserPublications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnUserPublications.setBackgroundResource(R.drawable.user_profile_on_right);
                btnLikedPublications.setBackgroundResource(R.drawable.user_profile_off_left);
                rvUserPublications.setVisibility(View.VISIBLE);
                rvLikedPublications.setVisibility(View.GONE);
            }
        });



        /* LIST USER POSTS */
        mDatabase = FirebaseDatabase.getInstance().getReference();
        myPosts = new ArrayList<>();
        Query myTopPostsQuery = mDatabase.child("UserPosts").child(ApplicationClass.currentUser.getUid()).orderByChild("value");
        postsID = new ArrayList<>();

        myTopPostsQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshots) {
                for (DataSnapshot dataSnap : dataSnapshots.getChildren()) {
                    boolean exits = false;

                    for (int i = 0; i < postsID.size(); i++) {
                        long aux = dataSnap.getValue(long.class);
                        if (postsID.get(i) == aux) {
                            exits = true;
                            break;
                        }

                    }
                    if (!exits) {
                        postsID.add(dataSnap.getValue(long.class));
                    }
                }
                for (long postID : postsID) {
                    Query myTopPostsQuery = mDatabase.child("Posts").child(postID + "");
                    myTopPostsQuery.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            boolean exits = false;
                            Post aux = dataSnapshot.getValue(Post.class);
                            for (int i = 0; i < myPosts.size(); i++) {

                                if (myPosts.get(i).PostId == aux.PostId) {
                                    myPosts.set(i, aux);
                                    exits = true;
                                }

                            }
                            if (!exits)
                                myPosts.add(aux);
                            myAdapter.notifyDataSetChanged();


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        myAdapter = new PostAdapter(getContext(), myPosts);
        rvUserPublications.setAdapter(myAdapter);

        /* LIST USER LIKED POSTS */
        myLikedPosts = new ArrayList<>();
        Query myLikedPostsQuery = mDatabase.child("User_likes").child(ApplicationClass.currentUser.getUid()).orderByChild("value");
        likedPostsID = new ArrayList<>();

        myLikedPostsQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshots) {
                for (DataSnapshot dataSnap : dataSnapshots.getChildren()) {
                    boolean exits = false;

                    for (int i = 0; i < likedPostsID.size(); i++) {
                        long aux = dataSnap.getValue(long.class);
                        if (likedPostsID.get(i) == aux) {
                            exits = true;
                            break;
                        }

                    }
                    if (!exits) {
                        likedPostsID.add(dataSnap.getValue(long.class));
                    }
                }
                for (long postID : likedPostsID) {
                    Query myTopPostsQuery = mDatabase.child("Posts").child(postID + "");
                    myTopPostsQuery.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            boolean exits = false;
                            Post aux = dataSnapshot.getValue(Post.class);
                            for (int i = 0; i < myLikedPosts.size(); i++) {

                                if (myLikedPosts.get(i).PostId == aux.PostId) {
                                    myLikedPosts.set(i, aux);
                                    exits = true;
                                }

                            }
                            if (!exits)
                                myLikedPosts.add(aux);
                            likedPostsAdapter.notifyDataSetChanged();


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        likedPostsAdapter = new PostAdapter(getContext(), myLikedPosts);
        rvLikedPublications.setAdapter(likedPostsAdapter);



        return root;
    }
}