package com.botmasterzzz.telegram.service.impl;

import com.botmasterzzz.telegram.dao.ProjectDAO;
import com.botmasterzzz.telegram.dao.UserDAO;
import com.botmasterzzz.telegram.dto.ProjectDTO;
import com.botmasterzzz.telegram.entity.UserEntity;
import com.botmasterzzz.telegram.entity.UserProjectEntity;
import com.botmasterzzz.telegram.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectDAO projectDAO;

    @Autowired
    private UserDAO userDAO;

    @Override
    public long projectCreate(ProjectDTO projectDTO) {
        UserEntity userEntity = userDAO.loadUser(projectDTO.getUserId());
        UserProjectEntity userProjectEntity = new UserProjectEntity();
        userProjectEntity.setName(projectDTO.getName());
        userProjectEntity.setDescription(projectDTO.getDescription());
        userProjectEntity.setNote(projectDTO.getNote());
        userProjectEntity.setImageUrl(projectDTO.getImageUrl());
        userProjectEntity.setUserEntity(userEntity);
        return projectDAO.projectAdd(userProjectEntity);
    }

    @Override
    public void projectUpdate(ProjectDTO projectDTO) {
        UserProjectEntity userProjectEntity = projectDAO.userProjectGet(projectDTO.getId(), projectDTO.getUserId());
        userProjectEntity.setName(projectDTO.getName());
        userProjectEntity.setDescription(projectDTO.getDescription());
        userProjectEntity.setSecret(projectDTO.getSecret());
        userProjectEntity.setIsPrivate(projectDTO.isIsPrivate());
        projectDAO.userProjectUpdate(userProjectEntity);
    }

    @Override
    public void projectImageUrlUpdate(ProjectDTO projectDTO) {
        UserProjectEntity userProjectEntity = projectDAO.userProjectGet(projectDTO.getId(), projectDTO.getUserId());
        userProjectEntity.setImageUrl(projectDTO.getImageUrl());
        projectDAO.userProjectUpdate(userProjectEntity);
    }

    @Override
    public ProjectDTO projectTokenUpdate(ProjectDTO projectDTO) {
        UserProjectEntity userProjectEntity = projectDAO.userProjectGet(projectDTO.getId(), projectDTO.getUserId());
        userProjectEntity.setToken(projectDTO.getToken());
        projectDAO.userProjectUpdate(userProjectEntity);
        projectDTO.setName(userProjectEntity.getName());
        projectDTO.setDescription(userProjectEntity.getDescription());
        projectDTO.setNote(userProjectEntity.getNote());
        projectDTO.setPrivate(userProjectEntity.getIsPrivate());
        projectDTO.setSecret(userProjectEntity.getSecret());
        projectDTO.setImageUrl(userProjectEntity.getImageUrl());
        return projectDTO;
    }

    @Override
    public ProjectDTO projectDelete(ProjectDTO projectDTO) {
        UserProjectEntity userProjectEntity = projectDAO.userProjectGet(projectDTO.getId(), projectDTO.getUserId());
        projectDTO.setName(userProjectEntity.getName());
        projectDTO.setDescription(userProjectEntity.getName());
        projectDTO.setImageUrl(userProjectEntity.getName());
        projectDTO.setNote(userProjectEntity.getName());
        projectDAO.projectDelete(userProjectEntity);
        return projectDTO;
    }

    @Override
    public ProjectDTO getProject(String name, long userId) {
        UserProjectEntity userProjectEntity = projectDAO.userProjectGet(name, userId);
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setName(userProjectEntity.getName());
        projectDTO.setDescription(userProjectEntity.getDescription());
        projectDTO.setUserId(userProjectEntity.getUserEntity().getId());
        projectDTO.setImageUrl(userProjectEntity.getImageUrl());
        projectDTO.setNote(userProjectEntity.getNote());
        projectDTO.setId(userProjectEntity.getId());
        projectDTO.setPrivate(userProjectEntity.getIsPrivate());
        projectDTO.setSecret(userProjectEntity.getSecret());
        projectDTO.setToken(userProjectEntity.getToken());
        return projectDTO;
    }

    @Override
    public ProjectDTO getProject(Long id, long userId) {
        UserProjectEntity userProjectEntity = projectDAO.userProjectGet(id, userId);
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setName(userProjectEntity.getName());
        projectDTO.setDescription(userProjectEntity.getDescription());
        projectDTO.setUserId(userProjectEntity.getUserEntity().getId());
        projectDTO.setImageUrl(userProjectEntity.getImageUrl());
        projectDTO.setNote(userProjectEntity.getNote());
        projectDTO.setId(userProjectEntity.getId());
        projectDTO.setPrivate(userProjectEntity.getIsPrivate());
        projectDTO.setSecret(userProjectEntity.getSecret());
        projectDTO.setToken(userProjectEntity.getToken());
        return projectDTO;
    }

    @Override
    public List<ProjectDTO> getUserProjectList(long userId) {
        List<ProjectDTO> projectDTOList = new ArrayList<>();
        List<UserProjectEntity> userProjectEntityList;
        userProjectEntityList = projectDAO.getUserProjectList(userId);
        for (UserProjectEntity userProjectEntity : userProjectEntityList) {
            ProjectDTO projectDTO = new ProjectDTO();
            projectDTO.setName(userProjectEntity.getName());
            projectDTO.setDescription(userProjectEntity.getDescription());
            projectDTO.setUserId(userProjectEntity.getUserEntity().getId());
            projectDTO.setImageUrl(userProjectEntity.getImageUrl());
            projectDTO.setNote(userProjectEntity.getNote());
            projectDTO.setId(userProjectEntity.getId());
            projectDTO.setPrivate(userProjectEntity.getIsPrivate());
            projectDTO.setSecret(userProjectEntity.getSecret());
            projectDTO.setToken(userProjectEntity.getToken());
            projectDTOList.add(projectDTO);
        }
        return projectDTOList;
    }
}
