package com.botmasterzzz.telegram.dao.impl;

import com.botmasterzzz.telegram.dao.TelegramMediaLogDAO;
import com.botmasterzzz.telegram.entity.TelegramMediaLogEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

@Repository
public class TelegramMediaLogDAOImpl implements TelegramMediaLogDAO {

    private final SessionFactory sessionFactory;

    public TelegramMediaLogDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void mediaLogAdd(TelegramMediaLogEntity telegramMediaLogEntity) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(telegramMediaLogEntity);
            tx.commit();
        } catch (Exception he) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
    }
}
