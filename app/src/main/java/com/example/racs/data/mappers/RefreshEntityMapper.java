package com.example.racs.data.mappers;

import com.example.racs.model.data.RefreshEntityData;
import com.example.racs.model.domain.RefreshEntityDomain;

import java.util.List;

public class RefreshEntityMapper extends Mapper<RefreshEntityDomain, RefreshEntityData> {

    @Override
    public RefreshEntityData map(RefreshEntityDomain value) {
        RefreshEntityData entityData = new RefreshEntityData();
        entityData.setRefresh(value.getRefresh());
        return entityData;
    }

    @Override
    public List<RefreshEntityData> map(List<RefreshEntityDomain> values) {
        throw new UnsupportedOperationException();
    }
}
