package com.babyspace.mamshare.app.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.basement.BaseActivity;
import com.babyspace.mamshare.basement.BaseResponseBean;
import com.babyspace.mamshare.bean.Advert;
import com.babyspace.mamshare.bean.AdvertEvent;
import com.babyspace.mamshare.commons.AppConstants;
import com.babyspace.mamshare.commons.UrlConstants;
import com.babyspace.mamshare.framework.eventbus.HttpErrorEvent;
import com.michael.core.okhttp.OkHttpExecutor;
import com.michael.library.debug.L;
import com.michael.library.widget.fancycoverflow.FancyCoverFlow;
import com.michael.library.widget.fancycoverflow.FancyCoverFlowAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import de.greenrobot.event.EventBus;

public class HomeSpecialTopicActivity extends BaseActivity {

    StrollFancyCoverFlowAdapter strollFancyCoverFlowAdapter;
    @InjectView(R.id.specialTopic_bg)
    ImageView specialTopic_bg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_special_topic);
        strollFancyCoverFlowAdapter = new StrollFancyCoverFlowAdapter(this);

        OkHttpExecutor.query(UrlConstants.HomeAdvertisingFigure, AdvertEvent.class, false, this);

    }

    // =============================================================================
    // Private classes
    // =============================================================================

    private class StrollFancyCoverFlowAdapter extends FancyCoverFlowAdapter {
        Context ctx;
        List<Advert> datas = new ArrayList<Advert>();


        public StrollFancyCoverFlowAdapter(Context ctx) {
            this.ctx = ctx;

        }

        public void setData(List<Advert> datas) {
            this.datas = datas;
        }
        // =============================================================================
        // SuperType overrides
        // =============================================================================

        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public Object getItem(int i) {
            return datas.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getCoverFlowItem(int i, View reuseableView, ViewGroup viewGroup) {
            CustomViewGroup customViewGroup = null;

            if (reuseableView != null) {
                customViewGroup = (CustomViewGroup) reuseableView;
            } else {
                customViewGroup = new CustomViewGroup(viewGroup.getContext());
                customViewGroup.setLayoutParams(new FancyCoverFlow.LayoutParams(300, 600));
            }
            customViewGroup.getButton().setText("i-" + i);
            ImageLoader.getInstance().displayImage(datas.get(i).ImgesUrl, customViewGroup.getImageView());
            return customViewGroup;
        }
    }

    private static class CustomViewGroup extends LinearLayout {

        // =============================================================================
        // Child views
        // =============================================================================

        private ImageView imageView;

        private Button button;

        // =============================================================================
        // Constructor
        // =============================================================================

        private CustomViewGroup(Context context) {
            super(context);

            this.setOrientation(VERTICAL);
            this.setWeightSum(5);

            this.imageView = new ImageView(context);
            this.button = new Button(context);

            LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

            this.imageView.setLayoutParams(layoutParams);
            this.button.setLayoutParams(layoutParams);

            this.imageView.setScaleType(ImageView.ScaleType.MATRIX);
            this.imageView.setAdjustViewBounds(true);

            this.button.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("mamShare://stroll_special_topic"));
                    view.getContext().startActivity(i);
                }
            });

            this.addView(this.imageView);
            this.addView(this.button);
        }

        // =============================================================================
        // Getters
        // =============================================================================

        private ImageView getImageView() {
            return imageView;
        }

        private Button getButton() {
            return button;
        }
    }

    public void onEventMainThread(final AdvertEvent event) {
        L.d("onEventMainThread", "AdvertEvent:" + event.getData().get(0).toString());
        L.d("onEventMainThread", "event.getCode():" + event.getCode());

        if (event.getCode().equals(AppConstants.RESPONSE_OK) && event.getData() != null) {

            FancyCoverFlow fancyCoverFlow = (FancyCoverFlow) findViewById(R.id.fancyCoverFlow);
            fancyCoverFlow.setReflectionEnabled(true);
            fancyCoverFlow.setReflectionRatio(0.4f);
            fancyCoverFlow.setReflectionGap(0);

            strollFancyCoverFlowAdapter.setData(event.getData());
            fancyCoverFlow.setAdapter(strollFancyCoverFlowAdapter);
            strollFancyCoverFlowAdapter.notifyDataSetChanged();

            fancyCoverFlow.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    ImageLoader.getInstance().displayImage(event.getData().get(position).ImgesUrl, specialTopic_bg);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }
        requestEnd(event);
    }

    public void onEventMainThread(HttpErrorEvent event) {
        requestEnd(event);
    }


}
