package com.botmasterzzz.telegram.dao.impl;

import com.botmasterzzz.telegram.dao.ReklamDAO;
import com.botmasterzzz.telegram.entity.AccountEntity;
import com.botmasterzzz.telegram.entity.GetPartsEntity;
import com.botmasterzzz.telegram.entity.LotsEntity;
import org.hibernate.*;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReklamDAOImpl implements ReklamDAO {

    @Autowired
    private SessionFactory sessionFactory;

    private static final Logger logger = LoggerFactory.getLogger(ReklamDAOImpl.class);

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

    @SuppressWarnings({"deprecation", "unchecked"})
    @Override
    public List<LotsEntity> getLotsForCustomer(int offset, int limit) {
        List<LotsEntity> lotsList;
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(LotsEntity.class);
//        criteria.add(Restrictions.conjunction()
//                .add(Restrictions.eq("creator", creator))
        criteria.add(Restrictions.eq("archive", false));
        criteria.add(Restrictions.eq("iscreate", true));
        criteria.add(Restrictions.eq("payed", true));
        criteria.add(Restrictions.eq("isdel", false));
        criteria.add(Restrictions.eq("validate", true));
        criteria.add(Restrictions.eq("islock", false));

        criteria.addOrder(Order.desc("id"));
        criteria.setFirstResult(offset);
        criteria.setMaxResults(1);
        lotsList = criteria.list();
        session.close();
        return lotsList;
    }

    @Override
    public List<LotsEntity> getLotsListAccept(int offset, int limit, long customer) {
        List<LotsEntity> lotsList;
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(LotsEntity.class);
        criteria.add(Restrictions.disjunction()
                .add(Restrictions.eq("customer", customer))
                .add(Restrictions.eq("archive", false))
                .add(Restrictions.eq("iscreate", true))
                .add(Restrictions.eq("isdel", false)));
        criteria.addOrder(Order.desc("id"));
        criteria.setFirstResult(offset);
        criteria.setMaxResults(1);
        lotsList = criteria.list();
        session.close();
        return lotsList;
    }

    @Override
    public List<LotsEntity> activeLotsForCreatorEntityList(long creator, boolean isArchive) {
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
        LotsEntity lot = null;
        Session session = sessionFactory.openSession();
        logger.debug("Lot with id {} GET from a repository", id);
        lot = session.get(LotsEntity.class, id);
        session.close();
        return lot;
    }

    @Override
    public boolean validateLot(long id) {
        return getLot(id).isValidate();
    }

    @Override
    public void setValidateLot(long id, boolean status) {
        getLot(id).setValidate(status);
    }

    @Override
    public boolean checkPay(long id) {
        return getLot(id).isPayed();
    }

    @Override
    public void setPayLot(long id, boolean status) {
        getLot(id).setPayed(status);
    }

    @Override
    public boolean isCreateLot(long id) {
        return getLot(id).isIscreate();
    }

    @Override
    public void setCreateLot(long id, boolean status) {
        getLot(id).setIscreate(status);
    }

    @Override
    public void setCustomerLot(long id, long userid) {
        getLot(id).setCustomer(userid);
    }

    @Override
    public boolean isArchiveLot(long id) {
        return getLot(id).isArchive();
    }

    @Override
    public void setArchiveLot(long id, boolean status) {
        getLot(id).setArchive(status);
    }

    @Override
    public boolean isDelLot(long id) {
        return getLot(id).isIsdel();
    }

    @Override
    public void setDelLot(long id, boolean status) {
        getLot(id).setIsdel(status);
    }

    @Override
    public boolean isLockLot(long id) {
        return getLot(id).isIslock();
    }

    @Override
    public void setLockLot(long id, boolean status) {
        LotsEntity lot = getLot(id);
        lot.setIslock(status);

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(lot);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<AccountEntity> accountsList() {
        return null;
    }

    @Override
    public AccountEntity getAccount(long id) {
        AccountEntity account;

        Session session = sessionFactory.openSession();
        logger.debug("Lot with id {} GET from a repository", id);
        account = session.get(AccountEntity.class, id);
        session.close();
        return account;
    }

    @SuppressWarnings({"deprecation", "unchecked"})
    @Override
    public AccountEntity getAccountByUserId(long id) {
        AccountEntity account = null;

        List<AccountEntity>  accList = null;
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(AccountEntity.class);
        criteria.add(Restrictions.eq("userid", id));
        try{
            accList = criteria.list();
        }catch (QueryException e){
            session.close();
        }finally {
            session.close();
        }
        account = accList.get(0);
        return account;
    }

    @Override
    public AccountEntity getAccountid(int userid) {
        return null;
    }

    @Override
    public double getAccountTotal(long id) {
        return getAccount(id).getTotal();
    }

    @Override
    public double getAccountIncome(long id) {
        return 0;
    }

    @Override
    public void setAccountIncome(long id, double count) {

    }

    @Override
    public double getAccountOutcome(long id) {
        return 0;
    }

    @Override
    public void setAccountOutcome(long id, double count) {

    }

    @Override
    public boolean getAccountIslock(long id) {
        return false;
    }

    @Override
    public void setAccountIslock(long id, boolean status) {

    }

    @Override
    public boolean getAccountIsactive(long id) {
        return false;
    }

    @Override
    public void setAccountIsactive(long id, boolean status) {

    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean exists(Class<?> clazz, Long idValue) {
        Session session = sessionFactory.openSession();
        boolean exists = false;
        try {
            exists =  session.createCriteria(clazz)
                    .add(Restrictions.eq("accountid", idValue))
                    .setProjection(Projections.id())
                    .uniqueResult() != null;
            session.close();
        } catch (Exception he) {
            session.close();
        } finally {
            session.close();
        }
        return exists;
    }

    @Override
    public void accountAdd(AccountEntity accountEntity) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(accountEntity);
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
