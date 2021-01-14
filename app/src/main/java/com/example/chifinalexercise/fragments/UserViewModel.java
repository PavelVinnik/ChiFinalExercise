package com.example.chifinalexercise.fragments;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.chifinalexercise.DataRepository;
import com.example.chifinalexercise.model.User;

public class UserViewModel extends AndroidViewModel {

    private DataRepository mRepository;

    public UserViewModel(Application application){
        super(application);
        mRepository = DataRepository.getInstance(application);

    }

    public LiveData<User> getUser(){
        return mRepository.getUser();
    }

    public void logout(){
        mRepository.logout();
    }
}
