package com.botmasterzzz.telegram.dao.impl;

import com.botmasterzzz.telegram.dao.TelegramUserMediaDAO;
import com.botmasterzzz.telegram.dto.OwnerStatisticDTO;
import com.botmasterzzz.telegram.dto.TopRatingUsersDTO;
import com.botmasterzzz.telegram.entity.TelegramMediaStatisticEntity;
import com.botmasterzzz.telegram.entity.TelegramRatingStatisticEntity;
import com.botmasterzzz.telegram.entity.TelegramUserMediaEntity;
import com.botmasterzzz.telegram.entity.stat.TelegramUserMediaStatisticEntity;
import org.hibernate.*;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
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
    public TelegramUserMediaEntity telegramUserMediaGet(String filePath) {
        TelegramUserMediaEntity telegramUserMediaEntity = null;
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(TelegramUserMediaEntity.class);
        criteria.add(Restrictions.eq("filePath", filePath));
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
        criteria.addOrder(Order.asc("audWhenCreate"));
        telegramUserMediaEntityList = criteria.list();
        session.close();
        return telegramUserMediaEntityList;
    }

    @SuppressWarnings({"deprecation", "unchecked"})
    @Override
    public List<TelegramUserMediaEntity> telegramUserMediaListForToday() {
        Date minDate = Date.from(Instant.now().truncatedTo(ChronoUnit.DAYS));
        Date maxDate = new Date();
        List<TelegramUserMediaEntity> telegramUserMediaEntityList;
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(TelegramUserMediaEntity.class);
        criteria.add(Restrictions.eq("isDeleted", false));
        criteria.addOrder(Order.asc("audWhenCreate"));
        criteria.add(Restrictions.between("audWhenCreate", minDate, maxDate));
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

    @Override
    @SuppressWarnings({"deprecation", "unchecked"})
    public List<TopRatingUsersDTO> topUsersGet() {
        List<TopRatingUsersDTO> telegramRatingStatisticEntityList;
        Session session = sessionFactory.openSession();

        Criteria criteria = session.createCriteria(TelegramUserMediaStatisticEntity.class);
        criteria.createAlias("telegramBotUserEntity", "user");
        criteria.createAlias("telegramMedia4TopStatisticEntity", "statistic");

        ProjectionList projList = Projections.projectionList();
        projList.add(Projections.count("statistic.touchType").as("countOfTouch"));
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

    @SuppressWarnings({"deprecation", "unchecked"})
    @Override
    public List<OwnerStatisticDTO> getUsersActivityStatistic(Long telegramUserId) {
        List<OwnerStatisticDTO> ownerStatisticDTOList;
        Session session = sessionFactory.openSession();

        Criteria criteria = session.createCriteria(TelegramRatingStatisticEntity.class);
        criteria.createAlias("telegramBotUserEntity", "user");
        criteria.add(Restrictions.eq("user.telegramId", telegramUserId));

        ProjectionList projList = Projections.projectionList();
        projList.add(Projections.count("touchType").as("countOfTouch"));
        projList.add(Projections.groupProperty("touchType").as("touchType"));

        criteria.setProjection(projList);
        criteria.setResultTransformer(Transformers.aliasToBean(OwnerStatisticDTO.class));
        criteria.addOrder(Order.desc("countOfTouch"));
        criteria.setMaxResults(3);
        ownerStatisticDTOList = criteria.list();
        session.close();
        return ownerStatisticDTOList;
    }

    @SuppressWarnings({"deprecation", "unchecked"})
    @Override
    public List<OwnerStatisticDTO> getSelfActivityStatistic(Long telegramUserId) {
        List<OwnerStatisticDTO> ownerStatisticDTOList;
        Session session = sessionFactory.openSession();

        Criteria criteria = session.createCriteria(TelegramUserMediaStatisticEntity.class);
        criteria.createAlias("telegramBotUserEntity", "user");
        criteria.createAlias("telegramMedia4TopStatisticEntity", "statistic");
        criteria.add(Restrictions.eq("user.telegramId", telegramUserId));

        ProjectionList projList = Projections.projectionList();
        projList.add(Projections.count("statistic.touchType").as("countOfTouch"));
        projList.add(Projections.groupProperty("statistic.touchType").as("touchType"));

        criteria.setProjection(projList);
        criteria.setResultTransformer(Transformers.aliasToBean(OwnerStatisticDTO.class));
        criteria.addOrder(Order.desc("countOfTouch"));
        criteria.setMaxResults(3);
        ownerStatisticDTOList = criteria.list();
        session.close();
        return ownerStatisticDTOList;
    }
}
