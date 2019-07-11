package com.botmasterzzz.telegram.dao.impl;

import com.botmasterzzz.telegram.dao.ProjectDAO;
import com.botmasterzzz.telegram.entity.UserProjectCommandEntity;
import com.botmasterzzz.telegram.entity.UserProjectEntity;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ProjectDAOImpl implements ProjectDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public long projectAdd(UserProjectEntity userProjectEntity) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(userProjectEntity);
        session.getTransaction().commit();
        session.close();
        return userProjectEntity.getId();
    }

    @Override
    @SuppressWarnings({"deprecation", "unchecked"})
    public List<UserProjectEntity> getUserProjectList(Long userId) {
        List<UserProjectEntity> userProjectEntityList;
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(UserProjectEntity.class);
        criteria.add(Restrictions.eq("userEntity.id", userId));
        userProjectEntityList = criteria.list();
        session.close();
        return userProjectEntityList;
    }

    @Override
    public UserProjectEntity userProjectUpdate(UserProjectEntity userProjectEntity) {
        Session session = sessionFactory.openSession();
        Transaction updateTransaction = session.beginTransaction();
        session.update(userProjectEntity);
        updateTransaction.commit();
        session.close();
        return userProjectEntity;
    }

    @Override
    public void projectDelete(UserProjectEntity userProjectEntity) {
        Session session = sessionFactory.openSession();
        Transaction updateTransaction = session.beginTransaction();
        session.delete(userProjectEntity);
        updateTransaction.commit();
        session.close();
    }

    @Override
    public Long projectCommandAdd(UserProjectCommandEntity userProjectCommandEntity) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(userProjectCommandEntity);
        session.getTransaction().commit();
        session.close();
        return userProjectCommandEntity.getId();
    }

    @Override
    @SuppressWarnings({"deprecation"})
    public UserProjectCommandEntity projectCommandGet(String command, Long userId, Long projectId) {
        UserProjectCommandEntity userProjectCommandEntity = null;
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(UserProjectCommandEntity.class);
        criteria.add(Restrictions.eq("command", command));
        criteria.add(Restrictions.eq("user.id", userId));
        criteria.add(Restrictions.eq("project.id", projectId));
        try{
            userProjectCommandEntity = (UserProjectCommandEntity) criteria.uniqueResult();
        }catch (QueryException e){
            session.close();
        }finally {
            session.close();
        }
        return userProjectCommandEntity;
    }

    @Override
    @SuppressWarnings({"deprecation"})
    public UserProjectCommandEntity projectCommandGet(Long commandId, Long userId, Long projectId) {
        UserProjectCommandEntity userProjectCommandEntity = null;
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(UserProjectCommandEntity.class);
        criteria.add(Restrictions.eq("id", commandId));
        criteria.add(Restrictions.eq("user.id", userId));
        criteria.add(Restrictions.eq("project.id", projectId));
        try{
            userProjectCommandEntity = (UserProjectCommandEntity) criteria.uniqueResult();
        }catch (QueryException e){
            session.close();
        }finally {
            session.close();
        }
        return userProjectCommandEntity;
    }

    @Override
    @SuppressWarnings({"deprecation", "unchecked"})
    public List<UserProjectCommandEntity> getUserProjectCommandList(long userId, long projectId) {
        List<UserProjectCommandEntity> userProjectCommandEntityList;
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(UserProjectCommandEntity.class);
        criteria.add(Restrictions.eq("user.id", userId));
        criteria.add(Restrictions.eq("project.id", projectId));
        userProjectCommandEntityList = criteria.list();
        session.close();
        return userProjectCommandEntityList;
    }

    @Override
    @SuppressWarnings({"deprecation", "unchecked"})
    public List<UserProjectCommandEntity> getUserProjectCommandList(long userId, long projectId, long[] ids) {
        List<UserProjectCommandEntity> userProjectCommandEntityList;
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(UserProjectCommandEntity.class);
        criteria.add(Restrictions.eq("user.id", userId));
        criteria.add(Restrictions.eq("project.id", projectId));
        criteria.add(Restrictions.in("id", Arrays.stream(ids).boxed().collect(Collectors.toList())));
        userProjectCommandEntityList = criteria.list();
        session.close();
        return userProjectCommandEntityList;
    }

    @Override
    public void projectCommandDelete(UserProjectCommandEntity projectCommandEntity) {
        Session session = sessionFactory.openSession();
        Transaction updateTransaction = session.beginTransaction();
        session.delete(projectCommandEntity);
        updateTransaction.commit();
        session.close();
    }

    @Override
    public UserProjectCommandEntity userProjectCommandUpdate(UserProjectCommandEntity projectCommandEntity) {
        Session session = sessionFactory.openSession();
        Transaction updateTransaction = session.beginTransaction();
        session.update(projectCommandEntity);
        updateTransaction.commit();
        session.close();
        return projectCommandEntity;
    }

    @Override
    public UserProjectEntity userProjectGet(long id) {
        UserProjectEntity userProjectEntity;
        Session session = sessionFactory.openSession();
        userProjectEntity = session.get(UserProjectEntity.class, id);
        session.close();
        return userProjectEntity;
    }

    @Override
    @SuppressWarnings({"deprecation"})
    public UserProjectEntity userProjectGet(String name, Long userId) {
        UserProjectEntity userProjectEntity;
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(UserProjectEntity.class);
        criteria.add(Restrictions.eq("name", name));
        criteria.add(Restrictions.eq("userEntity.id", userId));
        userProjectEntity = (UserProjectEntity) criteria.uniqueResult();
        session.close();
        return userProjectEntity;
    }

    @Override
    @SuppressWarnings({"deprecation"})
    public UserProjectEntity userProjectGet(Long id, Long userId) {
        UserProjectEntity userProjectEntity;
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(UserProjectEntity.class);
        criteria.add(Restrictions.eq("id", id));
        criteria.add(Restrictions.eq("userEntity.id", userId));
        userProjectEntity = (UserProjectEntity) criteria.uniqueResult();
        session.close();
        return userProjectEntity;
    }
}
