package com.example.racs.data.repository;

import com.example.racs.data.entities.AuthEntity;
import com.example.racs.data.entities.AuthPostEntity;

public interface IAuthRepository {

    AuthEntity getTokens(AuthPostEntity body);

}