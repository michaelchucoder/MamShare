package com.babyspace.mamshare.app.activity;

import android.os.Bundle;
import android.view.View;


import com.babyspace.mamshare.R;
import com.babyspace.mamshare.adapter.GridViewSearchAdapter;
import com.babyspace.mamshare.basement.BaseActivity;
import com.michael.library.widget.custom.GridViewWithHeaderAndFooter;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

public class RecommendLabelActivity extends BaseActivity {
    @InjectView(R.id.label_gridView)
    GridViewWithHeaderAndFooter gridView;

    GridViewSearchAdapter adapter;

    List<String> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend_label);
        data = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            data.add("ppp" + i);
        }


        adapter = new GridViewSearchAdapter(this);
        adapter.refresh(data);

        gridView.addHeaderView(View.inflate(this, R.layout.common_title_layout, null));
        gridView.addFooterView(View.inflate(this, R.layout.common_title_layout, null));

        gridView.setAdapter(adapter);

        data.add("ggg");
        adapter.refresh(data);


    }
}
