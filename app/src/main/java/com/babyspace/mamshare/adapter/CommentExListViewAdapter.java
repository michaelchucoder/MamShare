package com.babyspace.mamshare.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.bean.CommentItem;
import com.babyspace.mamshare.bean.SubCommentItem;

import java.util.ArrayList;


public class CommentExListViewAdapter extends BaseExpandableListAdapter implements View.OnClickListener {
    private Context ctx;
    private LayoutInflater mInflater;
    private ArrayList<CommentItem> mCommentItemList;
    private AlertDialog mCommentEditDialog;
    private int mCurrentGroupPosition = 0;

    public CommentExListViewAdapter(Context context, ArrayList<CommentItem> commentItemList) {
        ctx = context;
        mInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mCommentItemList = commentItemList;

        createCommentEditDialog();
    }

    private void createCommentEditDialog() {
        View commentInputView = mInflater.inflate(R.layout.dialog_sub_comment, null);
        final EditText commentEdit = (EditText) commentInputView.findViewById(R.id.dialogSubComment_commentContentInput_edt);

        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
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
                SubCommentItem subCommentItem = new SubCommentItem(R.drawable.push, "评论用户", commentContent);
                if (!commentContent.equals("")) {
                    mCommentItemList.get(mCurrentGroupPosition).getSubCommentItems().add(subCommentItem);

                    notifyDataSetChanged();
                }
            }
        });

        mCommentEditDialog = builder.create();
    }

    @Override
    public int getGroupCount() {
        return mCommentItemList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        CommentItem commentItem = mCommentItemList.get(groupPosition);
        if (commentItem.getSubCommentItems() == null) {
            return 0;
        } else {
            return commentItem.getSubCommentItems().size();
        }
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mCommentItemList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        CommentItem commentItem = mCommentItemList.get(groupPosition);
        if (commentItem.getSubCommentItems() == null) {
            return null;
        } else {
            return commentItem.getSubCommentItems().get(childPosition);
        }
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        CommentViewHolder commentViewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_comment_list, null);
            commentViewHolder = new CommentViewHolder(convertView);
            convertView.setTag(commentViewHolder);
        } else {
            commentViewHolder = (CommentViewHolder) convertView.getTag();
        }

        CommentItem commentItem = mCommentItemList.get(groupPosition);
        commentViewHolder.userHeaderImg.setImageResource(commentItem.getUserHeaderSourceId());
        commentViewHolder.userNameTv.setText(commentItem.getUserName());
        commentViewHolder.createTimeTv.setText(commentItem.getCreateTime());
        commentViewHolder.contentTv.setText(commentItem.getContent());
        commentViewHolder.replyBtn.setTag(groupPosition);
        commentViewHolder.replyBtn.setOnClickListener(this);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        SubCommentViewHolder subCommentViewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_comment_list_sub, null);
            subCommentViewHolder = new SubCommentViewHolder(convertView);
            convertView.setTag(subCommentViewHolder);
        } else {
            subCommentViewHolder = (SubCommentViewHolder) convertView.getTag();
        }

        CommentItem commentItem = mCommentItemList.get(groupPosition);
        if (commentItem.getSubCommentItems() != null) {
            SubCommentItem subCommentItem = commentItem.getSubCommentItems().get(childPosition);

            subCommentViewHolder.userHeaderImg.setImageResource(subCommentItem.getUserHeaderImgId());
            subCommentViewHolder.userNameTv.setText(subCommentItem.getUserName());
            subCommentViewHolder.contentTv.setText(subCommentItem.getContent());

            return convertView;
        } else {
            return null;
        }

    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.commentListViewItem_reply_btn:
                mCurrentGroupPosition = (Integer) v.getTag();

                mCommentEditDialog.show();
                // 每次评论前，清空Dialog输入框的内容
                ((EditText) mCommentEditDialog.findViewById(R.id.dialogSubComment_commentContentInput_edt)).setText("");
                break;
        }
    }


    class CommentViewHolder {
        ImageView userHeaderImg;
        TextView userNameTv;
        TextView createTimeTv;
        TextView contentTv;
        Button replyBtn;

        CommentViewHolder(View parent) {
            userHeaderImg = (ImageView) parent.findViewById(R.id.commentListViewItem_userHeader_img);
            userNameTv = (TextView) parent.findViewById(R.id.commentListViewItem_userName_tv);
            createTimeTv = (TextView) parent.findViewById(R.id.commentListViewItem_createTime_tv);
            contentTv = (TextView) parent.findViewById(R.id.commentListViewItem_commentContent_tv);
            replyBtn = (Button) parent.findViewById(R.id.commentListViewItem_reply_btn);
        }
    }

    class SubCommentViewHolder {
        ImageView userHeaderImg;
        TextView userNameTv;
        TextView contentTv;

        SubCommentViewHolder(View parent) {
            userHeaderImg = (ImageView) parent.findViewById(R.id.commentListViewChildItem_userHeader_img);
            userNameTv = (TextView) parent.findViewById(R.id.commentListViewChildItem_userName_tv);
            contentTv = (TextView) parent.findViewById(R.id.commentListViewChildItem_content_tv);
        }
    }
}
