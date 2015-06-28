package com.babyspace.mamshare.adapter.render;

import android.content.Context;
import android.widget.TextView;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.basement.adapter.render.BaseTypeAdapter;
import com.babyspace.mamshare.basement.adapter.render.BaseTypeRender;
import com.babyspace.mamshare.bean.ChatMessage;


/**
 * Created by michael on 2015/6/27.
 */
public class TextToRender extends BaseTypeRender<ChatMessage> {
    private TextView to;
    public TextToRender(Context context, BaseTypeAdapter baseTypeAdapter) {
        super(context, baseTypeAdapter, R.layout.chat_text_to_item);
    }
    @Override
    public void bindEvents() {

    }
    @Override
    public void bindDatas(ChatMessage item) {
        to= obtainView(mConvertView,R.id.to);
        to.setText(item.getContent());
    }
}
