package com.babyspace.mamshare.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.basement.MamShare;
import com.babyspace.mamshare.bean.TestBean;
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

    Context ctx;
    List<TestBean> data;

    public GridViewSearchAdapter(Context context, List<TestBean> data) {
        ctx = context;
        this.data = data;
    }

    public GridViewSearchAdapter(Context context) {
        ctx = context;
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

        @SuppressLint("ViewHolder")
        View rootView = LayoutInflater.from(ctx).inflate(R.layout.item_evaluate,
                parent, false);
        ViewRelayoutUtil.relayoutViewWithScale(rootView, MamShare.screenWidthScale);
        TextView txtTitle = (TextView) rootView.findViewById(R.id.tv_title);
        Button btnLike = (Button) rootView.findViewById(R.id.btn_like);
        txtTitle.setText(data.get(position).getTitle());
        btnLike.setText(data.get(position).isLike() ? "喜欢" : "无视");
        return rootView;
    }

    public void refresh(List<TestBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }
}