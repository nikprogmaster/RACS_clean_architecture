package com.example.racs.data.mappers;

import com.example.racs.model.data.LocksEntityData;
import com.example.racs.model.domain.LocksEntityDomain;

public class LocksEntityMapper extends Mapper<LocksEntityData.Lock, LocksEntityDomain> {

    @Override
    public LocksEntityDomain map(LocksEntityData.Lock value) {
        LocksEntityDomain entityDomain = new LocksEntityDomain();
        entityDomain.setLId(value.getLId());
        entityDomain.setUuid(value.getUuid());
        entityDomain.setDescription(value.getDescription());
        entityDomain.setIsApproved(value.getIsApproved());
        entityDomain.setIsOn(value.getIsOn());
        entityDomain.setLastEcho(value.getLastEcho());
        entityDomain.setVersion(value.getVersion());
        return entityDomain;
    }


}
