package com.example.chifinalexercise.fragments;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.example.chifinalexercise.DataRepository;
import com.example.chifinalexercise.model.Channel;

import java.util.List;

public class ChannelViewModel extends AndroidViewModel {

    private DataRepository mDataRepository;

    private LiveData<List<Channel>> mAllChannels;

    public ChannelViewModel(Application application){
        super(application);
        mDataRepository = DataRepository.getInstance(application);
        mAllChannels = mDataRepository.getAllChannels();
    }

    public LiveData<List<Channel>> getAllChannels(){
        return mAllChannels;
    }
}
