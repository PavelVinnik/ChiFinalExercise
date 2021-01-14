package com.example.chifinalexercise;

import android.app.Application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class MainActivityViewModel extends AndroidViewModel {

    private DataRepository mRepository;
    private LiveData<Boolean> mLoginStatus;

    public MainActivityViewModel(Application application){
        super(application);
        mRepository = DataRepository.getInstance(application);
        mLoginStatus = mRepository.getLoginStatus();
    }

    public LiveData<Boolean> getLoginStatus(){
        return mLoginStatus;
    }
}
