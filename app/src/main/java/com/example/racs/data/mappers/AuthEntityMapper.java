package com.example.racs.data.mappers;

import com.example.racs.model.data.AuthEntityData;
import com.example.racs.model.domain.AuthEntityDomain;

public class AuthEntityMapper extends Mapper<AuthEntityData, AuthEntityDomain> {

    @Override
    public AuthEntityDomain map(AuthEntityData value) {
        AuthEntityDomain entityDomain = new AuthEntityDomain();
        entityDomain.setAccess(value.getAccess());
        entityDomain.setRefresh(value.getRefresh());
        return entityDomain;
    }

}
