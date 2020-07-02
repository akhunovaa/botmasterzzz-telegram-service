package com.botmasterzzz.telegram.dao.impl;

import com.botmasterzzz.telegram.dao.TelegramStatisticDAO;
import com.botmasterzzz.telegram.entity.TelegramBotUsageStatisticEntity;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TelegramStatisticDAOImpl implements TelegramStatisticDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public long telegramStatisticAdd(TelegramBotUsageStatisticEntity telegramBotUsageStatisticEntity) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(telegramBotUsageStatisticEntity);
            tx.commit();
            System.out.println("User: '" + telegramBotUsageStatisticEntity + "' successfully approved.");
        } catch (HibernateException he) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
        return telegramBotUsageStatisticEntity.getId();
    }

    @Override
    @SuppressWarnings({"deprecation"})
    public TelegramBotUsageStatisticEntity telegramStatisticGet(long id) {
        TelegramBotUsageStatisticEntity telegramBotUsageStatisticEntity = null;
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(TelegramBotUsageStatisticEntity.class);
        criteria.add(Restrictions.eq("id", id));
        try {
            telegramBotUsageStatisticEntity = (TelegramBotUsageStatisticEntity) criteria.list().get(0);
        } catch (QueryException | IndexOutOfBoundsException e) {
            session.close();
        } finally {
            session.close();
        }
        return telegramBotUsageStatisticEntity;
    }

    @Override
    @SuppressWarnings({"deprecation", "unchecked"})
    public List<TelegramBotUsageStatisticEntity> getTelegramStatisticList() {
        List<TelegramBotUsageStatisticEntity> telegramBotUsageStatisticEntityList;
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(TelegramBotUsageStatisticEntity.class);
        telegramBotUsageStatisticEntityList = criteria.list();
        session.close();
        return telegramBotUsageStatisticEntityList;
    }
}
