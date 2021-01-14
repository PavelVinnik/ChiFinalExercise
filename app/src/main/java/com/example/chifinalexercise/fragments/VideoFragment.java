package com.example.chifinalexercise.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chifinalexercise.R;
import com.example.chifinalexercise.adapters.VideoListAdapter;
import com.example.chifinalexercise.model.Video;

import java.util.List;

public class VideoFragment extends Fragment {

    private static final String ARG_CHANNEL_ID = "channelId";

    private RecyclerView mRecyclerView;
    private VideoListAdapter mAdapter;
    private VideoViewModel mVideoViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_video, container, false);
        int channelId = getArguments().getInt(ARG_CHANNEL_ID);
        mVideoViewModel = new ViewModelProvider(requireActivity()).get(VideoViewModel.class);
        mRecyclerView = v.findViewById(R.id.video_list_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        if (mVideoViewModel.getAllVideos(channelId).getValue() != null) {
            mAdapter = new VideoListAdapter(mVideoViewModel.getAllVideos(channelId).getValue());
            mRecyclerView.setAdapter(mAdapter);
        }


        mVideoViewModel.getAllVideos(channelId).observe(getViewLifecycleOwner(), new Observer<List<Video>>() {
            @Override
            public void onChanged(List<Video> videos) {
                if (videos != null) {
                    mAdapter = new VideoListAdapter(mVideoViewModel.getAllVideos(channelId).getValue());
                    mRecyclerView.setAdapter(mAdapter);
                }
            }
        });
        return v;
    }

    public static VideoFragment newInstance(int channelId) {
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_CHANNEL_ID, channelId);
        VideoFragment videoFragment = new VideoFragment();
        videoFragment.setArguments(bundle);
        return videoFragment;
    }


}


