package com.drchip.ihelp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.drchip.ihelp.NetworkInfo;
import com.drchip.ihelp.Post;
import com.drchip.ihelp.PostAdapter;
import com.drchip.ihelp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private DatabaseReference mDatabase;
    RecyclerView recyclerView;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Long> postsID;
    ArrayList<Post> Feed;

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = root.findViewById(R.id.rvHome);
        recyclerView.setHasFixedSize(false);


        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);


        mDatabase = FirebaseDatabase.getInstance().getReference();

        Feed = new ArrayList<>();

        NetworkInfo info = new NetworkInfo(getContext());

        String[] macs = info.getBssidList_toString();

        for (String mac : macs) {

            Query myTopPostsQuery = mDatabase.child("Mac").child(mac).orderByChild("value");
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
                                for (int i = 0; i < Feed.size(); i++) {

                                    if (Feed.get(i).PostId == aux.PostId) {
                                        Feed.set(i, aux);
                                        exits = true;
                                    }

                                }
                                if (!exits)
                                    Feed.add(aux);
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


        }

        myAdapter = new PostAdapter(getContext(), Feed);
        recyclerView.setAdapter(myAdapter);










        return root;
    }


}