package com.botmasterzzz.telegram.dao.impl;

import com.botmasterzzz.telegram.dao.TelegramUserMediaDAO;
import com.botmasterzzz.telegram.entity.TelegramMediaStatisticEntity;
import com.botmasterzzz.telegram.entity.TelegramUserMediaEntity;
import org.hibernate.*;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TelegramUserMediaDAOImpl implements TelegramUserMediaDAO {

    @Autowired
    private SessionFactory sessionFactory;


    @Override
    public Long telegramUserMediaAdd(TelegramUserMediaEntity telegramUserMediaEntity) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(telegramUserMediaEntity);
        session.getTransaction().commit();
        session.close();
        return telegramUserMediaEntity.getId();
    }

    @Override
    public void mediaTouchAdd(TelegramMediaStatisticEntity telegramMediaStatisticEntity) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(telegramMediaStatisticEntity);
        session.getTransaction().commit();
        session.close();
    }

    @SuppressWarnings("deprecation")
    @Override
    public TelegramUserMediaEntity telegramUserMediaGet(Long id) {
        TelegramUserMediaEntity telegramUserMediaEntity = null;
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(TelegramUserMediaEntity.class);
        criteria.add(Restrictions.eq("id", id));
        criteria.setMaxResults(1);
        try {
            telegramUserMediaEntity = (TelegramUserMediaEntity) criteria.list().get(0);
        } catch (QueryException | IndexOutOfBoundsException e) {
            session.close();
        } finally {
            session.close();
        }
        return telegramUserMediaEntity;
    }

    @SuppressWarnings("deprecation")
    @Override
    public Optional<TelegramMediaStatisticEntity> findTouchTypeMedia(long telegramUserId, Long mediaFileId, String touchType) {
        TelegramMediaStatisticEntity telegramMediaStatisticEntity = null;
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(TelegramMediaStatisticEntity.class);
        criteria.add(Restrictions.eq("telegramUserMediaEntity.id", mediaFileId));
        criteria.add(Restrictions.eq("telegramUserId", telegramUserId));
        criteria.add(Restrictions.eq("touchType", touchType));
        criteria.setMaxResults(1);
        try {
            telegramMediaStatisticEntity  = (TelegramMediaStatisticEntity) criteria.uniqueResult();
        } catch (HibernateException e) {
            session.close();
        } finally {
            session.close();
        }
        return Optional.ofNullable(telegramMediaStatisticEntity);
    }

    @SuppressWarnings({"deprecation"})
    @Override
    public long countUserTouch(long telegramUserId, Long mediaFileId, String touchType) {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(TelegramMediaStatisticEntity.class)
                .setProjection(Projections.rowCount());
        criteria.add(Restrictions.eq("telegramUserMediaEntity.id", mediaFileId));
        criteria.add(Restrictions.eq("telegramUserId", telegramUserId));
        criteria.add(Restrictions.eq("touchType", touchType));
        Long rowCount = 0L;
        List result = criteria.list();
        if (!result.isEmpty()) {
            rowCount = (Long) result.get(0);
        }
        session.close();
        return rowCount;
    }

    @SuppressWarnings({"deprecation", "unchecked"})
    @Override
    public List<TelegramUserMediaEntity> telegramUserMediaList(int mediaType) {
        List<TelegramUserMediaEntity> telegramUserMediaEntityList;
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(TelegramUserMediaEntity.class);
        criteria.add(Restrictions.eq("fileType", mediaType));
        telegramUserMediaEntityList = criteria.list();
        session.close();
        return telegramUserMediaEntityList;
    }
}
