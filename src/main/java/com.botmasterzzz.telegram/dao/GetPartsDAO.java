package com.botmasterzzz.telegram.dao;

import com.botmasterzzz.telegram.entity.GetPartsEntity;

import java.util.List;

public interface GetPartsDAO {

    long getPartsAdd(GetPartsEntity getPartsEntity);

    List<GetPartsEntity> getGetPartsEntityList();

    GetPartsEntity getPartsEntityUpdate(GetPartsEntity getPartsEntity);

    void  projectDelete(GetPartsEntity getPartsEntity);

    GetPartsEntity getPartsEntityGet(String name);

    GetPartsEntity getPartsEntityGet(Long id);

    boolean getPartsEntityExists(String name);

    GetPartsEntity getPartsSearchEntityGet(String text);
}

