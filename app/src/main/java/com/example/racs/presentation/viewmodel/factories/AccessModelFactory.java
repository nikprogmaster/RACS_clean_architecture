package com.example.racs.presentation.viewmodel.factories;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.racs.data.repository.AccessesRepository;
import com.example.racs.presentation.viewmodel.AccessViewModel;

public class AccessModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final AccessesRepository accessesRepository;

    public AccessModelFactory(AccessesRepository accessesRepository) {
        super();
        this.accessesRepository = accessesRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == AccessViewModel.class) {
            return (T) new AccessViewModel(accessesRepository);
        }
        return null;
    }

}
