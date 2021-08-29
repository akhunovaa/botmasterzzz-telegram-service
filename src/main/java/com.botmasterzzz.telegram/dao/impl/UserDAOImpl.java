package com.botmasterzzz.telegram.dao.impl;

import com.botmasterzzz.telegram.dao.UserDAO;
import com.botmasterzzz.telegram.entity.UserEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAOImpl implements UserDAO {

    @Autowired
    private SessionFactory sessionFactory;

    private static final Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);

    @Override
    public UserEntity loadUser(long id) {
        UserEntity userEntity;
        Session session = sessionFactory.openSession();
        logger.debug("User with id {} GET from a repository", id);
        userEntity = session.get(UserEntity.class, id);
        session.close();
        return userEntity;
    }
}
