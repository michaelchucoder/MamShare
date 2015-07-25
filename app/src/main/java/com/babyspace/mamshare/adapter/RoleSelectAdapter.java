package com.babyspace.mamshare.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.app.dialog.ToastHelper;
import com.babyspace.mamshare.basement.MamShare;
import com.babyspace.mamshare.bean.MamaRole;
import com.michael.core.tools.ViewRelayoutUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

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
    private DisplayImageOptions options;

    public RoleSelectAdapter(Context ctx) {
        this.ctx = ctx;
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.img_default_loading) // 在ImageView加载过程中显示图片
                .showImageForEmptyUri(R.drawable.img_default_loading) // url地址为空的时候显示的图片
                .showImageOnFail(R.drawable.img_default_loading) // 图片加载失败后显示的图片
                .bitmapConfig(Bitmap.Config.RGB_565)
                .resetViewBeforeLoading(true).cacheInMemory(true)
                .displayer(new SimpleBitmapDisplayer()).build();
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
            convertView = View.inflate(ctx, resource, null);
            ViewRelayoutUtil.relayoutViewWithScale(convertView, MamShare.screenWidthScale);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ImageLoader.getInstance().displayImage(data.get(position).systemHeadIcon, holder.iv_avatar, options);

        holder.btn_role.setText(data.get(position).roleName);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastHelper.showToast(ctx, "position");
            }
        });

        return convertView;
    }

    class ViewHolder {
        @InjectView(R.id.iv_avatar)
        ImageView iv_avatar;
        @InjectView(R.id.btn_role)
        TextView btn_role;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

}
