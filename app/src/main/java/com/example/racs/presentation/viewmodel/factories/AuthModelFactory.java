package com.example.racs.presentation.viewmodel.factories;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.racs.data.repository.AuthRepository;
import com.example.racs.presentation.viewmodel.AuthViewModel;

public class AuthModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final AuthRepository authRepository;

    public AuthModelFactory(@NonNull AuthRepository authRepository) {
        super();
        this.authRepository = authRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == AuthViewModel.class) {
            return (T) new AuthViewModel(authRepository);
        }
        return null;
    }
}
