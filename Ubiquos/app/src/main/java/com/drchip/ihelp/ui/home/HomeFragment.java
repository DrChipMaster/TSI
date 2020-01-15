package com.drchip.ihelp.ui.home;

import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.drchip.ihelp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<NetworkInfo> networkInfos;
    NetworkInfo networkInfo;
    private DatabaseReference mDatabase;

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);

        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        mDatabase = FirebaseDatabase.getInstance().getReference();


        networkInfo =

                String[]macs = info.getBssidList_toString();

        for (String mac : macs) {
            DatabaseReference aux = mDatabase.child("Mac").child(mac).push();
            aux.setValue(postid);

        }


        networkInfos = new ArrayList<>();
        DatabaseReference databaseMacPosts = mDatabase.child("Mac").child(mac).orderByChild("value");
        databaseMacPosts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshots) {
                for (DataSnapshot dataSnap : dataSnapshots.getChildren()) {
                    boolean exits = false;
                    for ()
                        for (int i = 0; i < networkInfos.size(); i++) {
                            if (networkInfos.get(i).date.equals(dataSnap.getValue(NetworkInfo.class).date))
                                exits = true;

                        }
                    if (!exits) {
                        networkInfos.add(dataSnap.getValue(NetworkInfo.class));
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return root;
    }


}