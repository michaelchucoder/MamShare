package com.babyspace.mamshare.app.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.babyspace.mamshare.R;


public class DiscoverGuidanceFragment extends Fragment {

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DiscoverGuidanceFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DiscoverGuidanceFragment newInstance(String param1, String param2) {
        DiscoverGuidanceFragment fragment = new DiscoverGuidanceFragment();
        return fragment;
    }

    public DiscoverGuidanceFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_discover_guidance, container, false);
    }



}
