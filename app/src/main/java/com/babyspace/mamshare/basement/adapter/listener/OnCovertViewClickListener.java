package com.babyspace.mamshare.basement.adapter.listener;

import android.view.View;

/**
 * Created by michael on 2015/6/21.
 */
public abstract class OnCovertViewClickListener implements View.OnClickListener {
    private View mConvertView;
    private int positionId;

    public OnCovertViewClickListener(View convertView, int positionId) {
        mConvertView = convertView;
        this.positionId = positionId;
    }

    @Override
    public void onClick(View v) {
        int position = (int) mConvertView.getTag(positionId);
        onClickCallBack(v, position);
    }

    public abstract void onClickCallBack(View convertView, int position);
}
