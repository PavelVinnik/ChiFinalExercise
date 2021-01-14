package com.example.chifinalexercise.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chifinalexercise.R;

class ChannelItemViewHolder extends RecyclerView.ViewHolder{

    private TextView mChannelTextView;

    public ChannelItemViewHolder(@NonNull View itemView) {
        super(itemView);
        mChannelTextView = itemView.findViewById(R.id.channel_item_text_view);
    }

    public void bind(String title, View.OnClickListener listener) {
        mChannelTextView.setText(title);
        mChannelTextView.setOnClickListener(listener);
    }

    static ChannelItemViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_channel, parent, false);
        return new ChannelItemViewHolder(view);
    }
}
