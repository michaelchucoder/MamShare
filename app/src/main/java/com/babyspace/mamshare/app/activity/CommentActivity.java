package com.babyspace.mamshare.app.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.adapter.CommentExListViewAdapter;
import com.babyspace.mamshare.basement.BaseActivity;
import com.babyspace.mamshare.bean.CommentItem;
import com.babyspace.mamshare.bean.SubCommentItem;

import java.util.ArrayList;
import java.util.Calendar;

public class CommentActivity extends BaseActivity {

    private Context mContext;
    private ExpandableListView mCommentExLV;
    private CommentExListViewAdapter mCommentExListViewAdapter;
    private ArrayList<CommentItem> mCommentItemList;
    private AlertDialog mCommentEditDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        mContext = this;

        Button commentBtn = (Button) findViewById(R.id.main_comment_btn);
        commentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCommentEditDialog.show();
                //清空输入框
                ((EditText) mCommentEditDialog.findViewById(R.id.dialogComment_commentContentInput_edt)).setText("");
            }
        });

        mCommentExLV = (ExpandableListView) findViewById(R.id.main_commentList_elv);
        initTestCommentItemList();
        mCommentExListViewAdapter = new CommentExListViewAdapter(mContext, mCommentItemList);
        mCommentExLV.setAdapter(mCommentExListViewAdapter);
        mCommentExLV.setGroupIndicator(null);
        expandAllGroup();


        createCommentEditDialog();
    }

    private void expandAllGroup() {
        // 默认展开每一个分组
        for (int i = 0; i < mCommentExListViewAdapter.getGroupCount(); i++) {
            mCommentExLV.expandGroup(i);
        }
    }

    private void initTestCommentItemList() {
        mCommentItemList = new ArrayList<CommentItem>();
        //测试数据
        CommentItem tempCommentItem = new CommentItem(R.drawable.push, "王红1", "12:30", "这是测试评论内容", null);
        mCommentItemList.add(tempCommentItem);

        ArrayList<SubCommentItem> subCommentItems = new ArrayList<SubCommentItem>();
        SubCommentItem tempSubCommentItem = new SubCommentItem(R.drawable.push, "小红1", "这是子测试评论内容1");
        subCommentItems.add(tempSubCommentItem);

        tempSubCommentItem = new SubCommentItem(R.drawable.push, "小红2", "这是子测试评论内容2");
        subCommentItems.add(tempSubCommentItem);

        tempSubCommentItem = new SubCommentItem(R.drawable.push, "小红3", "这是子测试评论内容3");
        subCommentItems.add(tempSubCommentItem);

        tempCommentItem = new CommentItem(R.drawable.push, "王红2", "18:30", "这是测试评论内容2", subCommentItems);
        mCommentItemList.add(tempCommentItem);
    }

    private void createCommentEditDialog() {
        View commentInputView = getLayoutInflater().inflate(R.layout.dialog_comment, null);
        final EditText commentEdit = (EditText) commentInputView.findViewById(R.id.dialogComment_commentContentInput_edt);

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("请输入内容");
        builder.setView(commentInputView);
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String commentContent = commentEdit.getText().toString().trim();
                CommentItem commentItem = new CommentItem(R.drawable.push, "当前用户名", getCurrentDateTime(), commentContent, null);
                if (!commentContent.equals("")) {
                    mCommentItemList.add(commentItem);

                    mCommentExListViewAdapter.notifyDataSetChanged();
                    expandAllGroup();
                }
            }
        });

        mCommentEditDialog = builder.create();
    }

    private String getCurrentDateTime() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);

        return year + "-" + convertNumToString(month) + "-" + convertNumToString(day) + " " + convertNumToString(hour) + ":" + convertNumToString(minute) + ":" + second;
    }

    private String convertNumToString(int num) {
        if (num < 10) {
            return "0" + num;
        } else {
            return "" + num;
        }
    }

}
