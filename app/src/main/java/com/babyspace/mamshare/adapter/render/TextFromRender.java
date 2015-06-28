package com.babyspace.mamshare.adapter.render;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.adapter.ChatAdapter;
import com.babyspace.mamshare.basement.adapter.listener.OnCovertViewClickListener;
import com.babyspace.mamshare.basement.adapter.render.BaseTypeAdapter;
import com.babyspace.mamshare.basement.adapter.render.BaseTypeRender;
import com.babyspace.mamshare.bean.ChatMessage;

/**
 * Created by michael on 2015/6/27.
 */
public class TextFromRender extends BaseTypeRender<ChatMessage> {
    private TextView from;
    private ImageView photo;
    public TextFromRender(Context context, BaseTypeAdapter baseTypeAdapter) {
        super(context, baseTypeAdapter, R.layout.chat_text_from_item);
    }


    @Override
    public void bindEvents() {
        //监听器
        OnCovertViewClickListener listener=new OnCovertViewClickListener(mConvertView,R.id.item_position) {
            @Override
            public void onClickCallBack(View convertView, int position) {
                switch (convertView.getId()){
                    case R.id.photo:
                        //如果点击的是头像
                        ChatAdapter.OnMessageItemListener messageItemListener=((ChatAdapter) mBaseTypeAdapter).getOnMessageItemListener();
                        if (null != messageItemListener) {
                            messageItemListener.onPhotoClicked(position);
                            //回调
                        }
                        break;
                    case R.id.from:
                        //如果点击的是消息
                        ChatAdapter.OnMessageItemListener messageItemListener1=((ChatAdapter) mBaseTypeAdapter).getOnMessageItemListener();
                        if (null != messageItemListener1) {
                            messageItemListener1.onMessageClicked(position);
                            //回调
                        }
                        break;
                }

            }
        };
        //设置监听器
        obtainView(mConvertView,R.id.photo).setOnClickListener(listener);
        obtainView(mConvertView,R.id.from).setOnClickListener(listener);
    }


    @Override
    public void bindDatas(ChatMessage item) {
        //绑定数据
        from= obtainView(mConvertView,R.id.from);

        from.setText(item.getContent());
    }

}
