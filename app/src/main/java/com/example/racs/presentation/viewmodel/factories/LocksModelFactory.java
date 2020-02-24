package com.example.racs.presentation.viewmodel.factories;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.racs.data.repository.LocksRepository;
import com.example.racs.presentation.viewmodel.LocksViewModel;

public class LocksModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final LocksRepository locksRepository;

    public LocksModelFactory(LocksRepository locksRepository) {
        super();
        this.locksRepository = locksRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == LocksViewModel.class) {
            return (T) new LocksViewModel(locksRepository);
        }
        return null;
    }
}
