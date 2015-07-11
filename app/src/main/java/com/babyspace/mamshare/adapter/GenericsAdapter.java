package com.babyspace.mamshare.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.app.activity.EvaluateDetailActivity;
import com.babyspace.mamshare.app.activity.GuidanceDetailActivity;
import com.babyspace.mamshare.app.activity.ParallaxToolbarListViewActivity;
import com.babyspace.mamshare.basement.MamShare;
import com.babyspace.mamshare.bean.HomeGuidance;
import com.babyspace.mamshare.bean.TestBean;
import com.babyspace.mamshare.commons.AppConstants;
import com.michael.core.tools.ViewRelayoutUtil;
import com.michael.library.debug.L;
import com.nostra13.universalimageloader.core.ImageLoader;

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

    /**
     * public static final int page_home_guidance = 2001;
     * public static final int page_home_evaluate = 2002;
     * public static final int page_collect_guidance = 2003;
     * public static final int page_collect_evaluate = 2004;
     * public static final int page_discover_search = 2005;
     * public static final int page_recommend_label = 2006;
     * public static final int page_search_guidance = 2007;
     * public static final int page_search_evaluate = 2008;
     * public static final int page_label_evaluate = 2009;
     * public static final int page_user_evaluate = 2010;
     */


    List<TestBean> testBeans;

    int pageFlag;
    Context mContext;
    List<?> data;

    public GenericsAdapter(Context context, List<?> data) {
        mContext = context;
        this.data = data;
    }

    public GenericsAdapter(Context context, int pageFlag) {
        L.d("GenericsAdapter", "construct-pageFlag " + pageFlag);

        mContext = context;
        this.pageFlag = pageFlag;

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
        L.d("GenericsAdapter", "getView-pageFlag " + pageFlag);
        switch (pageFlag) {
            case AppConstants.page_home_guidance:
                HomeGuidanceHolder homeGuidanceHolder;
                List<HomeGuidance> homeGuidanceList = (List<HomeGuidance>) data;

                if (convertView == null) {
                    homeGuidanceHolder = new HomeGuidanceHolder();
                    convertView = View.inflate(mContext, AppConstants.item_home_guidance, null);
                    ViewRelayoutUtil.relayoutViewWithScale(convertView, MamShare.screenWidthScale);
                    homeGuidanceHolder.iv_guidance = (ImageView) convertView.findViewById(R.id.iv_guidance);
                    convertView.setTag(homeGuidanceHolder);
                } else {
                    homeGuidanceHolder = (HomeGuidanceHolder) convertView.getTag();
                }
                ImageLoader.getInstance().displayImage(homeGuidanceList.get(position).getImageUrl(), homeGuidanceHolder.iv_guidance);
                break;

            case AppConstants.page_home_evaluate:
                ViewHolder homeEvaluateHolder;

                if (convertView == null) {
                    homeEvaluateHolder = new ViewHolder();
                    convertView = View.inflate(mContext, AppConstants.item_home_evaluate, null);
                    ViewRelayoutUtil.relayoutViewWithScale(convertView, MamShare.screenWidthScale);
                    homeEvaluateHolder.txtTitle = (TextView) convertView.findViewById(R.id.title);
                    homeEvaluateHolder.btnLike = (Button) convertView.findViewById(R.id.like);
                    convertView.setTag(homeEvaluateHolder);
                } else {
                    homeEvaluateHolder = (ViewHolder) convertView.getTag();
                }

                homeEvaluateHolder.txtTitle.setText(((List<TestBean>) data).get(position).getTitle());
                homeEvaluateHolder.btnLike.setText(((List<TestBean>) data).get(position).isLike() ? "喜欢" : "无视");

                homeEvaluateHolder.btnLike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //状态改变后刷新数据
                        ((List<TestBean>) data).get(position).setIsLike(!((List<TestBean>) data).get(position).isLike());
                        notifyDataSetChanged();
                    }
                });
                break;

            default:
                /**
                 * 这里可以为空
                 */
                ViewHolder defaultHolder;

                if (convertView == null) {
                    defaultHolder = new ViewHolder();
                    convertView = View.inflate(mContext, AppConstants.item_recommend_label, null);
                    ViewRelayoutUtil.relayoutViewWithScale(convertView, MamShare.screenWidthScale);
                    defaultHolder.txtTitle = (TextView) convertView.findViewById(R.id.title);
                    defaultHolder.btnLike = (Button) convertView.findViewById(R.id.like);
                    convertView.setTag(defaultHolder);
                } else {
                    defaultHolder = (ViewHolder) convertView.getTag();
                }

                defaultHolder.txtTitle.setText(((List<TestBean>) data).get(position).getTitle());
                defaultHolder.btnLike.setText(((List<TestBean>) data).get(position).isLike() ? "喜欢" : "无视");

                defaultHolder.btnLike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //状态改变后刷新数据
                        ((List<TestBean>) data).get(position).setIsLike(!((List<TestBean>) data).get(position).isLike());
                        notifyDataSetChanged();
                    }
                });
                break;

        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                switch (pageFlag) {
                    case AppConstants.page_home_evaluate:
                        i.setClass(mContext, EvaluateDetailActivity.class);
                        break;
                    case AppConstants.page_home_guidance:
                        i.setClass(mContext, GuidanceDetailActivity.class);
                        break;
                    default:
                        i.setClass(mContext, ParallaxToolbarListViewActivity.class);
                        break;
                }
                mContext.startActivity(i);
            }
        });
        return convertView;

    }

    public void refresh(int pageFlag, List<?> data) {
        this.pageFlag = pageFlag;
        this.data = data;
        notifyDataSetChanged();
    }

    //TODO 可以写一个并集类, 方便使用

    static class ViewHolder {
        TextView txtTitle;
        Button btnLike;
    }

    static class HomeGuidanceHolder {
        ImageView iv_guidance;

    }

}
