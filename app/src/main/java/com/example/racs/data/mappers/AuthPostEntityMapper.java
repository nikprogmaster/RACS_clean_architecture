package com.example.racs.data.mappers;

import com.example.racs.model.data.AuthPostEntityData;
import com.example.racs.model.domain.AuthPostEntityDomain;

import java.util.List;

public class AuthPostEntityMapper extends Mapper<AuthPostEntityDomain, AuthPostEntityData> {

    @Override
    public AuthPostEntityData map(AuthPostEntityDomain value) {
        return new AuthPostEntityData(value.getEmail(), value.getPassword());
    }

    @Override
    public List<AuthPostEntityData> map(List<AuthPostEntityDomain> values) {
        throw new UnsupportedOperationException();
    }
}
