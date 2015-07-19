package com.babyspace.mamshare.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.EditText;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.adapter.TabPageAdapter;
import com.babyspace.mamshare.app.fragment.GridViewEvaluateFragment;
import com.babyspace.mamshare.app.fragment.GridViewGuidanceFragment;
import com.babyspace.mamshare.basement.BaseActivity;
import com.babyspace.mamshare.commons.AppConstants;
import com.babyspace.mamshare.listener.EmptyListener;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

public class SearchResultActivity extends BaseActivity implements EmptyListener {

    @InjectView(R.id.register_name_edit)
    EditText register_name_edit;
    public static final String[] TITLES = new String[]{"攻略", "评测"};
    public static final List<Fragment> FRAGMENTS = new ArrayList<>();

    private TabPageIndicator mIndicator;
    private ViewPager mViewPager;
    private TabPageAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        setTheme(R.style.MyTheme);

        FRAGMENTS.add(GridViewGuidanceFragment.newInstance(AppConstants.page_search_guidance));
        FRAGMENTS.add(GridViewEvaluateFragment.newInstance(AppConstants.page_search_evaluate));

        mIndicator = (TabPageIndicator) findViewById(R.id.id_indicator);
        mViewPager = (ViewPager) findViewById(R.id.id_pager);
        mAdapter = new TabPageAdapter(getSupportFragmentManager(), TITLES, FRAGMENTS);
        mViewPager.setAdapter(mAdapter);
        mIndicator.setViewPager(mViewPager, 0);

    }

    @OnClick({R.id.tv_label_search, R.id.clear_txt})
    public void doOnClick(View view) {
        Intent i = new Intent();

        switch (view.getId()) {
            case R.id.tv_label_search:
                i.setClass(this, SearchResultActivity.class);
                startActivity(i);
                break;
            case R.id.clear_txt:
                register_name_edit.setText("");
                break;
        }
    }


    @Override
    public void onDataEmpty() {

    }
}
