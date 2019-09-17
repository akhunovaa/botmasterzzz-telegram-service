package com.botmasterzzz.telegram.dao.impl;
import com.botmasterzzz.telegram.dao.GetPartsDAO;
import com.botmasterzzz.telegram.entity.GetPartsEntity;
import org.hibernate.*;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GetPartsDAOImpl implements GetPartsDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public long getPartsAdd(GetPartsEntity getPartsEntity) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(getPartsEntity);
        session.getTransaction().commit();
        session.close();
        return getPartsEntity.getId();
    }

    @Override
    @SuppressWarnings({"deprecation", "unchecked"})
    public List<GetPartsEntity> getGetPartsEntityList() {
        List<GetPartsEntity> getPartsEntityList;
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(GetPartsEntity.class);
        getPartsEntityList = criteria.list();
        session.close();
        return getPartsEntityList;
    }

    @Override
    public GetPartsEntity getPartsEntityUpdate(GetPartsEntity getPartsEntity) {
        Session session = sessionFactory.openSession();
        Transaction updateTransaction = session.beginTransaction();
        session.update(getPartsEntity);
        updateTransaction.commit();
        session.close();
        return getPartsEntity;
    }

    @Override
    public void projectDelete(GetPartsEntity getPartsEntity) {
        Session session = sessionFactory.openSession();
        Transaction updateTransaction = session.beginTransaction();
        session.delete(getPartsEntity);
        updateTransaction.commit();
        session.close();
    }

    @Override
    @SuppressWarnings("deprecation")
    public GetPartsEntity getPartsEntityGet(String name) {
        GetPartsEntity getPartsEntity = null;
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(GetPartsEntity.class);
        criteria.add(Restrictions.eq("name", name));
        try{
            getPartsEntity = (GetPartsEntity) criteria.uniqueResult();
        }catch (QueryException e){
            session.close();
        }finally {
            session.close();
        }
        return getPartsEntity;
    }

    @Override
    @SuppressWarnings({"deprecation", "unchecked"})
    public List<GetPartsEntity> getPartsSearchEntityGet(String text) {
        List<GetPartsEntity>  getPartsEntityList = null;
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(GetPartsEntity.class);
        criteria.add(Restrictions.disjunction()
                .add(Restrictions.like("name", text, MatchMode.ANYWHERE))
                .add(Restrictions.ilike("article", text, MatchMode.ANYWHERE))
                .add(Restrictions.ilike("brandName", text, MatchMode.ANYWHERE)));
        try{
            getPartsEntityList = criteria.list();
        }catch (QueryException e){
            session.close();
        }finally {
            session.close();
        }
        return getPartsEntityList;
    }

    @Override
    public GetPartsEntity getPartsEntityGet(Long id) {
        Session session = sessionFactory.openSession();
        Transaction updateTransaction = session.beginTransaction();
        GetPartsEntity getPartsEntity = session.get(GetPartsEntity.class, id);
        updateTransaction.commit();
        session.close();
        return getPartsEntity;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean getPartsEntityExists(String name) {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(GetPartsEntity.class);
        criteria.add(Restrictions.eq("name", name));
        criteria.setProjection(Projections.id());
        boolean exists = criteria.uniqueResult() != null;
        session.close();
        return exists;
    }
}
