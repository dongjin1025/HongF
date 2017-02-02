package com.dongjin.android.hongf.model;

import java.util.ArrayList;

/**
 * Created by kimdongjin on 2017. 1. 30..
 */

public class Channel
{
    private ArrayList<Item> item;

    private Info info;

    public ArrayList<Item> getItem ()
    {
        return item;
    }

    public void setItem (ArrayList<Item> item)
    {
        this.item = item;
    }

    public Info getInfo ()
    {
        return info;
    }

    public void setInfo (Info info)
    {
        this.info = info;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [item = "+item+", info = "+info+"]";
    }
}