package com.michael.library.widget.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 解决ScrollView嵌套GridView(ListView方法同理)显示不全的问题
 *
 * @author mrsimple
 */
public class NoStrollListView extends ListView {

    public boolean hasScrollBar = true;

    /**
     * @param context
     */
    public NoStrollListView(Context context) {
        this(context, null);
    }

    public NoStrollListView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public NoStrollListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = heightMeasureSpec;
        if (hasScrollBar) {
            expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                    MeasureSpec.AT_MOST);
            super.onMeasure(widthMeasureSpec, expandSpec);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

}
