package com.babyspace.mamshare.bean;

/**
 * Created with Android Studio
 * Package name: com.babyspace.mamshare.bean
 * Author: MichaelChuCoder
 * Date: 2015-7-4
 * Time: 9:40
 * To change this template use File | Settings | File and Code Templates.
 */
public class TestBean {

    public TestBean(String title, boolean isLike) {
        this.title = title;
        this.isLike = isLike;
    }

    private String title;
    private boolean isLike;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isLike() {
        return isLike;
    }

    public void setIsLike(boolean isLike) {
        this.isLike = isLike;
    }

}
