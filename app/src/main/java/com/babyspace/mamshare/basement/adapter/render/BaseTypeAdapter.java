package com.babyspace.mamshare.basement.adapter.render;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.babyspace.mamshare.R;

import java.util.List;


/**
 * Created by michael on 2015/6/27.
 */
public abstract class BaseTypeAdapter<T> extends BaseAdapter {
    protected List<T> mList;
    protected Context mContext;


    public BaseTypeAdapter( Context context,List<T> list) {
        mContext = context;
        mList = list;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AdapterTypeRender typeRender;
        if(null==convertView){
            typeRender=getAdapterTypeRender(position);
            convertView=typeRender.getConvertView();
            convertView.setTag(R.id.item_render_type,typeRender);
            typeRender.bindEvents();
        }else{
            typeRender= (AdapterTypeRender) convertView.getTag(R.id.item_render_type);
        }
        convertView.setTag(R.id.item_position,position);
        if(null!=typeRender){
            T item=(T)getItem(position);
            typeRender.bindDatas(item);
        }
        return convertView;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public T getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public abstract AdapterTypeRender getAdapterTypeRender(int position);



}
