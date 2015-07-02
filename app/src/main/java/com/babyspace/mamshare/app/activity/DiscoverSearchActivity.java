package com.babyspace.mamshare.app.activity;

import android.os.Bundle;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.app.fragment.DiscoverSearchFragment;
import com.babyspace.mamshare.basement.BaseActivity;


public class DiscoverSearchActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover_search);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new DiscoverSearchFragment())
                    .commit();
        }
    }

}
