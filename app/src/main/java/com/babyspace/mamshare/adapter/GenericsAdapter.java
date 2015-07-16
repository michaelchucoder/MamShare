package com.babyspace.mamshare.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
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
import com.babyspace.mamshare.app.dialog.ToastHelper;
import com.babyspace.mamshare.basement.MamShare;
import com.babyspace.mamshare.bean.Evaluate;
import com.babyspace.mamshare.bean.Guidance;
import com.babyspace.mamshare.bean.HomeEvaluate;
import com.babyspace.mamshare.bean.HomeGuidance;
import com.babyspace.mamshare.bean.Tags;
import com.babyspace.mamshare.bean.TestBean;
import com.babyspace.mamshare.bean.VersionCheckEvent;
import com.babyspace.mamshare.commons.AppConstants;
import com.babyspace.mamshare.commons.UrlConstants;
import com.michael.core.okhttp.OkHttpExecutor;
import com.michael.core.tools.ViewRelayoutUtil;
import com.michael.library.debug.L;
import com.michael.library.widget.roundimage.RoundImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import de.greenrobot.event.EventBus;

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
     * public static final int page_recommend_tag = 2006;
     * public static final int page_search_guidance = 2007;
     * public static final int page_search_evaluate = 2008;
     * public static final int page_label_evaluate = 2009;
     * public static final int page_user_evaluate = 2010;
     */

    int pageFlag;
    Context ctx;
    List<?> data;

    public GenericsAdapter(Context ctx, List<?> data) {
        this.ctx = ctx;
        this.data = data;
    }

    public GenericsAdapter(Context ctx, int pageFlag) {
        L.d("GenericsAdapter", "construct-pageFlag " + pageFlag);

        this.ctx = ctx;
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

        if (pageFlag == AppConstants.page_empty) { //TODO 这是如果为空

            convertView = LayoutInflater.from(ctx).inflate(AppConstants.item_empty,
                    parent, false);
            ViewRelayoutUtil.relayoutViewWithScale(convertView, MamShare.screenWidthScale);
            TextView txtTitle = (TextView) convertView.findViewById(R.id.iv_empty);
            txtTitle.setText("哈哈哈，走错路啦");

        } else if (pageFlag == AppConstants.page_home_guidance) { //TODO 这是首页 攻略
            HomeGuidanceHolder holder;
            List<HomeGuidance> list = (List<HomeGuidance>) data;

            if (convertView == null) {
                holder = new HomeGuidanceHolder();
                convertView = View.inflate(ctx, AppConstants.item_home_guidance, null);
                ViewRelayoutUtil.relayoutViewWithScale(convertView, MamShare.screenWidthScale);
                holder.iv_guidance = (ImageView) convertView.findViewById(R.id.iv_guidance);
                convertView.setTag(holder);
            } else {
                holder = (HomeGuidanceHolder) convertView.getTag();
            }
            ImageLoader.getInstance().displayImage(list.get(position).getImageUrl(), holder.iv_guidance);
        } else if (pageFlag == AppConstants.page_home_evaluate) { //TODO 这是首页 评测
            HomeEvaluateHolder holder;
            final List<HomeEvaluate> list = (List<HomeEvaluate>) data;

            if (convertView == null) {
                holder = new HomeEvaluateHolder();
                convertView = View.inflate(ctx, AppConstants.item_home_evaluate, null);
                ViewRelayoutUtil.relayoutViewWithScale(convertView, MamShare.screenWidthScale);
                holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
                holder.tv_desc = (TextView) convertView.findViewById(R.id.tv_desc);
                holder.tv_nickname = (TextView) convertView.findViewById(R.id.tv_nickname);
                holder.tv_role = (TextView) convertView.findViewById(R.id.tv_role);
                holder.tv_label1 = (TextView) convertView.findViewById(R.id.tv_label1);
                holder.tv_label2 = (TextView) convertView.findViewById(R.id.tv_label2);
                holder.tv_label3 = (TextView) convertView.findViewById(R.id.tv_label3);
                holder.tv_label4 = (TextView) convertView.findViewById(R.id.tv_label4);
                holder.tv_label5 = (TextView) convertView.findViewById(R.id.tv_label5);
                holder.iv_cover = (ImageView) convertView.findViewById(R.id.iv_cover);
                holder.iv_avatar = (RoundImageView) convertView.findViewById(R.id.iv_avatar);
                holder.btn_like = (Button) convertView.findViewById(R.id.btn_like);
                convertView.setTag(holder);
            } else {
                holder = (HomeEvaluateHolder) convertView.getTag();
            }
            holder.tv_title.setText(list.get(position).getTitle());
            holder.tv_desc.setText(list.get(position).getRemark());
            holder.tv_nickname.setText(list.get(position).getNickName());
            holder.tv_role.setText(list.get(position).getRoleName());
            holder.tv_label1.setText(list.get(position).getTags());
            holder.btn_like.setText("" + list.get(position).getLikeNum());

            ImageLoader.getInstance().displayImage(list.get(position).getAvatar(), holder.iv_avatar);

            holder.btn_like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //状态改变后刷新数据
                    ToastHelper.showToast(ctx, "喜欢还是讨厌");
                    doLike();
                }
            });
        } else if (pageFlag == AppConstants.page_discover_search) { //TODO
            DiscoverSearchHolder holder;
            List<Tags> list = (List<Tags>) data;

            if (convertView == null) {
                holder = new DiscoverSearchHolder();
                convertView = View.inflate(ctx, AppConstants.item_discover_search, null);
                ViewRelayoutUtil.relayoutViewWithScale(convertView, MamShare.screenWidthScale);
                holder.iv_cover = (ImageView) convertView.findViewById(R.id.iv_cover);
                holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
                convertView.setTag(holder);
            } else {
                holder = (DiscoverSearchHolder) convertView.getTag();
            }
            holder.tv_title.setText(list.get(position).tagName);
            ImageLoader.getInstance().displayImage(list.get(position).coverPhoto, holder.iv_cover);
        } else if (pageFlag == AppConstants.page_recommend_tag) { //TODO  热词
            RecommendTagHolder holder;
            List<String> list = (List<String>) data;

            if (convertView == null) {
                holder = new RecommendTagHolder();
                convertView = View.inflate(ctx, AppConstants.item_recommend_tag, null);
                ViewRelayoutUtil.relayoutViewWithScale(convertView, MamShare.screenWidthScale);
                holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
                convertView.setTag(holder);
            } else {
                holder = (RecommendTagHolder) convertView.getTag();
            }
            holder.tv_title.setText(list.get(position));

        } else if (pageFlag == AppConstants.page_search_guidance||pageFlag==AppConstants.page_collect_guidance) { //TODO 攻略
            GuidanceHolder holder;
            final List<Guidance> list = (List<Guidance>) data;

            if (convertView == null) {
                holder = new GuidanceHolder();
                convertView = View.inflate(ctx, AppConstants.item_home_evaluate, null);
                ViewRelayoutUtil.relayoutViewWithScale(convertView, MamShare.screenWidthScale);
                holder.iv_cover = (ImageView) convertView.findViewById(R.id.iv_cover);
                holder.btn_like = (Button) convertView.findViewById(R.id.btn_like);
                convertView.setTag(holder);
            } else {
                holder = (GuidanceHolder) convertView.getTag();
            }
            holder.btn_like.setText("" + list.get(position).likeNum);

            ImageLoader.getInstance().displayImage(list.get(position).imageUrl, holder.iv_cover);

            holder.btn_like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //状态改变后刷新数据
                    ToastHelper.showToast(ctx, "喜欢还是讨厌");
                    doLike();
                }
            });
        } else if (pageFlag == AppConstants.page_search_evaluate||pageFlag==AppConstants.page_collect_evaluate||pageFlag==AppConstants.page_user_evaluate) { //TODO 评测
            EvaluateHolder holder;
            final List<Evaluate> list = (List<Evaluate>) data;

            if (convertView == null) {
                holder = new EvaluateHolder();
                convertView = View.inflate(ctx, AppConstants.item_home_evaluate, null);
                ViewRelayoutUtil.relayoutViewWithScale(convertView, MamShare.screenWidthScale);
                holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
                holder.iv_cover = (ImageView) convertView.findViewById(R.id.iv_cover);
                holder.btn_like = (Button) convertView.findViewById(R.id.btn_like);
                convertView.setTag(holder);
            } else {
                holder = (EvaluateHolder) convertView.getTag();
            }
            holder.tv_title.setText(list.get(position).title);
            holder.btn_like.setText("" + list.get(position).likeNum);

            ImageLoader.getInstance().displayImage(list.get(position).headUrl, holder.iv_cover);

            holder.btn_like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //状态改变后刷新数据
                    ToastHelper.showToast(ctx, "喜欢还是讨厌");
                    doLike();
                }
            });
        } else {
            ViewHolder holder;
            final List<TestBean> list = (List<TestBean>) data;

            if (convertView == null) {
                holder = new ViewHolder();
                convertView = View.inflate(ctx, AppConstants.item_recommend_tag, null);
                ViewRelayoutUtil.relayoutViewWithScale(convertView, MamShare.screenWidthScale);
                holder.tv_title = (TextView) convertView.findViewById(R.id.title);
                holder.btn_like = (Button) convertView.findViewById(R.id.like);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.tv_title.setText(list.get(position).getTitle());
            holder.btn_like.setText((list.get(position).isLike() ? "喜欢" : "无视"));

            holder.btn_like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastHelper.showToast(ctx, "喜欢还是无视");
                    //状态改变后刷新数据
                    list.get(position).setIsLike(!list.get(position).isLike());
                    notifyDataSetChanged();
                }
            });
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                switch (pageFlag) {
                    case AppConstants.page_home_evaluate:
                        i.setClass(ctx, EvaluateDetailActivity.class);
                        break;
                    case AppConstants.page_home_guidance:
                        i.setClass(ctx, GuidanceDetailActivity.class);
                        break;
                    default:
                        i.setClass(ctx, ParallaxToolbarListViewActivity.class);
                        break;
                }
                ctx.startActivity(i);
            }
        });
        return convertView;

    }

    public void refresh(int pageFlag, List<?> data) {
        this.pageFlag = pageFlag;
        this.data = data;
        notifyDataSetChanged();
    }

    //TODO 可以写一个并集类, 方便使用 为什么要static类型呢 可以试试

    static class ViewHolder {
        TextView tv_title;
        Button btn_like;
    }

    class GuidanceHolder {
        Button btn_like;
        ImageView iv_cover;
    }

    class EvaluateHolder {
        TextView tv_title;
        Button btn_like;
        ImageView iv_cover;
    }

    class HomeGuidanceHolder {
        ImageView iv_guidance;
    }

    class HomeEvaluateHolder {
        TextView tv_title;
        TextView tv_desc;
        TextView tv_nickname;
        TextView tv_role;
        TextView tv_label1;
        TextView tv_label2;
        TextView tv_label3;
        TextView tv_label4;
        TextView tv_label5;
        ImageView iv_cover;
        ImageView iv_avatar;
        Button btn_like;
    }

    class DiscoverSearchHolder {
        TextView tv_title;
        ImageView iv_cover;
    }

    class RecommendTagHolder {
        TextView tv_title;
    }


    public void doLike() {
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
        OkHttpExecutor.query(UrlConstants.VersionCheck, VersionCheckEvent.class, false, this);

    }

    /**
     * EventBus 响应事件
     *
     * @param event
     */
    public void onEventMainThread(VersionCheckEvent event) {
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);
        L.d(OkHttpExecutor.TAG, "onEventMainThread->" + event.getResultStr());
        notifyDataSetChanged();

    }


}
