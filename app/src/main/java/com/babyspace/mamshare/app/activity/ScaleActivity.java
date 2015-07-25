package com.babyspace.mamshare.app.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.basement.BaseActivity;
import com.babyspace.mamshare.basement.MamShare;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

public class ScaleActivity extends BaseActivity implements AbsListView.OnScrollListener {
    @InjectView(R.id.common_title_text)
    TextView common_title_text;
    //控件
    private ListView listView;
    private RelativeLayout toolbar;
    private RelativeLayout floatTitle;
    private ImageView headerBg;
    //测量值
    private float headerHeight;//顶部高度
    private float minHeaderHeight;//顶部最低高度，即Bar的高度
    private float floatTitleLeftMargin;//header标题文字左偏移量
    private float floatTitleSize;//header标题文字大小
    private float floatTitleSizeLarge;//header标题文字大小（大号）

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scale);

        initMeasure();
        initView();
        initListViewHeader();
        initListView();
        initEvent();
    }

    private void initMeasure() {
        headerHeight = getResources().getDimension(R.dimen.header_height);
        minHeaderHeight = getResources().getDimension(R.dimen.min_header_height);
        floatTitleLeftMargin = getResources().getDimension(R.dimen.float_title_left_margin);
        floatTitleSize = getResources().getDimension(R.dimen.float_title_size);
        floatTitleSizeLarge = getResources().getDimension(R.dimen.float_title_size_large);
    }

    private void initView() {
        listView = (ListView) findViewById(R.id.lv_main);
        floatTitle = (RelativeLayout) findViewById(R.id.rl_main_title);
        toolbar = (RelativeLayout) findViewById(R.id.tb_main);
    }

    private void initListView() {
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            data.add(String.valueOf(i));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.activity_list_item, android.R.id.text1, data);
        listView.setAdapter(adapter);
    }

    private void initListViewHeader() {
        View headerContainer = LayoutInflater.from(this).inflate(R.layout.scale_header, listView, false);
        headerBg = (ImageView) headerContainer.findViewById(R.id.img_header_bg);

        listView.addHeaderView(headerContainer);
    }

    private void initEvent() {
        listView.setOnScrollListener(this);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        //Y轴偏移量
        float scrollY = getScrollY(view);

        //变化率
        float headerBarOffsetY = headerHeight - minHeaderHeight;//Toolbar与header高度的差值
        float offset = 1 - Math.max((headerBarOffsetY - scrollY) / headerBarOffsetY, 0f);

        //Toolbar背景色透明度
        toolbar.setBackgroundColor(Color.argb((int) (offset * 255), 255, 255, 255));
        //header背景图Y轴偏移
        headerBg.setTranslationY(scrollY / 2);

        /*** 标题文字处理 ***/
        //标题文字缩放圆心（X轴）
        floatTitle.setPivotX(floatTitle.getLeft() + floatTitle.getPaddingLeft());
        //标题文字缩放比例
        float titleScale = floatTitleSize / floatTitleSizeLarge;
        //标题文字X轴偏移
        /**
         * 加上了 MamShare.screenDensity
         */
        floatTitle.setTranslationX(MamShare.screenDensity*floatTitleLeftMargin * offset);
        //标题文字Y轴偏移：（-缩放高度差 + 大文字与小文字高度差）/ 2 * 变化率 + Y轴滑动偏移
        floatTitle.setTranslationY(MamShare.screenDensity*
                (-(floatTitle.getHeight() - minHeaderHeight) +//-缩放高度差
                        floatTitle.getHeight() * (1 - titleScale))//大文字与小文字高度差
                        / 2 * offset +
                        (headerHeight - floatTitle.getHeight()) * (1 - offset));//Y轴滑动偏移
        //标题文字X轴缩放
        floatTitle.setScaleX(1 - offset * (1 - titleScale));
        //标题文字Y轴缩放
        floatTitle.setScaleY(1 - offset * (1 - titleScale));

        //判断标题文字的显示
        if (scrollY > headerBarOffsetY) {
            common_title_text.setText("只是测试");
            floatTitle.setVisibility(View.GONE);
        } else {
            common_title_text.setText("");
            floatTitle.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 得到ListView在Y轴上的偏移
     */
    public float getScrollY(AbsListView view) {
        View c = view.getChildAt(0);

        if (c == null)
            return 0;

        int firstVisiblePosition = view.getFirstVisiblePosition();
        int top = c.getTop();

        float headerHeight = 0;
        if (firstVisiblePosition >= 1)
            headerHeight = this.headerHeight;

        return -top + firstVisiblePosition * c.getHeight() + headerHeight;
    }
}