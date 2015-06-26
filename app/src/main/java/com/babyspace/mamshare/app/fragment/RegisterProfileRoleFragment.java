package com.babyspace.mamshare.app.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.babyspace.mamshare.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class RegisterProfileRoleFragment extends Fragment {

    public RegisterProfileRoleFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register_profile_name, container, false);
    }
}
