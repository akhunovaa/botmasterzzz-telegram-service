package com.botmasterzzz.telegram.dao.impl;

import com.botmasterzzz.telegram.dao.TelegramMediaLogDAO;
import com.botmasterzzz.telegram.entity.TelegramMediaLogEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
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

    @Override
    @SuppressWarnings({"deprecation"})
    public long getCountOfMediaLog(Long mediaId) {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(TelegramMediaLogEntity.class);
        criteria.createAlias("telegramUserMediaEntity", "media");
        criteria.add( Restrictions.eq("media.id", mediaId));
        criteria.setProjection(Projections.rowCount());
        Long mediaCount = (Long) criteria.uniqueResult();
        session.close();
        return mediaCount;
    }

    @Override
    @SuppressWarnings({"deprecation"})
    public long getCountOfMediaLogToUser(Long telegramUserId) {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(TelegramMediaLogEntity.class);
        criteria.add( Restrictions.eq("telegramUserId", telegramUserId));
        criteria.setProjection(Projections.rowCount());
        Long mediaCount = (Long) criteria.uniqueResult();
        session.close();
        return mediaCount;
    }

    @Override
    @SuppressWarnings({"deprecation"})
    public long getUsersCountOfMediaLog(Long telegramUserId) {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(TelegramMediaLogEntity.class);
        criteria.createAlias("telegramUserMediaEntity", "media");
        criteria.createAlias("media.telegramBotUserEntity", "user");
        criteria.add(Restrictions.eq("user.telegramId", telegramUserId));
        criteria.setProjection(Projections.rowCount());
        Long mediaCount = (Long) criteria.uniqueResult();
        session.close();
        return mediaCount;
    }
}
