package com.botmasterzzz.telegram.dao;

import com.botmasterzzz.telegram.entity.AccountEntity;
import com.botmasterzzz.telegram.entity.LotsEntity;

import java.util.List;

public interface ReklamDAO {

    // заявки, принятые пользователем (customer) на исполнение
    List<LotsEntity> lotsCustomerEntityList(String customer);

    // активные заявки пользователя (creator)
    List<LotsEntity> activeLotsForCreatorEntityList(long creator, boolean isArchive);

    // доступные заявки к принятию в работу
    List<LotsEntity> availableLotsEntityList();

    // Получение лота / заявки
    LotsEntity loadLots(long id);

    // Добавление заявки
    LotsEntity addLot();

    // получить лот
    LotsEntity getLot(long id);

    // список с постраничным выводом для кастомера
    public List<LotsEntity> getLotsForCustomer(int offset, int limit);

    List<LotsEntity> getLotsListAccept(int offset, int limit, long customer);

    // получить валидность/доступность лота (isvalide)
    boolean validateLot(long id);

    // установить валидность лота
    void setValidateLot(long id, boolean status);

    // проверить оплату лота
    boolean checkPay(long id);

    // установить статус оплаченности лота
    void setPayLot(long id, boolean status);

    // лот создан? (звершено создание лота)
    boolean isCreateLot(long id);

    // установить статус завершения создания лота
    void setCreateLot(long id, boolean status);

    // установить исполнителя лота
    void setCustomerLot(long id, long status);

    // лот архивен?
    boolean isArchiveLot(long id);

    // установить статус архивности лота
    void setArchiveLot(long id, boolean status);

    // лот удален?
    boolean isDelLot(long id);

    // установить статус удаления лота
    void setDelLot(long id, boolean status);

    // лот заблокирован?
    boolean isLockLot(long id);

    // установить статус блокировки лота
    void setLockLot(long id, boolean status);

    // список счетов
    List<AccountEntity> accountsList();

    // получить счет
    AccountEntity getAccount(long id);

    // получить счет по ид пользователя
    AccountEntity getAccountid(int userid);

    // получить остаток счета
    double getAccountTotal(long id);

    //аккаунт по id пользователя
    AccountEntity getAccountByUserId(long id);

    // получить последнее(активное) пополнение счета
    double getAccountIncome(long id);

    // установить пополнение счета
    void setAccountIncome(long id, double count);

    // получить последний(активный) вывод со счета
    double getAccountOutcome(long id);

    // установить вывод со счета
    void setAccountOutcome(long id, double count);

    // получить статус блокировки аккаунта
    boolean getAccountIslock(long id);

    // установить статус блокировки аккаунта
    void setAccountIslock(long id, boolean status);

    // получить статус активации аккаунта
    boolean getAccountIsactive(long id);

    // установить статус активации аккаунта
    void setAccountIsactive(long id, boolean status);



}
