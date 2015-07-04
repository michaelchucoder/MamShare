package com.babyspace.mamshare.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.app.activity.SettingActivity;
import com.babyspace.mamshare.basement.MamShare;
import com.babyspace.mamshare.bean.TestBean;
import com.michael.core.tools.ViewRelayoutUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with Android Studio
 * Package name: com.babyspace.mamshare.adapter
 * Author: MichaelChuCoder
 * Date: 2015/6/29
 * Time: 11:55
 * To change this template use File | Settings | File and Code Templates.
 */
public class MamaProfileAdapter extends BaseAdapter {
    private List<TestBean> data = new ArrayList<TestBean>();
    private Context context;
    private int resource = R.layout.item_mama_profile;

    public MamaProfileAdapter(Context context) {
        this.context = context;
    }


    public void refresh(List<TestBean> result) {
        this.data = result;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        if (data.size() % 3 > 0) {
            return data.size() / 3 + 1;
        } else {
            return data.size() / 3;
        }
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
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, resource, null);
            ViewRelayoutUtil.relayoutViewWithScale(convertView, MamShare.screenWidthScale);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final TestBean productList = data.get(2 * (position));
        TestBean productList2 = null;




        if (data.size() <= 2 * (position) + 1) {
            holder.layout2.setVisibility(View.INVISIBLE);
        } else {
            holder.layout2.setVisibility(View.VISIBLE);
            productList2 = data.get(2 * (position) + 1);
            holder.tv_sale_marketprice2.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            holder.tv_sale_marketprice2.getPaint().setAntiAlias(true);
        }


        holder.iv_sale_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goGoodDetilsActivity(productList);
            }
        });

        final TestBean finalProductList = productList2;
        holder.iv_sale_img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goGoodDetilsActivity(finalProductList);
            }
        });

        return convertView;
    }

    public void goGoodDetilsActivity(TestBean productList) {
        Intent intent = new Intent(context, SettingActivity.class);

        context.startActivity(intent);
    }

    static class ViewHolder {
        RelativeLayout layout;
        ImageView iv_sale_img;
        TextView tv_sale_goods_name;
        TextView tv_sale_price;
        TextView tv_sale_marketprice;
        TextView tv_sale_zhekou;

        RelativeLayout layout2;
        ImageView iv_sale_img2;
        TextView tv_sale_goods_name2;
        TextView tv_sale_price2;
        TextView tv_sale_marketprice2;
        TextView tv_sale_zhekou2;
    }

}