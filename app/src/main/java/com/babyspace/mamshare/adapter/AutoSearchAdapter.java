package com.babyspace.mamshare.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.basement.MamShare;
import com.michael.core.tools.ViewRelayoutUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with Android Studio
 * Package name: com.babyspace.mamshare.adapter
 * Author: MichaelChuCoder
 * Date: 2015-7-6
 * Time: 11:56
 * To change this template use File | Settings | File and Code Templates.
 */
public class AutoSearchAdapter extends BaseAdapter {

    private Context context;
    private int resource = R.layout.item_auto_search;
    private List<String> data = new ArrayList<String>();

    public AutoSearchAdapter(Context context) {
        this.context = context;
    }

    public void resetData(List<String> newData) {
        this.data.clear();
        this.data.addAll(newData);
        this.notifyDataSetChanged();
    }

    public void resetNullData() {
        this.data.clear();
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return data.get(arg0);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        if (convertView == null) {
            convertView = View.inflate(context, resource, null);
            ViewRelayoutUtil.relayoutViewWithScale(convertView, MamShare.screenWidthScale);
        }
        String keyword = data.get(position);
        convertView.setTag(keyword);
        TextView txt_keyword = (TextView) convertView.findViewById(R.id.txt_keyword_ias);
        txt_keyword.setText(keyword);
        return convertView;
    }

}
