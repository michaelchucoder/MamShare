package com.babyspace.mamshare.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.basement.MamShare;
import com.babyspace.mamshare.bean.TestBean;
import com.michael.core.tools.ViewRelayoutUtil;

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
public class MamaFeatureAdapter extends BaseAdapter {
    private List<TestBean> data = new ArrayList<TestBean>();
    private Context context;
    private int resource = R.layout.item_mama_feature;

    public MamaFeatureAdapter(Context context) {
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

            holder.btn_avatar_1 = (Button) convertView.findViewById(R.id.btn_avatar_1);
            holder.btn_avatar_2 = (Button) convertView.findViewById(R.id.btn_avatar_2);
            holder.btn_avatar_3 = (Button) convertView.findViewById(R.id.btn_avatar_3);

            holder.btn_title_1 = (Button) convertView.findViewById(R.id.btn_title_1);
            holder.btn_title_2 = (Button) convertView.findViewById(R.id.btn_title_2);
            holder.btn_title_3 = (Button) convertView.findViewById(R.id.btn_title_3);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        TestBean testBean1 = data.get(3 * (position));
        TestBean testBean2;
        TestBean testBean3;


        holder.btn_avatar_1.setText(testBean1.isLike() ? "喜欢" : "无视");
        holder.btn_title_1.setText(testBean1.getTitle());


        if (data.size() <= 3 * (position) + 1) {
            holder.ll_container_2.setVisibility(View.INVISIBLE);
            holder.ll_container_3.setVisibility(View.INVISIBLE);

        } else if ((data.size() <= 3 * (position) + 2)) {
            testBean2 = data.get(3 * (position) + 1);
            holder.btn_avatar_1.setText(testBean2.isLike() ? "喜欢" : "无视");
            holder.btn_title_1.setText(testBean2.getTitle());

            holder.ll_container_3.setVisibility(View.VISIBLE);

        } else {
            testBean2 = data.get(3 * (position) + 1);
            holder.btn_avatar_1.setText(testBean2.isLike() ? "喜欢" : "无视");
            holder.btn_title_1.setText(testBean2.getTitle());

            testBean3 = data.get(3 * (position) + 2);
            holder.btn_avatar_1.setText(testBean3.isLike() ? "喜欢" : "无视");
            holder.btn_title_1.setText(testBean3.getTitle());
        }


        holder.ll_container_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        holder.ll_container_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        holder.ll_container_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        return convertView;
    }


    static class ViewHolder {
        LinearLayout ll_container_1;
        Button btn_avatar_1;
        Button btn_title_1;

        LinearLayout ll_container_2;
        Button btn_avatar_2;
        Button btn_title_2;

        LinearLayout ll_container_3;
        Button btn_avatar_3;
        Button btn_title_3;
    }

}