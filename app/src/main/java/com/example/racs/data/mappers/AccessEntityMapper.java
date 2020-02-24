package com.example.racs.data.mappers;

import com.example.racs.model.data.AccessEntityData;
import com.example.racs.model.domain.AccessEntityDomain;

public class AccessEntityMapper extends Mapper<AccessEntityData.Access, AccessEntityDomain> {


    @Override
    public AccessEntityDomain map(AccessEntityData.Access value) {
        AccessEntityDomain access = new AccessEntityDomain();
        access.setA_id(value.getA_id());
        access.setLock(value.getLock());
        access.setUser(value.getUser());
        access.setCardId(value.getCardId());
        access.setLockDesc(value.getLockDesc());
        access.setAccessStart(value.getAccessStart());
        access.setAccessStop(value.getAccessStop());
        return access;
    }

}
