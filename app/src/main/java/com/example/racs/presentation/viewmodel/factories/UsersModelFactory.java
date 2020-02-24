package com.example.racs.presentation.viewmodel.factories;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.racs.data.repository.UsersRepository;
import com.example.racs.presentation.viewmodel.UsersViewModel;

public class UsersModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final UsersRepository usersRepository;

    public UsersModelFactory(UsersRepository usersRepository) {
        super();
        this.usersRepository = usersRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == UsersViewModel.class) {
            return (T) new UsersViewModel(usersRepository);
        }
        return null;
    }

}
