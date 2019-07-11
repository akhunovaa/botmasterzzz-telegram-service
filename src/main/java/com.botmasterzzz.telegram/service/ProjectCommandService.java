package com.botmasterzzz.telegram.service;

import com.botmasterzzz.telegram.dto.ProjectCommandDTO;

import java.util.List;

public interface ProjectCommandService {

    Long projectCommandAdd(ProjectCommandDTO projectCommandDTO);

    ProjectCommandDTO projectCommandGet(ProjectCommandDTO projectCommandDTO);

    List<ProjectCommandDTO> getProjectCommandGetList(long userId, long projectId);

    List<ProjectCommandDTO> getProjectCommandGetList(long userId, long projectId, long[] id);

    ProjectCommandDTO projectCommandDelete(ProjectCommandDTO projectCommandDTO);

    ProjectCommandDTO projectCommandUpdate(ProjectCommandDTO projectCommandDTO);

    void projectCommandsGroupSave(List<ProjectCommandDTO> projectCommandDTOList);

    void projectCommandsGroupUpdate(List<ProjectCommandDTO> projectCommandDTOList);
}
