package com.babyspace.mamshare.bean;

/**
 * Created by lizhangqu on 2015/6/21.
 */
public class ChatMessage {
    public static final int TYPE_FROM=0x00;
    public static final int TYPE_TO=0x01;
    public static final int TOTAL=2;

    private int type;
    private String content;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
