package com.babyspace.mamshare.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.bean.TestBean;

import java.util.List;

/**
 * Created with Android Studio
 * Package name: com.babyspace.mamshare.adapter
 * Author: MichaelChuCoder
 * Date: 2015/6/29
 * Time: 19:56
 * To change this template use File | Settings | File and Code Templates.
 */
public class ListViewSearchAdapter extends BaseAdapter {

    Context mContext;
    List<TestBean> data;

    public ListViewSearchAdapter(Context context, List<TestBean> data) {
        mContext = context;
        this.data = data;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rootView = LayoutInflater.from(mContext).inflate(R.layout.listview_search_item,
                parent, false);
        //ViewRelayoutUtil.relayoutViewWithScale(rootView, MamShare.screenWidthScale);

        TextView textView = (TextView) rootView.findViewById(R.id.my_txt);
        textView.setText(data.get(position).getTitle());
        return rootView;
    }

    public void refresh(List<TestBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }
}