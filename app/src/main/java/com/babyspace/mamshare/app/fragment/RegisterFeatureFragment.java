package com.babyspace.mamshare.app.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.adapter.GenericsAdapter;
import com.babyspace.mamshare.adapter.MamaFeatureAdapter;
import com.babyspace.mamshare.basement.BaseFragment;
import com.babyspace.mamshare.bean.TestBean;
import com.babyspace.mamshare.listener.RegisterListener;
import com.michael.library.widget.custom.GridViewWithHeaderAndFooter;

import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFeatureFragment extends BaseFragment {
    RegisterListener mCallback;

    @InjectView(R.id.label_gridView)
    GridViewWithHeaderAndFooter gridView;

    MamaFeatureAdapter adapter;

    List<TestBean> data;




    public RegisterFeatureFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception.
        try {
            mCallback = (RegisterListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement RegisterProfileListener");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register_feature, container, false);
    }

    @Override
    public void init(Bundle savedInstanceState) {

        setContentView(R.layout.fragment_register_feature);

    }

    @Override
    public void initView() {

    }

    @OnClick({R.id.register_feature_custom})
    public void doOnClick(View view) {

        switch (view.getId()) {
            case R.id.register_feature_custom:
                mCallback.onRegisterCustomSelected();
                break;
        }
    }


}
