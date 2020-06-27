package com.botmasterzzz.telegram.dao.impl;

import com.botmasterzzz.telegram.dao.TelegramUserMediaDAO;
import com.botmasterzzz.telegram.dto.TopRatingUsersDTO;
import com.botmasterzzz.telegram.entity.TelegramMediaStatisticEntity;
import com.botmasterzzz.telegram.entity.TelegramRatingStatisticEntity;
import com.botmasterzzz.telegram.entity.TelegramUserMediaEntity;
import org.hibernate.*;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
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

    @Override
    public void telegramUserMediaUpdate(TelegramUserMediaEntity telegramUserMediaEntity) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(telegramUserMediaEntity);
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
            telegramMediaStatisticEntity = (TelegramMediaStatisticEntity) criteria.uniqueResult();
        } catch (HibernateException e) {
            session.close();
        } finally {
            session.close();
        }
        return Optional.ofNullable(telegramMediaStatisticEntity);
    }

    @SuppressWarnings({"deprecation"})
    @Override
    public long countUserTouch(Long mediaFileId, String touchType) {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(TelegramMediaStatisticEntity.class)
                .setProjection(Projections.rowCount());
        criteria.add(Restrictions.eq("telegramUserMediaEntity.id", mediaFileId));
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
        criteria.add(Restrictions.eq("isDeleted", false));
        criteria.add(Restrictions.eq("fileType", mediaType));
        criteria.addOrder(Order.desc("audWhenCreate"));
        telegramUserMediaEntityList = criteria.list();
        session.close();
        return telegramUserMediaEntityList;
    }

    @SuppressWarnings({"deprecation", "unchecked"})
    @Override
    public List<TopRatingUsersDTO> topActiveUsersGet() {
        List<TopRatingUsersDTO> telegramRatingStatisticEntityList;
        Session session = sessionFactory.openSession();

        Criteria criteria = session.createCriteria(TelegramRatingStatisticEntity.class);
        criteria.createAlias("telegramBotUserEntity", "user");

        ProjectionList projList = Projections.projectionList();
        projList.add(Projections.count("touchType").as("countOfTouch"));
        projList.add(Projections.groupProperty("user.id").as("userId"));
        projList.add(Projections.groupProperty("user.telegramId").as("telegramUserId"));
        projList.add(Projections.groupProperty("user.username").as("telegramUserName"));
        projList.add(Projections.groupProperty("user.lastName").as("telegramLastName"));
        projList.add(Projections.groupProperty("user.firstName").as("telegramFirstName"));
        criteria.setProjection(projList);
        criteria.setResultTransformer(Transformers.aliasToBean(TopRatingUsersDTO.class));
        criteria.addOrder(Order.desc("countOfTouch"));
        criteria.setMaxResults(10);
        telegramRatingStatisticEntityList = criteria.list();
        session.close();
        return telegramRatingStatisticEntityList;
    }
}
