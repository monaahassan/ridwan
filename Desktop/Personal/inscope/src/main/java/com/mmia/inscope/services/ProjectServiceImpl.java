package com.mmia.inscope.services;

import com.mmia.inscope.exceptions.ResourceNotFoundException;
import com.mmia.inscope.models.Project;
import com.mmia.inscope.repositories.IssueRepository;
import com.mmia.inscope.repositories.ProjectRepository;
import com.mmia.inscope.views.IssueListGroupedByProject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "projectService")
public class ProjectServiceImpl implements ProjectService
{
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private IssueService issueService;

    @Override
    public List<Project> findAllProjects()
    {
        List<Project> projectList = new ArrayList<>();
        projectRepository.findAll().iterator().forEachRemaining(projectList::add);

        return projectList;
    }

    @Override
    public Project findProjectById(long id)
    {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project id " + id + " not found!"));

        return project;
    }

    @Override
    public List<IssueListGroupedByProject> findAllProjectsWithIssueName()
    {
        List<IssueListGroupedByProject> projectList = projectRepository.getIssueListGroupedByProject();
        return projectList;
    }

    @Transactional
    @Override
    public Project save(Project project)
    {
        Project newProject = new Project();

        if (project.getProjectid() != 0)
        {
            projectRepository.findById(project.getProjectid())
                    .orElseThrow(() -> new ResourceNotFoundException("Project ID " + project.getProjectid() + " not found!"));
            newProject.setProjectid(project.getProjectid());
        }

        newProject.setProjectname(project.getProjectname());
        newProject.setProjectdescription(project.getProjectdescription());
        newProject.setIssue(project.getIssue());

//        newProject.getIssue().clear();
//        for (Issue p : project.getIssue())
//        {
//            Issue addIssue = issueService.findIssueById(p.getIssueid());
//
//            newProject.getIssue().add(new Issue(newProject, addIssue));
//
////            Issue addIssue = issueRepository.findById(p.getIssueid())
////                .orElseThrow(() -> new ResourceNotFoundException("Issue Id " + p.getIssueid() + " not found!"));
////            newProject.getIssue().add(new Issue(addIssueaddIssue, newProject));
//        }

        return projectRepository.save(newProject);
    }

    @Transactional
    @Override
    public void delete(long id)
    {
        projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project ID " + id + " not found!"));
        projectRepository.deleteById(id);
    }
}
