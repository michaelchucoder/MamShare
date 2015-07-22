package com.babyspace.mamshare.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.bean.FlowSearchItem;
import com.michael.core.tools.ViewRelayoutUtil;
import com.michael.library.widget.custom.NoStrollGridView;
import com.michael.library.widget.custom.NoStrollListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with Android Studio
 * Package name: com.babyspace.mamshare.adapter
 * Author: MichaelChuCoder
 * Date: 2015/6/29
 * Time: 19:56
 * To change this template use File | Settings | File and Code Templates.
 */
public class SearchFlowAdapter extends BaseAdapter {

    List<FlowSearchItem> items = new ArrayList<FlowSearchItem>();
    LayoutInflater mInflater;
    Context ctx;

    public SearchFlowAdapter(Context context, List<FlowSearchItem> items) {
        ctx = context;
        this.items = items;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public FlowSearchItem getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        final FlowSearchItem item = getItem(position);

        if (item.mText.equals("111")) {
            ViewHolderGrid viewHolder = null;

            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.flow_search_item_g, parent, false);
                ViewRelayoutUtil.relayoutViewWithScale(convertView, MamShare.screenWidthScale);

                viewHolder = new ViewHolderGrid();
                viewHolder.noStrollGridView = (NoStrollGridView) convertView.findViewById(R.id.my_flow_view);
                viewHolder.mTextView = (TextView) convertView.findViewById(R.id.my_tv);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolderGrid) convertView.getTag();
            }

            // 设置GridView的Adapter
            viewHolder.noStrollGridView.setAdapter(new GridViewSearchAdapter(ctx, item.data));
            // 计算GridView宽度, 设置默认为numColumns为3.
            //GridViewUtils.updateGridViewLayoutParams(viewHolder.noStrollGridView, 3);
            viewHolder.mTextView.setText(item.mText);
            return convertView;
        } else {
            ViewHolderList viewHolder = null;

            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.flow_search_item_l, parent, false);
                ViewRelayoutUtil.relayoutViewWithScale(convertView, MamShare.screenWidthScale);

                viewHolder = new ViewHolderList();
                viewHolder.noStrollListView = (NoStrollListView) convertView.findViewById(R.id.my_flow_view);
                viewHolder.mTextView = (TextView) convertView.findViewById(R.id.my_tv);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolderList) convertView.getTag();
            }

            // 设置LIstView的Adapter
            viewHolder.noStrollListView.setAdapter(new ListViewSearchAdapter(ctx, item.data));
            // 计算GridView宽度, 设置默认为numColumns为3.
            //GridViewUtils.updateGridViewLayoutParams(viewHolder.noStrollGridView, 3);
            viewHolder.mTextView.setText(item.mText);
            return convertView;
        }


    }

    /**
     * @author michael
     */
    static class ViewHolderGrid {
        NoStrollGridView noStrollGridView;
        TextView mTextView;
    }

    static class ViewHolderList {
        NoStrollListView noStrollListView;
        TextView mTextView;
    }

    public void refresh(List<FlowSearchItem> items) {
        this.items = items;
        notifyDataSetChanged();
    }
}