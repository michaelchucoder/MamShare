package com.babyspace.mamshare.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.app.activity.UserCollectActivity;
import com.babyspace.mamshare.basement.MamShare;
import com.babyspace.mamshare.bean.Guidance;
import com.babyspace.mamshare.commons.AppConstants;
import com.babyspace.mamshare.commons.IntentParams;
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
 * Date: 2015-7-30
 * Time: 10:33
 * To change this template use File | Settings | File and Code Templates.
 */
public class CollectAdapter extends BaseAdapter {
    private List<Guidance> data = new ArrayList<>();
    private Context ctx;
    private int resource = R.layout.item_user_collect;
    private int footer = R.layout.collect_more_footer;
    private DisplayImageOptions options;

    private int listViewHight;

    public CollectAdapter(Context ctx) {
        this.ctx = ctx;
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.img_default_loading) // 在ImageView加载过程中显示图片
                .showImageForEmptyUri(R.drawable.img_default_loading) // url地址为空的时候显示的图片
                .showImageOnFail(R.drawable.img_default_loading) // 图片加载失败后显示的图片
                .bitmapConfig(Bitmap.Config.RGB_565)
                .resetViewBeforeLoading(true).cacheInMemory(true).cacheOnDisk(true)
                .displayer(new SimpleBitmapDisplayer()).build();
    }

    public void refresh(List<Guidance> result) {
        this.data = result;

        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub

        if (null != data && data.size() > 0)
            return data.size() + 1;


        return 0;
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
        if (position == data.size()) {
//            listViewHight = parent.getHeight();

            convertView = getFooterView();
            convertView.setLayoutParams(new ListView.LayoutParams(listViewHight * 3 / 4, listViewHight));

        } else {

            listViewHight = parent.getHeight();

            ViewHolder holder;
            convertView = null;
            if (convertView == null) {

                convertView = View.inflate(ctx, resource, null);
                ViewRelayoutUtil.relayoutViewWithScale(convertView, MamShare.screenWidthScale);
                convertView.setLayoutParams(new ListView.LayoutParams(listViewHight * 3 / 4, listViewHight));

                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            ImageLoader.getInstance().displayImage(data.get(position).imageUrl, holder.iv_cover, options);


        }
        return convertView;

    }

    private View getFooterView() {
        View convertView = View.inflate(ctx, footer, null);
        ViewRelayoutUtil.relayoutViewWithScale(convertView, MamShare.screenWidthScale);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(ctx, UserCollectActivity.class);
                i.putExtra(IntentParams.prePage, AppConstants.page_collect_guidance);
                ctx.startActivity(i);
            }
        });
        return convertView;
    }

    class ViewHolder {
        @InjectView(R.id.iv_cover)
        ImageView iv_cover;
        boolean footer;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

}
