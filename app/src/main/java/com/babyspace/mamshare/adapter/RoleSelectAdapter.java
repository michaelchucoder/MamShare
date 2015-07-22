package com.babyspace.mamshare.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.app.dialog.ToastHelper;
import com.babyspace.mamshare.basement.MamShare;
import com.babyspace.mamshare.bean.MamaRole;
import com.michael.core.tools.ViewRelayoutUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with Android Studio
 * Package name: com.babyspace.mamshare.adapter
 * Author: MichaelChuCoder
 * Date: 2015-7-21
 * Time: 18:52
 * To change this template use File | Settings | File and Code Templates.
 */
public class RoleSelectAdapter extends BaseAdapter {
    private List<MamaRole> data = new ArrayList<>();
    private Context ctx;
    private int resource = R.layout.item_role_select;

    public RoleSelectAdapter(Context ctx) {
        this.ctx = ctx;
    }

    public void refresh(List<MamaRole> result) {
        this.data = result;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(ctx, resource, null);
            ViewRelayoutUtil.relayoutViewWithScale(convertView, MamShare.screenWidthScale);

            holder.iv_avatar = (ImageView) convertView.findViewById(R.id.iv_avatar);
            holder.btn_role = (Button) convertView.findViewById(R.id.btn_role);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ImageLoader.getInstance().displayImage(data.get(position).roleUrl, holder.iv_avatar);

        holder.btn_role.setText(data.get(position).roleName);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastHelper.showToast(ctx,"position");
            }
        });

        return convertView;
    }


    static class ViewHolder {
        ImageView iv_avatar;
        Button btn_role;

    }

}
