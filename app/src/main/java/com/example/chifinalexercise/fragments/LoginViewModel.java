package com.example.chifinalexercise.fragments;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.chifinalexercise.DataRepository;

public class LoginViewModel extends AndroidViewModel {

    private DataRepository mRepository;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        mRepository = DataRepository.getInstance(application);
    }

    public void registration(String username, String email, String password){
        mRepository.registration(username, email, password);
    }

    public void login(String email, String password){
        mRepository.login(email, password);
    }
}
