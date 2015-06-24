package com.michael.library.widget.progressview;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.babyspace.mamshare.R;


public class MamaProgressBarLogoView extends LinearLayout {

    private TextView mContentLabel;
    private LinearLayout mTitleStyleRoot;
    private LinearLayout mCommonStyleRoot;

    @TargetApi(11)
    public MamaProgressBarLogoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public MamaProgressBarLogoView(Context context) {
        super(context);
        init();
    }

    public MamaProgressBarLogoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        View rootView = View.inflate(getContext(), R.layout.view_toast_loading_dialog, null);
        //ViewRelayoutUtil.relayoutViewWithScale(rootView, OpenApplication.screenWidthScale);

        mContentLabel = (TextView) rootView.findViewById(R.id.mContentLabel);
        mCommonStyleRoot = (LinearLayout) rootView.findViewById(R.id.mCommonStyleRoot);
        mTitleStyleRoot = (LinearLayout) rootView.findViewById(R.id.mTitleStyleRoot);
        rootView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        addView(rootView);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }

    public void setContent(String text) {
        mContentLabel.setText(text);
    }

    public void show() {
        if (getVisibility() != View.VISIBLE) {
            setVisibility(View.VISIBLE);
            mCommonStyleRoot.setVisibility(View.VISIBLE);
            mTitleStyleRoot.setVisibility(View.GONE);
        }
    }

    public void hide() {
        if (getVisibility() == View.VISIBLE) {
            setVisibility(View.GONE);
        }
    }


    public void showTitle() {
        if (getVisibility() != View.VISIBLE) {
            setVisibility(View.VISIBLE);
            mTitleStyleRoot.setVisibility(View.VISIBLE);
            mCommonStyleRoot.setVisibility(View.GONE);
        }
    }

}
