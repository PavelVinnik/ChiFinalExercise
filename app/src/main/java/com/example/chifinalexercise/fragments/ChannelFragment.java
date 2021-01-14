package com.example.chifinalexercise.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chifinalexercise.adapters.ChannelListAdapter;
import com.example.chifinalexercise.R;
import com.example.chifinalexercise.model.Channel;

import java.util.List;

public class ChannelFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private ChannelListAdapter mAdapter;
    private ChannelViewModel mChannelViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_channel, container, false);

        mChannelViewModel = new ViewModelProvider(requireActivity()).get(ChannelViewModel.class);

        mRecyclerView = v.findViewById(R.id.channel_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        if (mChannelViewModel.getAllChannels().getValue() != null){
            mAdapter = new ChannelListAdapter(mChannelViewModel.getAllChannels().getValue(), getActivity());
            mRecyclerView.setAdapter(mAdapter);
        }

        mChannelViewModel.getAllChannels().observe(getViewLifecycleOwner(), new Observer<List<Channel>>() {
            @Override
            public void onChanged(List<Channel> channels) {
                if (channels!=null){
                    mAdapter = new ChannelListAdapter(channels, getActivity());
                    mRecyclerView.setAdapter(mAdapter);
                }
            }
        });

        return v;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.user_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.open_user: {
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.container, UserFragment.newInstance())
                        .addToBackStack(null)
                        .commit();
                return true;
            }
            default:{
                return super.onOptionsItemSelected(item);
            }
        }
    }

    public static ChannelFragment newInstance(){
        return new ChannelFragment();
    }

}
