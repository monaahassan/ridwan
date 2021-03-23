package com.mmia.inscope.services;

import com.mmia.inscope.models.Project;
import com.mmia.inscope.views.IssueListGroupedByProject;

import java.util.List;

public interface ProjectService
{
    List<Project> findAllProjects(); // get all projects

    Project findProjectById(long id); // get project by id

    List<IssueListGroupedByProject> findAllProjectsWithIssueName();

    Project save(Project project);

    void delete(long id);
}
