package com.babyspace.mamshare.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.app.fragment.HomeGuidanceListFragment;
import com.babyspace.mamshare.app.fragment.HomeEvaluateListFragment;
import com.babyspace.mamshare.basement.BaseActivity;

import butterknife.InjectView;
import butterknife.OnClick;

public class HomePrefaceActivity extends BaseActivity implements ViewPager.OnPageChangeListener {
    // 加上fragment
    @InjectView(R.id.tab_guidance)
    Button tab_guidance;
    @InjectView(R.id.tab_evaluate)
    Button tab_evaluate;

    private static int pagePosition = 0;
    private static int lastState = 0;

    //StrollFancyCoverFlowAdapter strollFancyCoverFlowAdapter;
    private ViewPager mPager;
    //private AbsPagerTab mTab;
    public static final String[] TITLES = {"攻略", "评测"};
    public static final Fragment[] FRAGMENTS = {new HomeGuidanceListFragment(), new HomeEvaluateListFragment()};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_preface);
        //strollFancyCoverFlowAdapter = new StrollFancyCoverFlowAdapter(this);
        initView();


    }

    private void initView() {
        mPager = (ViewPager) findViewById(R.id.pager);

        // 给ViewPager添加适配器
        mPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return FRAGMENTS[position];
            }

            @Override
            public int getCount() {
                return FRAGMENTS.length;
            }
        });
        // 设置监听获得回调
        mPager.setOnPageChangeListener(this);

        mPager.post(new Runnable() {
            @Override
            public void run() {
                onPageSelected(mPager.getCurrentItem());
            }
        });

    }

    // =============================================================================
    // Private classes
    // =============================================================================

/*
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

*/

    @OnClick({R.id.tab_guidance, R.id.tab_evaluate})
    public void doOnClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.tab_guidance:
                tab_guidance.setBackgroundResource(R.drawable.tab_shape_left_blue_selected);
                tab_evaluate.setBackgroundResource(R.drawable.tab_shape_right_blue_unselect);
                mPager.setCurrentItem(0);
                break;
            case R.id.tab_evaluate:
                tab_guidance.setBackgroundResource(R.drawable.tab_shape_left_blue_unselect);
                tab_evaluate.setBackgroundResource(R.drawable.tab_shape_right_blue_selected);
                mPager.setCurrentItem(1);
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        pagePosition = position;

        if (position == 0) {
            tab_guidance.setBackgroundResource(R.drawable.tab_shape_left_blue_selected);
            tab_evaluate.setBackgroundResource(R.drawable.tab_shape_right_blue_unselect);
        } else {
            tab_guidance.setBackgroundResource(R.drawable.tab_shape_left_blue_unselect);
            tab_evaluate.setBackgroundResource(R.drawable.tab_shape_right_blue_selected);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (lastState == 1 && state == 0) {
            Intent i = new Intent();

            switch (pagePosition) {
                case 0:
                    i.setClass(this, RecommendLabelActivity.class);
                    break;
                case 1:
                    i.setClass(this, HomeUserCenterActivity.class);
                    break;

            }
            startActivity(i);

        }
        lastState = state;
    }
}