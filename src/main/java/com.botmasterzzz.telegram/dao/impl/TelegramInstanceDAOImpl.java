package com.botmasterzzz.telegram.dao.impl;

import com.botmasterzzz.telegram.dao.TelegramInstanceDAO;
import com.botmasterzzz.telegram.entity.TelegramInstanceEntity;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TelegramInstanceDAOImpl implements TelegramInstanceDAO {


    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Long telegramInstanceAdd(TelegramInstanceEntity telegramInstanceEntity) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(telegramInstanceEntity);
        session.getTransaction().commit();
        session.close();
        return telegramInstanceEntity.getId();
    }

    @Override
    @SuppressWarnings({"deprecation"})
    public TelegramInstanceEntity telegramInstanceGet(Long userId, Long projectId) {
        TelegramInstanceEntity telegramInstanceEntity = null;
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(TelegramInstanceEntity.class);
        criteria.add(Restrictions.eq("user.id", userId));
        criteria.add(Restrictions.eq("project.id", projectId));
        try{
            telegramInstanceEntity = (TelegramInstanceEntity) criteria.list().get(0);
        }catch (QueryException | IndexOutOfBoundsException e){
            session.close();
        }finally {
            session.close();
        }
        return telegramInstanceEntity;
    }

    @Override
    @SuppressWarnings({"deprecation", "unchecked"})
    public List<TelegramInstanceEntity> getTelegramInstanceList(long userId) {
        List<TelegramInstanceEntity> telegramInstanceEntity;
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(TelegramInstanceEntity.class);
        criteria.add(Restrictions.eq("user.id", userId));
        telegramInstanceEntity = criteria.list();
        session.close();
        return telegramInstanceEntity;
    }

    @Override
    public void telegramInstanceDelete(TelegramInstanceEntity telegramInstanceEntity) {
        Session session = sessionFactory.openSession();
        Transaction updateTransaction = session.beginTransaction();
        session.delete(telegramInstanceEntity);
        updateTransaction.commit();
        session.close();
    }

    @Override
    public TelegramInstanceEntity telegramInstanceUpdate(TelegramInstanceEntity telegramInstanceEntity) {
        Session session = sessionFactory.openSession();
        Transaction updateTransaction = session.beginTransaction();
        session.update(telegramInstanceEntity);
        updateTransaction.commit();
        session.close();
        return telegramInstanceEntity;
    }
}
