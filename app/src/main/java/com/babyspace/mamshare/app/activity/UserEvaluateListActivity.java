package com.babyspace.mamshare.app.activity;

import android.os.Bundle;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.app.fragment.GridViewEvaluateFragment;
import com.babyspace.mamshare.basement.BaseActivity;

public class UserEvaluateListActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_evaluate_list);
        if (getActionBar() != null) getActionBar().hide();

        if (findViewById(R.id.fragment_container) != null) {

            if (savedInstanceState != null) {
                return;
            }

            GridViewEvaluateFragment fragment = GridViewEvaluateFragment.newInstance(AppConstants.page_user_evaluate);

            fragment.setArguments(getIntent().getExtras());

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment).commit();
        }
    }

}
