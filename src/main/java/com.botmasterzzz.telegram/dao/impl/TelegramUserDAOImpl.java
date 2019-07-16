package com.botmasterzzz.telegram.dao.impl;

import com.botmasterzzz.telegram.dao.TelegramUserDAO;
import com.botmasterzzz.telegram.entity.TelegramBotUserEntity;
import com.botmasterzzz.telegram.entity.TelegramInstanceEntity;
import org.hibernate.Criteria;
import org.hibernate.QueryException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
        session.beginTransaction();
        session.save(telegramBotUserEntity);
        session.getTransaction().commit();
        session.close();
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
    @SuppressWarnings({"deprecation", "unchecked"})
    public List<TelegramBotUserEntity> getTelegramUserList() {
        List<TelegramBotUserEntity> telegramBotUserEntityList;
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(TelegramBotUserEntity.class);
        telegramBotUserEntityList = criteria.list();
        session.close();
        return telegramBotUserEntityList;
    }
}
