package com.example.chifinalexercise.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.chifinalexercise.R;

public class LoginFragment extends Fragment {

    private EditText mUsernameEditText;
    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private Button mLoginButton;
    private Button mRegistrationhButton;

    private LoginViewModel mLoginViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        mLoginViewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);

        mUsernameEditText = v.findViewById(R.id.user_name_edit_text);
        mEmailEditText = v.findViewById(R.id.email_edit_text);
        mPasswordEditText = v.findViewById(R.id.password_edit_text);
        mLoginButton = v.findViewById(R.id.login_button);
        mRegistrationhButton = v.findViewById(R.id.registration_button);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoginViewModel.login(
                        mEmailEditText.getText().toString(),
                        mPasswordEditText.getText().toString());
            }
        });

        mRegistrationhButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoginViewModel.registration(
                        mUsernameEditText.getText().toString(),
                        mEmailEditText.getText().toString(),
                        mPasswordEditText.getText().toString());
            }
        });

        return v;
    }

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }
}
