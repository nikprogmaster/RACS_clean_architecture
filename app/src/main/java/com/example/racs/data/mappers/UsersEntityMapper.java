package com.example.racs.data.mappers;

import com.example.racs.model.data.UsersEntityData;
import com.example.racs.model.domain.UsersEntityDomain;

public class UsersEntityMapper extends Mapper<UsersEntityData.User, UsersEntityDomain> {


    @Override
    public UsersEntityDomain map(UsersEntityData.User value) {
        UsersEntityDomain entityDomain = new UsersEntityDomain();
        entityDomain.setCardId(value.getCardId());
        entityDomain.setEmail(value.getEmail());
        entityDomain.setFirstName(value.getFirstName());
        entityDomain.setLastName(value.getLastName());
        entityDomain.setPatronymic(value.getPatronymic());
        entityDomain.setIsSuperuser(value.getIsSuperuser());
        entityDomain.setUId(value.getUId());
        entityDomain.setRole(value.getRole());
        return entityDomain;
    }
}
