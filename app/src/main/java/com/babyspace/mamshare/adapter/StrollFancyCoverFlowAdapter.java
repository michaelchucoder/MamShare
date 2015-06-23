
package com.babyspace.mamshare.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.babyspace.mamshare.R;
import com.michael.library.widget.fancycoverflow.FancyCoverFlow;
import com.michael.library.widget.fancycoverflow.FancyCoverFlowAdapter;


public class StrollFancyCoverFlowAdapter extends FancyCoverFlowAdapter {

    // =============================================================================
    // Private members
    // =============================================================================

    private int[] images = {R.drawable.plane_airport_nor, R.drawable.plane_flightbook_normal, R.drawable.icon_plane_tab_flightbook};


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
        ImageView imageView = null;

        if (reuseableView != null) {
            imageView = (ImageView) reuseableView;
        } else {
            imageView = new ImageView(viewGroup.getContext());
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            imageView.setLayoutParams(new FancyCoverFlow.LayoutParams(300, 400));

        }

        imageView.setImageResource(this.getItem(i));
        return imageView;
    }
}
