package com.example.chifinalexercise.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "channel_table")
public class Channel {

    @PrimaryKey
    private int mChannelId;

    @ColumnInfo(name = "title")
    private String mChannelTitle;

    public Channel(int channelId, String channelTitle) {
        this.mChannelId = channelId;
        this.mChannelTitle = channelTitle;
    }

    public int getChannelId() {
        return mChannelId;
    }

    public String getChannelTitle() {
        return mChannelTitle;
    }
}
