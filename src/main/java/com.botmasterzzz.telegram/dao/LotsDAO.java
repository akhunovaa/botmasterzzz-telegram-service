package com.botmasterzzz.telegram.dao;

import com.botmasterzzz.telegram.entity.LotsEntity;

import java.util.List;

public interface LotsDAO {

    // заявки, принятые пользователем (customer) на исполнение
    List<LotsEntity> lotsCustomerEntityList(String customer);

    // активные заявки пользователя (creator)
    List<LotsEntity> activeLotsForCreatorEntityList(String creator, boolean isArchive);

    // доступные заявки к принятию в работу
    List<LotsEntity> availableLotsEntityList();

    LotsEntity loadLots(long id);

    LotsEntity addLot();

    LotsEntity getLot(long id);

    boolean validateLot(long id);

    void setValidateLot(long id);

    boolean checkPay(long id);

    void setPayLot(long id);

    boolean isCreateLot(long id);

    void setCreateLot(long id);

    void setCustomerLot(long id);

    boolean isArchiveLot(long id);

    void setArchiveLot(long id);

    boolean isDelLot(long id);

    void setDelLot(long id);

    boolean isLockLot(long id);

    void setLockLot(long id);

}
