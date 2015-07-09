package com.michael.library.widget.custom;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created with Android Studio
 * Package name: com.michael.library.widget.custom
 * Author: MichaelChuCoder 朱小辉
 * Date: 2015-7-9
 * Time: 16:06
 * To change this template use File | Settings | File and Code Templates.
 */
public class MichaelScrollView extends ScrollView {
    private OnScrollListener onScrollListener;
    /**
     * 主要是用在用户手指离开MichaelScrollView，MichaelScrollView还在继续滑动，我们用来保存Y的距离，然后做比较
     */
    private int lastScrollY;

    public MichaelScrollView(Context context) {
        this(context, null);
    }

    public MichaelScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MichaelScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * 设置滚动接口
     * @param onScrollListener
     */
    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }


    /**
     * 用于用户手指离开MichaelScrollView的时候获取MichaelScrollView滚动的Y距离，然后回调给onScroll方法中
     */
    private Handler handler = new Handler() {

        public void handleMessage(android.os.Message msg) {
            int scrollY = MichaelScrollView.this.getScrollY();

            //此时的距离和记录下的距离不相等，在隔5毫秒给handler发送消息
            if(lastScrollY != scrollY){
                lastScrollY = scrollY;
                handler.sendMessageDelayed(handler.obtainMessage(), 200);
            }
            if(onScrollListener != null){
                onScrollListener.onScroll(scrollY);
            }

        }

    };

    /**
     * 重写onTouchEvent， 当用户的手在MichaelScrollView上面的时候，
     * 直接将MichaelScrollView滑动的Y方向距离回调给onScroll方法中，当用户抬起手的时候，
     * MichaelScrollView可能还在滑动，所以当用户抬起手我们隔5毫秒给handler发送消息，在handler处理
     * MichaelScrollView滑动的距离
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.d("onScroll", "getAction " + ev.getAction());
        Log.d("onScroll", "lastScrollY " + lastScrollY);
        Log.d("onScroll", "getScrollY " + this.getScrollY());
        int mScrollY=this.getScrollY();
        int mLastScrollY=lastScrollY;

        if(onScrollListener != null){
            onScrollListener.onScroll(lastScrollY = this.getScrollY());
        }

        switch(ev.getAction()){
            case MotionEvent.ACTION_UP:
                handler.sendMessageDelayed(handler.obtainMessage(), 5);

                /**
                 * 朱小辉
                 */
                if(mScrollY-mLastScrollY>22)   onScrollListener.onScrollUp();
                if(mLastScrollY-mScrollY>22)   onScrollListener.onScrollDown();

                break;

        }
        return super.onTouchEvent(ev);
    }

    /**
     *
     * 滚动的回调接口
     *
     * @author MichaelChuCoder
     *
     */
    public interface OnScrollListener{
        /**
         * 回调方法， 返回MichaelScrollView滑动的Y方向距离
         * @param scrollY
         *         
         */
        void onScroll(int scrollY);
        void onScrollUp();
        void onScrollDown();
    }



}