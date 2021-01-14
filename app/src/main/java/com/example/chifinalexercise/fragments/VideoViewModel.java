package com.example.chifinalexercise.fragments;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.chifinalexercise.DataRepository;
import com.example.chifinalexercise.model.Video;

import java.util.List;

public class VideoViewModel extends AndroidViewModel {

    private DataRepository mDataRepository;

    private LiveData<List<Video>> mAllVideos;

    public VideoViewModel(Application application) {
        super(application);
        mDataRepository = DataRepository.getInstance(application);
        mAllVideos = new MutableLiveData<>();
    }

    public LiveData<List<Video>> getAllVideos(int channelId) {
        mAllVideos = mDataRepository.getVideos(channelId);
        return mAllVideos;
    }
}
