package com.drchip.ihelp;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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

//import javax.swing.border.TitledBorder;

public class PostAdapter  extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private ArrayList<Post> posts;
    ItemClicked activity;

    ArrayList<Long> likedPostsID;
    ArrayList<Post> myLikedPosts;

    public PostAdapter(Context context, ArrayList<Post> list) {
        files = new ArrayList<>();
        posts = list;
        activity = (ItemClicked) context;
    }


    @NonNull
    @Override
    public PostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_post,parent,false);

        return new ViewHolder(v);         //muito importante!!!!para linkar com o ivPref por exemplo
    }

    DatabaseReference mDatabase1;
    private StorageReference mStorageRef;

    ArrayList<File> files;

    public interface ItemClicked  // Interface serve para passar eventos e assim entre classes, ou seja criar uma interface entre classes!
    {
        void onItemClicked(Post postClicked);
    }

    @Override
    public void onBindViewHolder(@NonNull final PostAdapter.ViewHolder holder, final int position) {
        holder.itemView.setTag(posts.get(position));   // quando algem segura o cenas guarda o index!!!!



        mDatabase1 = FirebaseDatabase.getInstance().getReference();

        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = mDatabase.child("users").child(posts.get(position).Author);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user;
                user = dataSnapshot.getValue(User.class);
                holder.tvName.setText(user.username);
                mStorageRef = FirebaseStorage.getInstance().getReference();
                StorageReference profile = mStorageRef.child("images/" + posts.get(position).Author + "/profile.jpg");

                try {
                    final File finalLocalFile = File.createTempFile(posts.get(position).Author, ".jpg");
                    boolean exists = false;
                    for (File file : files) {
                        if (file.equals(finalLocalFile)) {
                            exists = true;
                        }

                    }
                    if (!exists)

                        profile.getFile(finalLocalFile)
                            .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                    final Uri imageUri = Uri.parse(finalLocalFile.toString());

                                    try {
                                        Picasso.get().load(String.valueOf(finalLocalFile.toURL())).into(holder.ivProfilePicture);

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


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        if (!posts.get(position).ImagePath.equals("null")) {
            mStorageRef = FirebaseStorage.getInstance().getReference();
            StorageReference profile = mStorageRef.child("post/" + posts.get(position).ImagePath + "" + "/image.jpg");

            try {
                final File finalFileImage = File.createTempFile("image", ".jpg");
                profile.getFile(finalFileImage)
                        .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                final Uri imageUri = Uri.parse(finalFileImage.toString());

                                try {
                                    Picasso.get().load(String.valueOf(finalFileImage.toURL())).into(holder.ivPostImage);

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
            holder.ivUpperLine.setVisibility(View.GONE);
            holder.ivProfilePicture.setVisibility(View.VISIBLE);
        }


        likedPostsID = new ArrayList<>();
        likedPostsID.clear();
        holder.ivLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* store liked posts */
                Query myLikedPostsQuery = mDatabase.child("Users_likes").child(ApplicationClass.currentUser.getUid()).orderByChild("value");

                //myLikedPosts = new ArrayList<>();

                myLikedPostsQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshots) {
                        for (DataSnapshot dataSnap : dataSnapshots.getChildren()) {
                            likedPostsID.add(dataSnap.getValue(long.class));


                        }
                        boolean exists = false;
                        for (Post post : posts) {

                            if (likedPostsID.contains(post.PostId)) {
                                exists = true;
                            }
                        }
                        if (!exists) {
                            DatabaseReference trans = mDatabase1.child("Posts").child(posts.get(position).PostId + "");
                            /* if post not liked yet then like and store value */
                            DatabaseReference likes = mDatabase1.child("Users_likes").child(ApplicationClass.currentUser.getUid());
                            posts.get(position).Likes++;
                            trans.setValue(posts.get(position));
                            likes.push().setValue(posts.get(position).PostId);
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        });
        holder.tvTitle.setText(posts.get(position).Title);
        holder.tvDate.setText(posts.get(position).Date);
        holder.tvDescription.setText(posts.get(position).Description);
        holder.tvLikes.setText(posts.get(position).Likes + "");
        holder.tvComments.setText(0 + "");



        //holder.tvTitle.setText(posts.Title);
       // posts.get(position).Title = toString(holder.tvTitle.getText());
//        holder.tvName.setText(posts.get(position).getName());
//        holder.tvSurname.setText(posts.get(position).getSurname());
//
//        if(posts.get(position).getPreference().equals("bus"))
//        {
//            holder.ivPref.setImageResource(R.drawable.bus);
//        }
//        else
//        {
//            holder.ivPref.setImageResource(R.drawable.plane);
//
//        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivProfilePicture, ivPostImage, ivShare, ivLike, ivComment, ivUpperLine;
        TextView tvTitle, tvName, tvDate, tvDescription, tvLikes, tvComments;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvName = itemView.findViewById(R.id.tvName);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvLikes = itemView.findViewById(R.id.tvLikes);
            tvComments = itemView.findViewById(R.id.tvComments);
            ivProfilePicture = itemView.findViewById(R.id.ivProfilePicture);
            ivPostImage = itemView.findViewById(R.id.ivPostImage);
            ivShare = itemView.findViewById(R.id.ivShare);
            ivLike = itemView.findViewById(R.id.ivLike);
            ivComment = itemView.findViewById(R.id.ivComment);
            ivUpperLine = itemView.findViewById(R.id.ivUpperLine);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    activity.onItemClicked((Post) view.getTag());  // como obter o index do item clicado!!

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }
}
