package com.botmasterzzz.telegram.dao.impl;

import com.botmasterzzz.telegram.dao.LotsDAO;
import com.botmasterzzz.telegram.entity.GetPartsEntity;
import com.botmasterzzz.telegram.entity.LotsEntity;
import org.hibernate.Criteria;
import org.hibernate.QueryException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LotsDAOImpl implements LotsDAO {

    @Autowired
    private SessionFactory sessionFactory;

    private static final Logger logger = LoggerFactory.getLogger(LotsDAOImpl.class);

    @Override
    @SuppressWarnings({"deprecation", "unchecked"})
    public List<LotsEntity> lotsCustomerEntityList(String customer) {
        List<LotsEntity>  lotsCustomerEntityList = null;
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(GetPartsEntity.class);
        criteria.add(Restrictions.disjunction()
                .add(Restrictions.eq("customer", customer))
                .add(Restrictions.eq("archive", false))
                .add(Restrictions.eq("isdel", false)));
        try{
            lotsCustomerEntityList = criteria.list();
        }catch (QueryException e){
            session.close();
        }finally {
            session.close();
        }
        return lotsCustomerEntityList;
    }

    @Override
    public List<LotsEntity> activeLotsForCreatorEntityList(String creator, boolean isArchive) {
        List<LotsEntity>  lotsCustomerEntityList = null;
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(GetPartsEntity.class);
        criteria.add(Restrictions.disjunction()
                .add(Restrictions.eq("creator", creator))
                .add(Restrictions.eq("archive", false))
                .add(Restrictions.eq("iscreate", true))
                .add(Restrictions.eq("isdel", false)));
        try{
            lotsCustomerEntityList = criteria.list();
        }catch (QueryException e){
            session.close();
        }finally {
            session.close();
        }
        return lotsCustomerEntityList;
    }

    @Override
    public List<LotsEntity> availableLotsEntityList() {
        List<LotsEntity>  lotsCustomerEntityList = null;
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(GetPartsEntity.class);
        criteria.add(Restrictions.disjunction()
                .add(Restrictions.eq("archive", false))
                .add(Restrictions.eq("payed", true))
                .add(Restrictions.eq("isdel", false)));
        try{
            lotsCustomerEntityList = criteria.list();
        }catch (QueryException e){
            session.close();
        }finally {
            session.close();
        }
        return lotsCustomerEntityList;
    }

    @Override
    public LotsEntity loadLots(long id) {
        LotsEntity lotsEntity;
        Session session = sessionFactory.openSession();
        logger.debug("Lot with id {} GET from a repository", id);
        lotsEntity = session.get(LotsEntity.class, id);
        session.close();
        return lotsEntity;
    }

    @Override
    public LotsEntity addLot() {
        return null;
    }

    @Override
    public LotsEntity getLot(long id) {
        return null;
    }

    @Override
    public boolean validateLot(long id) {
        return false;
    }

    @Override
    public void setValidateLot(long id) {

    }

    @Override
    public boolean checkPay(long id) {
        return false;
    }

    @Override
    public void setPayLot(long id) {

    }

    @Override
    public boolean isCreateLot(long id) {
        return false;
    }

    @Override
    public void setCreateLot(long id) {

    }

    @Override
    public void setCustomerLot(long id) {

    }

    @Override
    public boolean isArchiveLot(long id) {
        return false;
    }

    @Override
    public void setArchiveLot(long id) {

    }

    @Override
    public boolean isDelLot(long id) {
        return false;
    }

    @Override
    public void setDelLot(long id) {

    }

    @Override
    public boolean isLockLot(long id) {
        return false;
    }

    @Override
    public void setLockLot(long id) {

    }
}
