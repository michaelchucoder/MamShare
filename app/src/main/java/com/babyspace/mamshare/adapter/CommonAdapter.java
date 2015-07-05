package com.babyspace.mamshare.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.app.dialog.ToastHelper;
import com.babyspace.mamshare.basement.BaseResponseBean;
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
public class CommonAdapter extends BaseAdapter {
    int pageFlag;
    Context mContext;
    List<TestBean> data;
    BaseResponseBean responseBean;

    public CommonAdapter(Context context, List<TestBean> data) {
        mContext = context;
        this.data = data;
    }
    public CommonAdapter(Context context, BaseResponseBean responseBean) {
        mContext = context;
        this.responseBean = responseBean;
    }

    public CommonAdapter(Context context,int pageFlag) {
        mContext = context;
        this.pageFlag = pageFlag;

        L.d("GenericsAdapter",mContext.getPackageName());
        L.d("GenericsAdapter",mContext.getPackageCodePath());
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
            convertView = View.inflate(mContext, R.layout.gridview_search_item, null);
            ViewRelayoutUtil.relayoutViewWithScale(convertView, MamShare.screenWidthScale);
            holder.txtTitle = (TextView) convertView.findViewById(R.id.title);
            holder.btnLike = (Button) convertView.findViewById(R.id.like);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtTitle.setText(data.get(position).getTitle());
        holder.btnLike.setText(data.get(position).isLike() ? "喜欢" : "无视");

        holder.btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //状态改变后刷新数据
                data.get(position).setIsLike(!data.get(position).isLike());
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

    public void refresh(int pageFlag,List<TestBean> data) {
        this.pageFlag=pageFlag;
        this.data = data;
        notifyDataSetChanged();
    }


    static class ViewHolder {
        TextView txtTitle;
        Button btnLike;
    }

}