package com.babyspace.mamshare.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.babyspace.mamshare.R;

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
    List<String> datas;

    public ListViewSearchAdapter(Context context, List<String> datas) {
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

        View rootView = LayoutInflater.from(mContext).inflate(R.layout.listview_search_item,
                parent, false);
        TextView textView = (TextView) rootView.findViewById(R.id.my_txt);
        textView.setText(datas.get(position));
        return rootView;
    }


}