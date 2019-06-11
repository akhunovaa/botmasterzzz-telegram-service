package com.botmasterzzz.telegram.service;

import com.botmasterzzz.telegram.dto.ProjectDTO;

import java.util.List;

public interface ProjectService {

    long projectCreate(ProjectDTO projectDTO);

    void projectUpdate(ProjectDTO projectDTO);

    ProjectDTO projectTokenUpdate(ProjectDTO projectDTO);

    void projectImageUrlUpdate(ProjectDTO projectDTO);

    ProjectDTO projectDelete(ProjectDTO projectDTO);

    ProjectDTO getProject(String name, long userId);

    ProjectDTO getProject(Long id, long userId);

    List<ProjectDTO> getUserProjectList(long userId);

}
