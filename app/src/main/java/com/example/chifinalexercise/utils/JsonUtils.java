package com.example.chifinalexercise.utils;

import android.util.Log;

import com.example.chifinalexercise.model.Channel;
import com.example.chifinalexercise.model.User;
import com.example.chifinalexercise.model.Video;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String TAG = "JsonUtils";

    public static List<Channel> parseChanelList(String jsonString) {
        List<Channel> channelList = new ArrayList<>();

        try {
            JSONObject jsonBody = new JSONObject(jsonString);
            JSONArray channelJsonArray = jsonBody.getJSONArray("data");
            for (int i = 0; i < channelJsonArray.length(); i++) {
                JSONObject channelObject = channelJsonArray.getJSONObject(i);
                int channelId = channelObject.getInt("id");
                String channelTitle = channelObject.getString("title");
                channelList.add(new Channel(channelId, channelTitle));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return channelList;
    }

    public static List<Video> parseVideoList(String jsonString) {
        List<Video> videoList = new ArrayList<>();

        try {
            JSONObject jsonBody = new JSONObject(jsonString);
            JSONObject jsonData = jsonBody.getJSONObject("data");
            JSONObject jsonVideo = jsonData.getJSONObject("video");
            JSONArray videoJsonArray = jsonVideo.getJSONArray("data");
            for (int i = 0; i < videoJsonArray.length(); i++) {
                JSONObject videoObject = videoJsonArray.getJSONObject(i);
                String title = videoObject.getString("title");
                String description;
                if (videoObject.isNull("description")) {
                    description = null;
                } else {
                    description = videoObject.getString("description");
                }
                int likes = videoObject.getInt("count_likes");
                videoList.add(new Video(title, description, likes));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return videoList;
    }

    public static String parseToken(String jsonString){
        String token = null;
        try {
            JSONObject jsonBody = new JSONObject(jsonString);
            token = jsonBody.getString("accessToken");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return token;
    }

    public static int parseUserId(String jsonString){
        int userId = 0;
        try {
            JSONObject jsonBody = new JSONObject(jsonString);
            JSONObject jsonUser = jsonBody.getJSONObject("user");
            userId = jsonUser.getInt("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return userId;
    }

    public static User parseUser(String jsonString){
        User user = null;
        try {
            JSONObject jsonBody = new JSONObject(jsonString);
            JSONObject jsonData = jsonBody.getJSONObject("data");
            JSONObject jsonUser = jsonData.getJSONObject("user");
            String username = jsonUser.getString("username");
            String email = jsonUser.getString("email");
            user = new User(username, email);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return  user;
    }

    public static boolean isStatusTrue(String jsonString){
        boolean isStatusTrue = false;
        try {
            JSONObject jsonBody = new JSONObject(jsonString);
            if (jsonBody.getString("status").equals("true")){
                Log.d(TAG, jsonBody.getString("status"));
                isStatusTrue = true;
            }
        } catch (JSONException e){
            e.printStackTrace();
        }
        return isStatusTrue;
    }
}
