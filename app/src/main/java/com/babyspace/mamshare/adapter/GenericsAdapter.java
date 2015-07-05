package com.babyspace.mamshare.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.app.dialog.ToastHelper;
import com.babyspace.mamshare.basement.MamShare;
import com.babyspace.mamshare.bean.TestBean;
import com.michael.core.tools.ViewRelayoutUtil;
import com.michael.library.debug.L;

import java.util.List;

/**
 * Created with Android Studio
 * Package name: com.babyspace.mamshare.adapter
 * Author: MichaelChuCoder
 * Date: 2015-7-4
 * Time: 9:15
 * To change this template use File | Settings | File and Code Templates.
 */
public class GenericsAdapter extends BaseAdapter {
    int pageFlaglayout=R.layout.item_gridview_search;

    int pageFlag;
    Context mContext;
    List<?> data;

    public GenericsAdapter(Context context, List<?> data) {
        mContext = context;
        this.data = data;
    }

    public GenericsAdapter(Context context,int pageFlag) {
        mContext = context;
        this.pageFlag = pageFlag;

        L.d("CommonAdapter",mContext.getPackageName());
        L.d("CommonAdapter",mContext.getPackageCodePath());
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.item_gridview_search, null);
            ViewRelayoutUtil.relayoutViewWithScale(convertView, MamShare.screenWidthScale);
            holder.txtTitle = (TextView) convertView.findViewById(R.id.title);
            holder.btnLike = (Button) convertView.findViewById(R.id.like);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtTitle.setText(((List<TestBean>) data).get(position).getTitle());
        holder.btnLike.setText(((List<TestBean>) data).get(position).isLike() ? "喜欢" : "无视");

        holder.btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //状态改变后刷新数据
                ((List<TestBean>) data).get(position).setIsLike(!((List<TestBean>) data).get(position).isLike());
                notifyDataSetChanged();
            }
        });
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastHelper.showToast(mContext, "进入下一个页面");
            }
        });
        return convertView;

    }

    public void refresh(int pageFlag,List<?> data) {
        this.pageFlag=pageFlag;
        this.data = data;
        notifyDataSetChanged();
    }

    //TODO 可以写一个并集类, 方便使用

    static class ViewHolder {
        TextView txtTitle;
        Button btnLike;
    }

}
