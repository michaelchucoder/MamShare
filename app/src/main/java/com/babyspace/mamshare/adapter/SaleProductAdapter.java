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
import com.babyspace.mamshare.bean.SaleProductEntity;
import com.michael.core.tools.ViewRelayoutUtil;
import com.michael.library.debug.L;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;

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
public class SaleProductAdapter extends BaseAdapter {
    private List<SaleProductEntity> data = new ArrayList<SaleProductEntity>();
    private Context context;
    private int resource = R.layout.item_sale_product;
    private DisplayImageOptions options;
    private int saleId;

    public SaleProductAdapter(Context context) {
        this.context = context;
        options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.img_default_loading)
                // 在ImageView加载过程中显示图片
                .showImageForEmptyUri(R.drawable.img_default_loading)
                        // url地址为空的时候显示的图片
                .showImageOnFail(R.drawable.img_default_loading)
                        // 图片加载失败后显示的图片
                .bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.EXACTLY).resetViewBeforeLoading(true).cacheOnDisk(true).cacheInMemory(true)
                .displayer(new SimpleBitmapDisplayer()).build();
    }

    public void resetData(List<SaleProductEntity> result) {
        data.clear();
        data.addAll(result);
        this.notifyDataSetChanged();
    }

    public void refresh(List<SaleProductEntity> result, int saleId) {
        L.d("Michael", "dataList:" + result.size());
        this.data = result;
        this.saleId = saleId;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        if (data.size() % 2 > 0) {
            return data.size() / 2 + 1;
        } else {
            return data.size() / 2;
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

            holder.iv_sale_img = (ImageView) convertView.findViewById(R.id.iv_sale_list_img);
            holder.tv_sale_goods_name = (TextView) convertView.findViewById(R.id.tv_sale_goods_name);
            holder.tv_sale_price = (TextView) convertView.findViewById(R.id.tv_sale_price);
            holder.tv_sale_marketprice = (TextView) convertView.findViewById(R.id.tv_sale_marketprice);
            holder.tv_sale_zhekou = (TextView) convertView.findViewById(R.id.tv_sale_zhekou);

            holder.layout2 = (RelativeLayout) convertView.findViewById(R.id.layout2);
            holder.iv_sale_img2 = (ImageView) convertView.findViewById(R.id.iv_sale_list_img2);
            holder.tv_sale_goods_name2 = (TextView) convertView.findViewById(R.id.tv_sale_goods_name2);
            holder.tv_sale_price2 = (TextView) convertView.findViewById(R.id.tv_sale_price2);
            holder.tv_sale_marketprice2 = (TextView) convertView.findViewById(R.id.tv_sale_marketprice2);
            holder.tv_sale_zhekou2 = (TextView) convertView.findViewById(R.id.tv_sale_zhekou2);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final SaleProductEntity productList = data.get(2 * (position));
        SaleProductEntity productList2 = null;

        ImageLoader.getInstance().displayImage(productList.DefaultPhotoUrl, holder.iv_sale_img, options);
        holder.tv_sale_goods_name.setText(productList.GoodsName);
        holder.tv_sale_price.setText(productList.Price + "");
        holder.tv_sale_marketprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        holder.tv_sale_marketprice.getPaint().setAntiAlias(true);
        holder.tv_sale_marketprice.setText("¥" + productList.MarketPrice + "");
        holder.tv_sale_zhekou.setText(productList.ZheKou);


        if (data.size() <= 2 * (position) + 1) {
            holder.layout2.setVisibility(View.INVISIBLE);
        } else {
            holder.layout2.setVisibility(View.VISIBLE);
            productList2 = data.get(2 * (position) + 1);
            ImageLoader.getInstance().displayImage(productList2.DefaultPhotoUrl, holder.iv_sale_img2, options);
            holder.tv_sale_goods_name2.setText(productList2.GoodsName);
            holder.tv_sale_price2.setText(productList2.Price + "");
            holder.tv_sale_marketprice2.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            holder.tv_sale_marketprice2.getPaint().setAntiAlias(true);
            holder.tv_sale_marketprice2.setText("¥" + productList2.MarketPrice + "");
            holder.tv_sale_zhekou2.setText(productList2.ZheKou);
        }


        holder.iv_sale_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goGoodDetilsActivity(productList);
            }
        });

        final SaleProductEntity finalProductList = productList2;
        holder.iv_sale_img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goGoodDetilsActivity(finalProductList);
            }
        });

        return convertView;
    }

    public void goGoodDetilsActivity(SaleProductEntity productList) {
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