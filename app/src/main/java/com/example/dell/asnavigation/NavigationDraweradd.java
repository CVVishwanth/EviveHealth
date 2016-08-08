package com.example.dell.asnavigation;

/**
 * Created by dell on 7/30/2016.
 */
public class NavigationDraweradd {

    private boolean showNotify;
    private String title;


    public NavigationDraweradd() {

    }

    public NavigationDraweradd(boolean showNotify, String title) {
        this.showNotify = showNotify;
        this.title = title;
    }

    public boolean isShowNotify() {
        return showNotify;
    }

    public void setShowNotify(boolean showNotify) {
        this.showNotify = showNotify;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
