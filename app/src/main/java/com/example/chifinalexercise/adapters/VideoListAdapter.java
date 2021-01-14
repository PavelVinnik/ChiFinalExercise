package com.example.chifinalexercise.adapters;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chifinalexercise.model.Video;

import java.util.List;

public class VideoListAdapter extends RecyclerView.Adapter<VideoItemViewHolder> {

    private List<Video> mVideoList;

    public VideoListAdapter(List<Video> videoList) {
        mVideoList = videoList;
    }

    @NonNull
    @Override
    public VideoItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return VideoItemViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoItemViewHolder holder, int position) {
        Video current = mVideoList.get(position);
        holder.bind(current.getTitle(), current.getLikes(), current.getDescription());
    }

    @Override
    public int getItemCount() {
        return mVideoList.size();
    }

    public void setNewList(List<Video> videoList){
        mVideoList = videoList;
        this.notifyDataSetChanged();

    }
}
