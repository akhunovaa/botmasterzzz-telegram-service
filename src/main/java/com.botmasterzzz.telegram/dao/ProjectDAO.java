package com.botmasterzzz.telegram.dao;

import com.botmasterzzz.telegram.entity.UserProjectCommandEntity;
import com.botmasterzzz.telegram.entity.UserProjectEntity;

import java.util.List;

public interface ProjectDAO {

    long projectAdd(UserProjectEntity userProjectEntity);

    UserProjectEntity userProjectGet(long id);

    UserProjectEntity userProjectGet(String name, Long userId);

    UserProjectEntity userProjectGet(Long id, Long userId);

    List<UserProjectEntity> getUserProjectList(Long userId);

    UserProjectEntity userProjectUpdate(UserProjectEntity userProjectEntity);

    void  projectDelete(UserProjectEntity userProjectEntity);

    Long projectCommandAdd(UserProjectCommandEntity userProjectCommandEntity);

    UserProjectCommandEntity projectCommandGet(Long commandId, Long userId, Long projectId);

    UserProjectCommandEntity projectCommandGet(String command, Long userId, Long projectId);

    List<UserProjectCommandEntity> getUserProjectCommandList(long userId, long projectId);

    List<UserProjectCommandEntity> getUserProjectCommandList(long userId, long projectId, long[] ids);

    void projectCommandDelete(UserProjectCommandEntity projectCommandEntity);

    UserProjectCommandEntity userProjectCommandUpdate(UserProjectCommandEntity projectCommandEntity);
}
