package com.babyspace.mamshare.adapter.render;

import android.view.View;

/**
 * Created by michael on 2015/6/27.
 */
public interface AdapterTypeRender<T> {
    View getConvertView();
    void bindEvents();
    void bindDatas(T item);
}
