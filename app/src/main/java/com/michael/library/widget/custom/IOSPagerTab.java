package com.michael.library.widget.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.Button;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.framework.utils.DensityUtil;
import com.babyspace.mamshare.framework.utils.UIUtils;


public class IOSPagerTab extends AbsPagerTab {
    private int width = DensityUtil.dp2px(this.getContext(), 100);
    private int heigh = DensityUtil.dp2px(this.getContext(), 36);

    public IOSPagerTab(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * TextView tab = new TextView(getContext());
     * tab.setText(title);
     * tab.setGravity(Gravity.CENTER);
     * tab.setSingleLine();
     * tab.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTabTextSize);
     * tab.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
     * tab.setTextColor(UIUtils.getColorStateList(getContext(), mTabTextColorResId));
     * tab.setBackgroundDrawable(UIUtils.getDrawable(getContext(), mTabBackgroundResId));
     * tab.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
     */
    @Override
    public void addPageTab(int position, String title, int len) {
        if (position == 0) {
            Button tab = new Button(getContext());
            tab.setText(title);
            tab.setGravity(Gravity.CENTER);
            tab.setSingleLine();
            tab.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTabTextSize);
            tab.setTextColor(UIUtils.getColorStateList(mTabTextColorResId));
            tab.setBackgroundResource(R.drawable.bg_tab_left);
            tab.setLayoutParams(new LayoutParams(width, heigh));
            addTab(position, tab);
        } else if (position == len - 1) {
            Button tab = new Button(getContext());
            tab.setText(title);
            tab.setGravity(Gravity.CENTER);
            tab.setSingleLine();
            tab.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTabTextSize);
            tab.setTextColor(UIUtils.getColorStateList(mTabTextColorResId));
            tab.setBackgroundResource(R.drawable.bg_tab_right);
            tab.setLayoutParams(new LayoutParams(width, heigh));
            addTab(position, tab);
        }
    }

}
