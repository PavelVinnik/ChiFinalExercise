package com.example.chifinalexercise.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chifinalexercise.R;
import com.example.chifinalexercise.fragments.VideoFragment;
import com.example.chifinalexercise.model.Channel;

import java.util.List;

public class ChannelListAdapter extends RecyclerView.Adapter<ChannelItemViewHolder> {
    private List<Channel> mChannelList;

    private Context mContext;

    public ChannelListAdapter(List<Channel> channelList, Context activityContext) {
        mChannelList = channelList;
        mContext = activityContext;
    }

    @NonNull
    @Override
    public ChannelItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ChannelItemViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ChannelItemViewHolder holder, int position) {
        Channel current = mChannelList.get(position);
        holder.bind(current.getChannelTitle(), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AppCompatActivity)mContext).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, VideoFragment.newInstance(current.getChannelId()))
                        .addToBackStack("top")
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mChannelList.size();
    }

}


