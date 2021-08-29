package com.botmasterzzz.telegram.service.impl;

import com.botmasterzzz.telegram.service.ProjectPrivacyService;
import org.springframework.stereotype.Service;

@Service
public class ProjectPrivacyServiceImpl implements ProjectPrivacyService {

//    @Autowired
//    private ProjectDAO projectDAO;
//
//    @Autowired
//    private UserDAO userDAO;
//
//    @Override
//    public long projectCreate(UserProjectEntity userProjectEntity) {
//        UserEntity userEntity = userDAO.loadUser(userProjectEntity.getUserEntity().getId());
////        UserProjectPrivacyEntity userProjectPrivacyEntity = new UserProjectEntity();
////        userProjectEntity.setName(projectDTO.getName());
////        userProjectEntity.setDescription(projectDTO.getDescription());
////        userProjectEntity.setNote(projectDTO.getNote());
////        userProjectPrivacyEntity.setImageUrl(projectDTO.getImageUrl());
////        userProjectPrivacyEntity.setUserEntity(userEntity);
//        return projectDAO.projectAdd(userProjectEntity);
//    }
//
//    @Override
//    public void projectUpdate(ProjectDTO projectDTO) {
//        UserProjectEntity userProjectEntity = projectDAO.userProjectGet(projectDTO.getId(), projectDTO.getUserId());
//        userProjectEntity.setName(projectDTO.getName());
//        userProjectEntity.setDescription(projectDTO.getDescription());
//        userProjectEntity.setSecret(projectDTO.getSecret());
//        userProjectEntity.setIsPrivate(projectDTO.isIsPrivate());
//        projectDAO.userProjectUpdate(userProjectEntity);
//    }
//
//    @Override
//    public void projectImageUrlUpdate(ProjectDTO projectDTO) {
//        UserProjectEntity userProjectEntity = projectDAO.userProjectGet(projectDTO.getId(), projectDTO.getUserId());
//        userProjectEntity.setImageUrl(projectDTO.getImageUrl());
//        projectDAO.userProjectUpdate(userProjectEntity);
//    }
//
//    @Override
//    public ProjectDTO projectTokenUpdate(ProjectDTO projectDTO) {
//        UserProjectEntity userProjectEntity = projectDAO.userProjectGet(projectDTO.getId(), projectDTO.getUserId());
//        userProjectEntity.setToken(projectDTO.getToken());
//        projectDAO.userProjectUpdate(userProjectEntity);
//        projectDTO.setName(userProjectEntity.getName());
//        projectDTO.setDescription(userProjectEntity.getDescription());
//        projectDTO.setNote(userProjectEntity.getNote());
//        projectDTO.setPrivate(userProjectEntity.getIsPrivate());
//        projectDTO.setSecret(userProjectEntity.getSecret());
//        projectDTO.setImageUrl(userProjectEntity.getImageUrl());
//        return projectDTO;
//    }
//
//    @Override
//    public ProjectDTO projectDelete(ProjectDTO projectDTO) {
//        UserProjectEntity userProjectEntity = projectDAO.userProjectGet(projectDTO.getId(), projectDTO.getUserId());
//        projectDTO.setName(userProjectEntity.getName());
//        projectDTO.setDescription(userProjectEntity.getName());
//        projectDTO.setImageUrl(userProjectEntity.getName());
//        projectDTO.setNote(userProjectEntity.getName());
//        projectDAO.projectDelete(userProjectEntity);
//        return projectDTO;
//    }
//
//    @Override
//    public ProjectDTO getProject(String name, long userId) {
//        UserProjectEntity userProjectEntity = projectDAO.userProjectGet(name, userId);
//        ProjectDTO projectDTO = new ProjectDTO();
//        projectDTO.setName(userProjectEntity.getName());
//        projectDTO.setDescription(userProjectEntity.getDescription());
//        projectDTO.setUserId(userProjectEntity.getUserEntity().getId());
//        projectDTO.setImageUrl(userProjectEntity.getImageUrl());
//        projectDTO.setNote(userProjectEntity.getNote());
//        projectDTO.setId(userProjectEntity.getId());
//        projectDTO.setPrivate(userProjectEntity.getIsPrivate());
//        projectDTO.setSecret(userProjectEntity.getSecret());
//        projectDTO.setToken(userProjectEntity.getToken());
//        return projectDTO;
//    }
//
//    @Override
//    public ProjectDTO getProject(Long id, long userId) {
//        UserProjectEntity userProjectEntity = projectDAO.userProjectGet(id, userId);
//        ProjectDTO projectDTO = new ProjectDTO();
//        projectDTO.setName(userProjectEntity.getName());
//        projectDTO.setDescription(userProjectEntity.getDescription());
//        projectDTO.setUserId(userProjectEntity.getUserEntity().getId());
//        projectDTO.setImageUrl(userProjectEntity.getImageUrl());
//        projectDTO.setNote(userProjectEntity.getNote());
//        projectDTO.setId(userProjectEntity.getId());
//        projectDTO.setPrivate(userProjectEntity.getIsPrivate());
//        projectDTO.setSecret(userProjectEntity.getSecret());
//        projectDTO.setToken(userProjectEntity.getToken());
//        return projectDTO;
//    }
//
//    @Override
//    public List<ProjectDTO> getUserProjectList(long userId) {
//        List<ProjectDTO> projectDTOList = new ArrayList<>();
//        List<UserProjectEntity> userProjectEntityList;
//        userProjectEntityList = projectDAO.getUserProjectList(userId);
//        for (UserProjectEntity userProjectEntity : userProjectEntityList) {
//            ProjectDTO projectDTO = new ProjectDTO();
//            projectDTO.setName(userProjectEntity.getName());
//            projectDTO.setDescription(userProjectEntity.getDescription());
//            projectDTO.setUserId(userProjectEntity.getUserEntity().getId());
//            projectDTO.setImageUrl(userProjectEntity.getImageUrl());
//            projectDTO.setNote(userProjectEntity.getNote());
//            projectDTO.setId(userProjectEntity.getId());
//            projectDTO.setPrivate(userProjectEntity.getIsPrivate());
//            projectDTO.setSecret(userProjectEntity.getSecret());
//            projectDTO.setToken(userProjectEntity.getToken());
//            projectDTOList.add(projectDTO);
//        }
//        return projectDTOList;
//    }
//
//    @Override
//    public long projectPrivacyCreate(ProjectPrivacyDTO projectPrivacyDTO) {
//        return 0;
//    }
//
//    @Override
//    public ProjectDTO projectPrivacyRestrict(ProjectPrivacyDTO projectPrivacyDTO) {
//        return null;
//    }
//
//    @Override
//    public ProjectPrivacyDTO projectPrivacyDelete(ProjectPrivacyDTO projectPrivacyDTO) {
//        return null;
//    }
//
//    @Override
//    public ProjectPrivacyDTO getProjectPrivacy(String login, long projectId, long userId) {
//        return null;
//    }
//
//    @Override
//    public List<ProjectPrivacyDTO> getUserProjectPrivacyList(long projectId, long userId) {
//        return null;
//    }
}
