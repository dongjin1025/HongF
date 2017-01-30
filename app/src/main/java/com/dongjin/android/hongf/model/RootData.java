package com.dongjin.android.hongf.model;

/**
 * Created by kimdongjin on 2017. 1. 30..
 */

public class RootData
{
    private Channel channel;

    public Channel getChannel ()
    {
        return channel;
    }

    public void setChannel (Channel channel)
    {
        this.channel = channel;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [channel = "+channel+"]";
    }
}