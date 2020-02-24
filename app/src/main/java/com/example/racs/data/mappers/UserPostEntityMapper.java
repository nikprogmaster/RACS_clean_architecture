package com.example.racs.data.mappers;

import com.example.racs.model.data.UserPostEntityData;
import com.example.racs.model.domain.UserPostEntityDomain;

import java.util.List;

public class UserPostEntityMapper extends Mapper<UserPostEntityDomain, UserPostEntityData> {

    @Override
    public UserPostEntityData map(UserPostEntityDomain value) {
        UserPostEntityData entityData = new UserPostEntityData();
        entityData.setCardId(value.getCardId());
        entityData.setEmail(value.getEmail());
        entityData.setFirstName(value.getFirstName());
        entityData.setLastName(value.getLastName());
        entityData.setPatronymic(value.getPatronymic());
        entityData.setRole(value.getRole());
        return entityData;
    }

    @Override
    public List<UserPostEntityData> map(List<UserPostEntityDomain> values) {
        throw new UnsupportedOperationException();
    }
}
