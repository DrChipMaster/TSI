package com.drchip.ihelp.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.drchip.ihelp.R;

public class SlideshowFragment extends Fragment {
    private SlideshowViewModel slideshowViewModel;
    Button btnLikedPublications;
    Button btnUserPublications;
    RecyclerView rvUserPublications;
    RecyclerView rvLikedPublications;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel = ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);

        rvUserPublications = root.findViewById(R.id.rvUserPublications);
        rvLikedPublications = root.findViewById(R.id.rvLikedPublications);
        btnUserPublications = root.findViewById(R.id.btnPosts);
        btnLikedPublications = root.findViewById(R.id.btnLiked);

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

        return root;
    }
}