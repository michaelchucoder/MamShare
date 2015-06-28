package com.babyspace.mamshare.adapter;

import android.content.Context;

import com.babyspace.mamshare.adapter.render.TextFromRender;
import com.babyspace.mamshare.adapter.render.TextToRender;
import com.babyspace.mamshare.basement.adapter.render.AdapterTypeRender;
import com.babyspace.mamshare.basement.adapter.render.BaseTypeAdapter;
import com.babyspace.mamshare.bean.Message;

import java.util.List;


/**
 * Created by michael on 2015/6/27.
 */
public class ChatAdapter extends BaseTypeAdapter<Message> {

    public ChatAdapter(Context context, List<Message> list) {
        super(context, list);
    }

    @Override
    public AdapterTypeRender getAdapterTypeRender(int position) {
        AdapterTypeRender<Message> typeRender=null;
        switch (getItemViewType(position)){
            case Message.TYPE_FROM:
                typeRender=new TextFromRender(mContext,this);

                break;
            case Message.TYPE_TO:
                typeRender=new TextToRender(mContext,this);
                break;
        }
        return typeRender;
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position).getType();
    }

    @Override
    public int getViewTypeCount() {
        return Message.TOTAL;
    }

    public interface OnMessageItemListener{
        void onPhotoClicked(int position);
        void onMessageClicked(int position);
    }
    private OnMessageItemListener onChatItemListener;
    public void setOnMessageItemListener(OnMessageItemListener onChatItemListener) {
        this.onChatItemListener = onChatItemListener;
    }
    public OnMessageItemListener getOnMessageItemListener() {
        return onChatItemListener;
    }

}
