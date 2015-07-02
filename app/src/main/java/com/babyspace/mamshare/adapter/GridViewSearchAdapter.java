package com.babyspace.mamshare.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.basement.MamShare;
import com.michael.core.tools.ViewRelayoutUtil;

import java.util.List;

/**
 * Created with Android Studio
 * Package name: com.babyspace.mamshare.adapter
 * Author: MichaelChuCoder
 * Date: 2015/6/29
 * Time: 19:52
 * To change this template use File | Settings | File and Code Templates.
 */
public class GridViewSearchAdapter extends BaseAdapter {

    Context mContext;
    List<String> datas;

    public GridViewSearchAdapter(Context context, List<String> datas) {
        mContext = context;
        this.datas = datas;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rootView = LayoutInflater.from(mContext).inflate(R.layout.gridview_search_item,
                parent, false);
        //ViewRelayoutUtil.relayoutViewWithScale(rootView, MamShare.screenWidthScale);
        Button textView = (Button) rootView.findViewById(R.id.my_txt);
        textView.setText(datas.get(position));
        return rootView;
    }
    public void refresh(List<String> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }
}