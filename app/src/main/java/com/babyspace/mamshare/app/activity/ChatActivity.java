package com.babyspace.mamshare.app.activity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.adapter.ChatAdapter;
import com.babyspace.mamshare.basement.BaseActivity;
import com.babyspace.mamshare.bean.Message;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends BaseActivity {

    private ListView mListView;
    private ChatAdapter mAdapter;
    private List<Message> mList=new ArrayList<Message>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initView();
        initData();
    }

    private void initView() {
        mListView= (ListView) findViewById(R.id.listview);
        mAdapter=new ChatAdapter(this,mList);
        mListView.setAdapter(mAdapter);
        mAdapter.setOnMessageItemListener(new ChatAdapter.OnMessageItemListener() {
            @Override
            public void onPhotoClicked(int position) {
                Toast.makeText(ChatActivity.this, "from photo:" + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onMessageClicked(int position) {
                Message message=mAdapter.getItem(position);
                Toast.makeText(ChatActivity.this, "from message:"+message.getContent(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initData() {
        Message m=null;
        for (int i=1;i<=10;i++){
            m=new Message();
            m.setType(Message.TYPE_FROM);
            m.setContent("form "+i);
            mList.add(m);
            m=new Message();
            m.setType(Message.TYPE_TO);
            m.setContent("to "+i);
            mList.add(m);

        }



        mAdapter.notifyDataSetChanged();
    }
}
