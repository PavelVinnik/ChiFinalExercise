package com.example.chifinalexercise.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.chifinalexercise.R;
import com.example.chifinalexercise.model.User;

public class UserFragment extends Fragment {

    private TextView mUsernameTextView;
    private TextView mEmailTextView;
    private Button mLogoutButton;

    private UserViewModel mUserViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_user, container, false);
        mUserViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        mUsernameTextView = v.findViewById(R.id.username_text_view);
        mEmailTextView = v.findViewById(R.id.email_text_view);
        mLogoutButton = v.findViewById(R.id.logout_button);
        mUserViewModel.getUser().observe(requireActivity(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                mEmailTextView.setText(user.getEmail());
                mUsernameTextView.setText(user.getUsername());
            }
        });
        mLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUserViewModel.logout();
            }
        });
        return v;
    }

    public static UserFragment newInstance(){
        return new UserFragment();
    }
}
