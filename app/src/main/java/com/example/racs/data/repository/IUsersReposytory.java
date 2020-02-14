package com.example.racs.data.repository;

import com.example.racs.data.entities.UserPostEntity;
import com.example.racs.data.entities.UsersEntity;

import java.util.List;

public interface IUsersReposytory {

    List<UsersEntity.User> getUsers(String token, int count);

    boolean addUser(String token, UserPostEntity body);

    boolean deleteUser(String token, int id);

}
