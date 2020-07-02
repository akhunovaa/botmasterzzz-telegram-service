package com.botmasterzzz.telegram.dao.impl;

import com.botmasterzzz.telegram.dao.TelegramUserDAO;
import com.botmasterzzz.telegram.entity.TelegramBotUserEntity;
import com.botmasterzzz.telegram.entity.TelegramInstanceEntity;
import org.hibernate.*;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TelegramUserDAOImpl implements TelegramUserDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public long telegramUserAdd(TelegramBotUserEntity telegramBotUserEntity) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(telegramBotUserEntity);
            tx.commit();
            System.out.println("User: '" + telegramBotUserEntity + "' successfully approved.");
        } catch (Exception he) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
        return telegramBotUserEntity.getId();
    }

    @Override
    @SuppressWarnings({"deprecation"})
    public TelegramBotUserEntity telegramUserGet(long id) {
        TelegramBotUserEntity telegramBotUserEntity = null;
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(TelegramInstanceEntity.class);
        criteria.add(Restrictions.eq("id", id));
        try{
            telegramBotUserEntity = (TelegramBotUserEntity) criteria.list().get(0);
        }catch (QueryException | IndexOutOfBoundsException e){
            session.close();
        }finally {
            session.close();
        }
        return telegramBotUserEntity;
    }

    @Override
    @SuppressWarnings({"deprecation"})
    public TelegramBotUserEntity telegramUserGetTelegramId(long telegramId) {
        TelegramBotUserEntity telegramBotUserEntity = null;
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(TelegramBotUserEntity.class);
        criteria.add(Restrictions.eq("telegramId", telegramId));
        try{
            telegramBotUserEntity = (TelegramBotUserEntity) criteria.list().get(0);
        }catch (QueryException | IndexOutOfBoundsException e){
            session.close();
        }finally {
            session.close();
        }
        return telegramBotUserEntity;
    }

    @Override
    @SuppressWarnings({"deprecation", "unchecked"})
    public List<TelegramBotUserEntity> getTelegramUserList() {
        List<TelegramBotUserEntity> telegramBotUserEntityList;
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(TelegramBotUserEntity.class);
        telegramBotUserEntityList = criteria.list();
        session.close();
        return telegramBotUserEntityList;
    }

    @Override
    @SuppressWarnings({"deprecation"})
    public boolean exists(Class<?> clazz, long idValue) {
        Session session = sessionFactory.openSession();
        boolean exists =  session.createCriteria(clazz)
                .add(Restrictions.eq("telegramId", idValue))
                .setProjection(Projections.id())
                .uniqueResult() != null;
        session.close();
        return exists;
    }
}
