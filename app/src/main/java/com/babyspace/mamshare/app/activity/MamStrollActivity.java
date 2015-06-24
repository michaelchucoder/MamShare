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
import com.michael.library.debug.L;
import com.michael.library.widget.fancycoverflow.FancyCoverFlow;
import com.michael.library.widget.fancycoverflow.FancyCoverFlowAdapter;

import butterknife.InjectView;

public class MamStrollActivity extends BaseActivity {
    private int[] images = {R.drawable.plane_airport_nor, R.drawable.plane_flightbook_normal, R.drawable.icon_plane_tab_flightbook};

    @InjectView(R.id.ll_stroll_fancy_cover_flow)
    LinearLayout ll_stroll_fancy_cover_flow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mam_stroll);
        setSwipeBackEnable(false);

        FancyCoverFlow fancyCoverFlow = (FancyCoverFlow) findViewById(R.id.fancyCoverFlow);
        fancyCoverFlow.setReflectionEnabled(true);
        fancyCoverFlow.setReflectionRatio(0.4f);
        fancyCoverFlow.setReflectionGap(0);

        fancyCoverFlow.setAdapter(new StrollFancyCoverFlowAdapter(images));

        fancyCoverFlow.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ll_stroll_fancy_cover_flow.setBackgroundResource(images[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    // =============================================================================
    // Private classes
    // =============================================================================

    private static class StrollFancyCoverFlowAdapter extends FancyCoverFlowAdapter {

        private final int[] images;

        public StrollFancyCoverFlowAdapter(int[] images) {
            this.images = images;

        }

        // =============================================================================
        // Private members
        // =============================================================================


        // =============================================================================
        // SuperType overrides
        // =============================================================================

        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public Integer getItem(int i) {
            return images[i];
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

            customViewGroup.getImageView().setImageResource(this.getItem(i));
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

            this.imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            this.imageView.setAdjustViewBounds(true);

            this.button.setText("奔跑吧");
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
    }
}
