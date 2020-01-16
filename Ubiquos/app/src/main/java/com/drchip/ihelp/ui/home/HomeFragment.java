package com.drchip.ihelp.ui.home;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.drchip.ihelp.NetworkInfo;
import com.drchip.ihelp.Post;
import com.drchip.ihelp.PostAdapter;
import com.drchip.ihelp.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class HomeFragment extends Fragment {

    private DatabaseReference mDatabase;
    RecyclerView recyclerView;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Long> postsID;
    ArrayList<Post> Feed;
    Location loc;
    int length;
    private HomeViewModel homeViewModel;
    private FusedLocationProviderClient clientLoc;

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

        final String[] macs = info.getBssidList_toString();
        clientLoc = LocationServices.getFusedLocationProviderClient(getContext());

        getLocation(clientLoc);

        length = macs.length;
        for (final String mac : macs) {

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
                    if (mac.equals(macs[macs.length - 1]) || macs.length == 0) {
                        if (postsID.isEmpty()) {
                            String PathGPS = (int) (loc.getLatitude() * 10000) + " " + (int) (loc.getLongitude() * 10000);

                            PathGPS = PathGPS.replace('-', 'A').replace('.', '_');
                            Query local = mDatabase.child("GPS").child(PathGPS).push();
                            local.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for (DataSnapshot dataSnap : dataSnapshot.getChildren()) {
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

    private void getLocation(FusedLocationProviderClient client) {
        if (ActivityCompat.checkSelfPermission(getContext(), ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        client.getLastLocation().addOnSuccessListener((Activity) getContext(), new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    CharSequence text = "Latitude: " + location.getLatitude() + "\nLongitude: " + location.getLongitude();
                    int duration = Toast.LENGTH_SHORT;
                    loc = location;
                    if (length == 0) {
                        if (true) {
                            String PathGPS = (int) (loc.getLatitude() * 10000) + " " + (int) (loc.getLongitude() * 10000);

                            PathGPS = PathGPS.replace('-', 'A').replace('.', '_');
                            Query local = mDatabase.child("GPS").child(PathGPS).push();
                            local.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for (DataSnapshot dataSnap : dataSnapshot.getChildren()) {
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
                    }

                }
            }
        });
    }



}