package com.botmasterzzz.telegram.dao.impl;

import com.botmasterzzz.telegram.dao.MediaCommentsDataDAO;
import com.botmasterzzz.telegram.entity.MediaCommentsDataEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MediaCommentsDataDAOImpl implements MediaCommentsDataDAO {

    private final SessionFactory sessionFactory;

    public MediaCommentsDataDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void commentAdd(MediaCommentsDataEntity mediaCommentsDataEntity) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(mediaCommentsDataEntity);
            tx.commit();
        } catch (Exception he) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
    }

    @SuppressWarnings({"deprecation", "unchecked"})
    @Override
    public List<MediaCommentsDataEntity> getCommentsForMedia(Long mediaFileId, int offset, int limit) {
        List<MediaCommentsDataEntity> mediaCommentsDataEntityList;
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(MediaCommentsDataEntity.class);
        criteria.createAlias("telegramUserMediaEntity", "media");
        criteria.add(Restrictions.eq("media.id", mediaFileId));
        criteria.addOrder(Order.desc("audWhenCreate"));
        criteria.setFirstResult(offset);
        criteria.setMaxResults(limit);
        mediaCommentsDataEntityList = criteria.list();
        session.close();
        return mediaCommentsDataEntityList;
    }
}
