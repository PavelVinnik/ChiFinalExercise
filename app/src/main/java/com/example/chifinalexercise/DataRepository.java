package com.example.chifinalexercise;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.chifinalexercise.bd.ChannelDao;
import com.example.chifinalexercise.bd.ChannelRoomDatabase;
import com.example.chifinalexercise.model.Channel;
import com.example.chifinalexercise.model.User;
import com.example.chifinalexercise.model.Video;
import com.example.chifinalexercise.utils.JsonUtils;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DataRepository {

    private static final String TAG = "DataRepository";

    private static DataRepository sInstance;

    private static final String SHARED_PREFERENCES_NAME = "finalExercisePref";
    private static final String IS_CHANNELS_FETCHED_KEY = "isChannelsFetched";
    private static final String USER_TOKEN_KEY = "userToken";
    private static final String USER_ID_KEY = "userId";

    private static final String DEVICE_TOKEN = "HGWYUwbd53bqsjdwwoi4c";
    private static final String OS_TYPE = "android";

    private static final int LIMIT_VIDEO_NUMBER = 5;

    private final String mUrlChannels = "https://api.screenlife.com/api/get-channels";
    private final String mUrlVideos = "https://api.screenlife.com/api-mobile/search";
    private final String mUrlRegistration = "https://api.screenlife.com/api-mobile/user/create";
    private final String mUrlLogin = "https://api.screenlife.com/api-mobile/user/login";
    private final String mUrlLogout = "https://api.screenlife.com/api-mobile/user/logout";
    private final String mUrlUserById = "https://api.screenlife.com/api-mobile/user/get-by-id";
    private OkHttpClient mClient = new OkHttpClient();

    private SharedPreferences mPreferences;
    private ChannelDao mChannelDao;
    private LiveData<List<Channel>> mAllChannels;
    private MutableLiveData<List<Video>> mVideoList;
    private MutableLiveData<Boolean> mLoginStatus;
    private MutableLiveData<User> mUser;


    private DataRepository(Application application) {
        mPreferences = application.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        ChannelRoomDatabase db = ChannelRoomDatabase.getDatabase(application);
        mChannelDao = db.channelDao();
        mAllChannels = mChannelDao.getChannels();
        mVideoList = new MutableLiveData<>();
        mLoginStatus = new MutableLiveData<>();
        mUser = new MutableLiveData<>();
        if (mPreferences.getString(USER_TOKEN_KEY, "") == ""){
            mLoginStatus.setValue(false);
        }else {
            mLoginStatus.setValue(true);
        }
        if (!mPreferences.getBoolean(IS_CHANNELS_FETCHED_KEY, false)) {
            fetchChannelList();
        }
    }

    public static DataRepository getInstance(Application application) {
        if (sInstance == null) {
            sInstance = new DataRepository(application);
        }
        return sInstance;
    }

    public LiveData<List<Channel>> getAllChannels() {
        return mAllChannels;
    }

    public LiveData<List<Video>> getVideos(int channelId) {
        fetchVideoList(channelId);
        return mVideoList;
    }

    public void insertChannel(Channel channel) {
        ChannelRoomDatabase.sDatabaseWrightExecutor.execute(() ->
                mChannelDao.insert(channel));
    }

    private String getUserToken() {
        return mPreferences.getString(USER_TOKEN_KEY, null);
    }

    private void setUserToken(String userToken) {
        Log.d(TAG, userToken);
        mPreferences.edit().putString(USER_TOKEN_KEY, userToken).apply();
    }


    public LiveData<Boolean> getLoginStatus() {
        return mLoginStatus;
    }

    public void setLoginStatus(boolean status){
        mLoginStatus.setValue(status);
    }

    public void setUserId(int id){
        mPreferences.edit().putInt(USER_ID_KEY, id).apply();
    }

    public int getUserId(){
        return mPreferences.getInt(USER_ID_KEY, 0);
    }

    private void setUser(User user){
        mUser.setValue(user);
    }

    public LiveData<User> getUser(){
        getUserById(getUserId());
        return mUser;
    }


    private void fetchChannelList() {
        Request getChannels = new Request.Builder()
                .url(mUrlChannels)
                .build();

        mClient.newCall(getChannels).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                List<Channel> list = JsonUtils.parseChanelList(response.body().string());
                for (int i = 0; i < list.size(); i++) {
                    insertChannel(list.get(i));
                }
                mPreferences.edit().putBoolean(IS_CHANNELS_FETCHED_KEY, true).apply();
            }
        });
    }

    private void fetchVideoList(int channelId) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(mUrlVideos).newBuilder();
        urlBuilder.addQueryParameter("limit", String.valueOf(LIMIT_VIDEO_NUMBER));
        urlBuilder.addQueryParameter("channel", String.valueOf(channelId));
        urlBuilder.addQueryParameter("type", "video");
        String urlVideoRequest = urlBuilder.build().toString();
        Request getVideosOfChannel = new Request.Builder()
                .url(urlVideoRequest)
                .build();


        mClient.newCall(getVideosOfChannel).enqueue(new Callback() {
            Handler mHandler = new Handler(Looper.getMainLooper());

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                List<Video> list = JsonUtils.parseVideoList(response.body().string());
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mVideoList.setValue(list);
                    }
                });
            }
        });
    }

    public void registration(String username, String email, String password) {

        RequestBody registrationBody = new FormBody.Builder()
                .add("email", email)
                .add("username", username)
                .add("password", password)
                .add("device_token", DEVICE_TOKEN)
                .add("os_type", OS_TYPE)
                .build();

        Request registration = new Request.Builder()
                .url(mUrlRegistration)
                .post(registrationBody)
                .build();

        mClient.newCall(registration).enqueue(new Callback() {
            Handler mHandler = new Handler(Looper.getMainLooper());

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.code() == 200){
                    String responseString = response.body().string();
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            login(email, password);
                        }
                    });
                }
            }
        });
    }

    public void login(String email, String password) {
        RequestBody loginBody = new FormBody.Builder()
                .add("email", email)
                .add("password", password)
                .add("device_token", DEVICE_TOKEN)
                .add("os_type", OS_TYPE)
                .build();
        Request login = new Request.Builder()
                .url(mUrlLogin)
                .post(loginBody)
                .build();
        mClient.newCall(login).enqueue(new Callback() {
            Handler mHandler = new Handler(Looper.getMainLooper());

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.code() == 200){
                    String responseString = response.body().string();
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            setUserToken(JsonUtils.parseToken(responseString));
                            setUserId(JsonUtils.parseUserId(responseString));
                            setLoginStatus(true);
                        }
                    });
                }
            }

        });
    }

    public void getUserById(int id){

        HttpUrl.Builder urlBuilder = HttpUrl.parse(mUrlUserById).newBuilder();
        urlBuilder.addQueryParameter("id", String.valueOf(id));
        String urlUserById = urlBuilder.build().toString();
        Request userById = new Request.Builder()
                .url(urlUserById)
                .header("Authorization", "Bearer " + getUserToken())
                .build();
        mClient.newCall(userById).enqueue(new Callback() {
            Handler mHandler = new Handler(Looper.getMainLooper());
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if(response.code() == 200){
                    String responseString = response.body().string();
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            setUser(JsonUtils.parseUser(responseString));
                        }
                    });
                }
            }
        });
    }

    public void logout(){
        RequestBody logoutBody = new FormBody.Builder()
                .add("device_token", DEVICE_TOKEN)
                .add("os_type", OS_TYPE)
                .build();
        Request logout = new Request.Builder()
                .url(mUrlLogout)
                .header("Authorization", "Bearer " + getUserToken())
                .post(logoutBody)
                .build();
        mClient.newCall(logout).enqueue(new Callback() {
            Handler mHandler = new Handler(Looper.getMainLooper());
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if(response.code() == 200){
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            setLoginStatus(false);
                            setUserToken("");
                            setUserId(0);
                        }
                    });
                }
            }
        });
    }
}
