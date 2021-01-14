package com.example.chifinalexercise.bd;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.chifinalexercise.model.Channel;

import java.util.List;

@Dao
public interface ChannelDao {

    @Insert
    void insert(Channel channel);

    @Query("SELECT * FROM channel_table")
    LiveData<List<Channel>> getChannels();
}
