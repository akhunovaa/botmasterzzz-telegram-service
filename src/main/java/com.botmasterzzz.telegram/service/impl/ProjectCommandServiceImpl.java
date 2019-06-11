package com.botmasterzzz.telegram.service.impl;

import com.botmasterzzz.telegram.dao.ProjectDAO;
import com.botmasterzzz.telegram.dao.UserDAO;
import com.botmasterzzz.telegram.dto.ProjectCommandDTO;
import com.botmasterzzz.telegram.entity.UserEntity;
import com.botmasterzzz.telegram.entity.UserProjectCommandEntity;
import com.botmasterzzz.telegram.entity.UserProjectEntity;
import com.botmasterzzz.telegram.service.ProjectCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectCommandServiceImpl implements ProjectCommandService {

    @Autowired
    private ProjectDAO projectDAO;

    @Autowired
    private UserDAO userDAO;

    @Override
    public Long projectCommandAdd(ProjectCommandDTO projectCommandDTO) {
        UserEntity userEntity = userDAO.loadUser(projectCommandDTO.getUserId());
        UserProjectEntity userProjectEntity = projectDAO.userProjectGet(projectCommandDTO.getProjectId(), projectCommandDTO.getUserId());
        UserProjectCommandEntity userProjectCommandEntity = new UserProjectCommandEntity();
        userProjectCommandEntity.setAnswer(projectCommandDTO.getAnswer());
        userProjectCommandEntity.setCommand(projectCommandDTO.getCommand());
        userProjectCommandEntity.setCommandName(projectCommandDTO.getCommandName());
        userProjectCommandEntity.setMessengerId(projectCommandDTO.getMessengerId());
        userProjectCommandEntity.setNote(projectCommandDTO.getNote());
        userProjectCommandEntity.setPrivacy(projectCommandDTO.getPrivacy());
        userProjectCommandEntity.setProject(userProjectEntity);
        userProjectCommandEntity.setUser(userEntity);
        return projectDAO.projectCommandAdd(userProjectCommandEntity);
    }

    @Override
    public ProjectCommandDTO projectCommandGet(ProjectCommandDTO projectCommandDTO) {
        UserProjectCommandEntity userProjectCommandEntity = projectDAO.projectCommandGet(projectCommandDTO.getCommand(), projectCommandDTO.getUserId(), projectCommandDTO.getProjectId());
        if (null != userProjectCommandEntity){
            projectCommandDTO.setId(userProjectCommandEntity.getId());
            projectCommandDTO.setAnswer(userProjectCommandEntity.getAnswer());
            projectCommandDTO.setAudWhenCreate(userProjectCommandEntity.getAudWhenCreate());
            projectCommandDTO.setAudWhenUpdate(userProjectCommandEntity.getAudWhenUpdate());
            projectCommandDTO.setCommand(userProjectCommandEntity.getCommand());
            projectCommandDTO.setCommandName(userProjectCommandEntity.getCommandName());
            projectCommandDTO.setMessengerId(userProjectCommandEntity.getMessengerId());
            projectCommandDTO.setNote(userProjectCommandEntity.getNote());
            projectCommandDTO.setPrivacy(userProjectCommandEntity.isPrivacy());
            projectCommandDTO.setProjectId(userProjectCommandEntity.getProject().getId());
            projectCommandDTO.setUserId(userProjectCommandEntity.getUser().getId());
        }
        return projectCommandDTO;
    }

    @Override
    public List<ProjectCommandDTO> getProjectCommandGetList(long userId, long projectId) {
        List<ProjectCommandDTO> projectCommandDTOList = new ArrayList<>();
        List<UserProjectCommandEntity> userProjectCommandEntityList;
        userProjectCommandEntityList = projectDAO.getUserProjectCommandList(userId, projectId);
        for (UserProjectCommandEntity userProjectCommandEntity : userProjectCommandEntityList) {
            ProjectCommandDTO projectCommandDTO = new ProjectCommandDTO();
            projectCommandDTO.setId(userProjectCommandEntity.getId());
            projectCommandDTO.setAnswer(userProjectCommandEntity.getAnswer());
            projectCommandDTO.setAudWhenCreate(userProjectCommandEntity.getAudWhenCreate());
            projectCommandDTO.setAudWhenUpdate(userProjectCommandEntity.getAudWhenUpdate());
            projectCommandDTO.setCommand(userProjectCommandEntity.getCommand());
            projectCommandDTO.setCommandName(userProjectCommandEntity.getCommandName());
            projectCommandDTO.setMessengerId(userProjectCommandEntity.getMessengerId());
            projectCommandDTO.setNote(userProjectCommandEntity.getNote());
            projectCommandDTO.setPrivacy(userProjectCommandEntity.isPrivacy());
            projectCommandDTO.setProjectId(userProjectCommandEntity.getProject().getId());
            projectCommandDTO.setUserId(userProjectCommandEntity.getUser().getId());
            projectCommandDTOList.add(projectCommandDTO);
        }
        return projectCommandDTOList;
    }

    @Override
    public ProjectCommandDTO projectCommandDelete(ProjectCommandDTO projectCommandDTO) {
        UserProjectCommandEntity userProjectCommandEntity = projectDAO.projectCommandGet(projectCommandDTO.getId(), projectCommandDTO.getUserId(), projectCommandDTO.getProjectId());
        if (null != userProjectCommandEntity){
            projectCommandDTO.setId(userProjectCommandEntity.getId());
            projectCommandDTO.setAnswer(userProjectCommandEntity.getAnswer());
            projectCommandDTO.setCommand(userProjectCommandEntity.getCommand());
            projectCommandDTO.setCommandName(userProjectCommandEntity.getCommandName());
            projectCommandDTO.setMessengerId(userProjectCommandEntity.getMessengerId());
            projectCommandDTO.setNote(userProjectCommandEntity.getNote());
            projectCommandDTO.setPrivacy(userProjectCommandEntity.isPrivacy());
            projectCommandDTO.setProjectId(userProjectCommandEntity.getProject().getId());
            projectCommandDTO.setUserId(userProjectCommandEntity.getUser().getId());
            projectDAO.projectCommandDelete(userProjectCommandEntity);
        }
        return projectCommandDTO;
    }

    @Override
    public ProjectCommandDTO projectCommandUpdate(ProjectCommandDTO projectCommandDTO) {
        UserProjectCommandEntity userProjectCommandEntity = projectDAO.projectCommandGet(projectCommandDTO.getId(), projectCommandDTO.getUserId(), projectCommandDTO.getProjectId());
        if (null != userProjectCommandEntity){
            userProjectCommandEntity.setPrivacy(projectCommandDTO.getPrivacy());
            userProjectCommandEntity.setCommand(projectCommandDTO.getCommand());
            userProjectCommandEntity.setCommandName(projectCommandDTO.getCommandName());
            userProjectCommandEntity.setAnswer(projectCommandDTO.getAnswer());
            projectDAO.userProjectCommandUpdate(userProjectCommandEntity);
        }else{
            projectCommandDTO.setNotFound(true);
        }
        return projectCommandDTO;
    }
}
