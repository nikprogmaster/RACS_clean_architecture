package com.example.racs.presentation.viewmodel;

import com.example.racs.data.api.App;

public interface OnReceiverSecretData {
    void onReceive(String email, String password);

    void onGetState(App.ApplicationState state);
}
