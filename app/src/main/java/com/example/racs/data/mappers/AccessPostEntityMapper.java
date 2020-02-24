package com.example.racs.data.mappers;

import com.example.racs.model.data.AccessPostEntityData;
import com.example.racs.model.domain.AccessPostEntityDomain;

import java.util.List;

public class AccessPostEntityMapper extends Mapper<AccessPostEntityDomain, AccessPostEntityData> {

    @Override
    public AccessPostEntityData map(AccessPostEntityDomain value) {
        AccessPostEntityData entityData = new AccessPostEntityData();
        entityData.setA_id(value.getA_id());
        entityData.setLock(value.getLock());
        entityData.setUser(value.getUser());
        entityData.setAccessStart(value.getAccessStart());
        entityData.setAccessStop(value.getAccessStop());
        return entityData;
    }

    @Override
    public List<AccessPostEntityData> map(List<AccessPostEntityDomain> values) {
        throw new UnsupportedOperationException();
    }
}
