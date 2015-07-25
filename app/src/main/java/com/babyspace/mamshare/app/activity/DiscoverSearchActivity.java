package com.babyspace.mamshare.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.app.fragment.DiscoverSearchFragment;
import com.babyspace.mamshare.basement.BaseActivity;
import com.babyspace.mamshare.commons.AppConstants;

import butterknife.OnClick;

public class DiscoverSearchActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover_search);
        if (getActionBar() != null) getActionBar().hide();

        if (findViewById(R.id.fragment_container) != null) {

            if (savedInstanceState != null) {
                return;
            }
            DiscoverSearchFragment fragment = DiscoverSearchFragment.newInstance(AppConstants.page_discover_search);

            fragment.setArguments(getIntent().getExtras());

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment).commit();
        }
    }


    @OnClick({R.id.ll_label_search, R.id.tv_label_search, R.id.back})
    public void doOnClick(View view) {
        switch (view.getId()) {
            case R.id.ll_label_search:
            case R.id.tv_label_search:
                Intent i = new Intent(this, RecommendTagActivity.class);
                startActivity(i);
                break;
            case R.id.back:
                onBackPressed();
                break;
        }
    }


}
