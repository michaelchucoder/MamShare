package com.babyspace.mamshare.app.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.babyspace.mamshare.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterProfileBabyFragment extends Fragment {


    public RegisterProfileBabyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register_profile_baby, container, false);
    }


}
