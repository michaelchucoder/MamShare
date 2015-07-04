package com.babyspace.mamshare.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.babyspace.mamshare.R;

import java.util.List;

/**
 * Created with Android Studio
 * Package name: com.babyspace.mamshare.adapter
 * Author: MichaelChuCoder
 * Date: 2015-7-4
 * Time: 9:15
 * To change this template use File | Settings | File and Code Templates.
 */
public class GenericsAdapter extends BaseAdapter{

    Context mContext;
    List<String> data;

    public GenericsAdapter(Context context, List<String> data) {
        mContext = context;
        this.data = data;
    }

    public GenericsAdapter(Context context) {
        mContext = context;
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

        View rootView = LayoutInflater.from(mContext).inflate(R.layout.gridview_search_item,
                parent, false);
        //ViewRelayoutUtil.relayoutViewWithScale(rootView, MamShare.screenWidthScale);
        Button textView = (Button) rootView.findViewById(R.id.my_txt);
        textView.setText(data.get(position));
        return rootView;
    }

    public void refresh(List<String> data) {
        this.data = data;
        notifyDataSetChanged();
    }

}
