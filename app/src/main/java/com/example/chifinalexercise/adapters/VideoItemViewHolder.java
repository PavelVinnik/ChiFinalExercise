package com.example.chifinalexercise.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chifinalexercise.R;

public class VideoItemViewHolder extends RecyclerView.ViewHolder {

    private TextView mTitleTextView;
    private TextView mLikesTextView;
    private TextView mDescriptionTextView;

    public VideoItemViewHolder(@NonNull View itemView) {
        super(itemView);
        mTitleTextView = itemView.findViewById(R.id.video_title_text_view);
        mLikesTextView = itemView.findViewById(R.id.video_likes_text_view);
        mDescriptionTextView = itemView.findViewById(R.id.video_description_text_view);
    }

    public void bind(String title, int likes, String description) {
        mTitleTextView.setText(title);
        mLikesTextView.setText("\uD83D\uDC4D " + likes);
        if (description == null) {
            mDescriptionTextView.setVisibility(View.GONE);
        } else {
            mDescriptionTextView.setText(description);
        }

    }

    static VideoItemViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_video, parent, false);
        return new VideoItemViewHolder(view);
    }
}
