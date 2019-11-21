package com.drchip.ihelp.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.drchip.ihelp.R;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;
    Button btnUserInfo;
    Button btnUserPublications;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        btnUserInfo = root.findViewById(R.id.button3);
        btnUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnUserInfo.setBackgroundResource(R.drawable.user_profile_on_left);
                btnUserInfo.setBackgroundResource(R.drawable.user_profile_off_right);


                /*    CONTINUAR: MOSTRAR CONTAINER INFO    */
            }
        });


        btnUserPublications = root.findViewById(R.id.button2);
        btnUserPublications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnUserPublications.setBackgroundResource(R.drawable.user_profile_on_right);
                btnUserPublications.setBackgroundResource(R.drawable.user_profile_off_left);

                /*    CONTINUAR: MOSTRAR CONTAINER INFO    */
            }
        });


        return root;
    }
}