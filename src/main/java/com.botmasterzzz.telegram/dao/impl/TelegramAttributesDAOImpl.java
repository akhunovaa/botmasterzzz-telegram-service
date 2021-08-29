package com.botmasterzzz.telegram.dao.impl;

import com.botmasterzzz.telegram.dao.TelegramAttributesDAO;
import com.botmasterzzz.telegram.entity.TelegramAttributesDataEntity;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TelegramAttributesDAOImpl implements TelegramAttributesDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(TelegramAttributesDAOImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void telegramAttributeAdd(TelegramAttributesDataEntity telegramAttributesDataEntity) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(telegramAttributesDataEntity);
            tx.commit();
        } catch (Exception he) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
    }

    @Override
    @SuppressWarnings({"deprecation"})
    public TelegramAttributesDataEntity telegramAttributeGet(Long telegramUserId, Long instanceId, String attributeName) {
        TelegramAttributesDataEntity telegramAttributesDataEntity = null;
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(TelegramAttributesDataEntity.class);
        criteria.add(Restrictions.eq("telegramBotUserEntity.telegramId", telegramUserId));
        criteria.add(Restrictions.eq("telegramInstanceEntity.id", instanceId));
        criteria.add(Restrictions.eq("name", attributeName));
        try{
            telegramAttributesDataEntity = (TelegramAttributesDataEntity) criteria.list().get(0);
        }catch (QueryException | IndexOutOfBoundsException e){
            session.close();
        }finally {
            session.close();
        }
        return telegramAttributesDataEntity;
    }
}
