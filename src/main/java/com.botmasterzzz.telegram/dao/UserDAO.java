package com.botmasterzzz.telegram.dao;

import com.botmasterzzz.telegram.entity.UserEntity;

public interface UserDAO {

    UserEntity loadUser(long id);

}
