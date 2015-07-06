package com.babyspace.mamshare.basement.adapter.render;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by michael on 2015/6/27.
 */
public abstract class BaseTypeRender<T> implements AdapterTypeRender<T> {
    protected Context mContext;
    protected BaseTypeAdapter<T> mBaseTypeAdapter;
    protected View mConvertView;

    public BaseTypeRender(Context context, BaseTypeAdapter<T> baseTypeAdapter, int resID) {
        mContext = context;
        mBaseTypeAdapter = baseTypeAdapter;
        mConvertView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(resID, null);
    }

    @Override
    public View getConvertView() {
        return mConvertView;
    }

    public static <V extends View> V obtainView(View convertView, int id) {
        SparseArray<View> holder = (SparseArray<View>) convertView.getTag();
        if (holder == null) {
            holder = new SparseArray<View>();
            convertView.setTag(holder);
        }
        View childView = holder.get(id);
        if (childView == null) {
            childView = convertView.findViewById(id);
            holder.put(id, childView);
        }
        return (V) childView;
    }
}
