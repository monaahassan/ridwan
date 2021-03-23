package com.mmia.inscope.controllers;

import com.mmia.inscope.models.Project;
import com.mmia.inscope.services.ProjectService;
import com.mmia.inscope.views.IssueListGroupedByProject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController
{
    @Autowired
    private ProjectService projectService;

    //GET REQUESTS

    //Get all project list
    //http://localhost:2019/projects/projects
    @GetMapping(value = "/projects", produces = "application/json")
    public ResponseEntity<?> listAllProjects()
    {
        List<Project> projectList = projectService.findAllProjects();

        return new ResponseEntity<>(projectList, HttpStatus.OK);
    }

    // Get Project by Id
    //http://localhost:2019/projects/project/{id}
    @GetMapping(value = "/project/{projectid}", produces = "application/json")
    public ResponseEntity<?> findProjectById(@PathVariable long projectid)
    {
        Project project = projectService.findProjectById(projectid);

        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    // Get Projects with respective problem name and description
    // http://localhost:2019/projects/projectslist
    @GetMapping(value = "/projectslist", produces = "application/json")
    public ResponseEntity<?> findAllProjectsWithProblemName()
    {
        List<IssueListGroupedByProject> projectList = projectService.findAllProjectsWithIssueName();

        return new ResponseEntity<>(projectList, HttpStatus.OK);
    }

    // POST
    // Add new Project http://localhost:2019/projects/project
    @PostMapping(value = "/project", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> addNewProject(@Valid @RequestBody Project newProject) throws URISyntaxException
    {
        newProject.setProjectid(0);
        newProject = projectService.save(newProject);

        // set the location header for the newly created resource
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newProjectURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{projectid}")
                .buildAndExpand(newProject.getProjectid())
                .toUri();
        responseHeaders.setLocation(newProjectURI);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    // PUT
    // Update project by project ID
    // http://localhost:2019/projects/project/{projectid}
    @PutMapping(value = "/project/{projectid}", consumes = "application/json")
    public ResponseEntity<?> updateFullProject(@Valid @RequestBody Project updateProject, @PathVariable long projectid)
    {
        updateProject.setProjectid(projectid);
        projectService.save(updateProject);

        return new ResponseEntity<>(HttpStatus.OK);
    }


    // DELETE
    // http://localhost:2019/projects/project/{projectid}
    @DeleteMapping(value = "/project/{projectid}")
    public ResponseEntity<?> deleteProjectById(@PathVariable long projectid)
    {
        projectService.delete(projectid);

        return new ResponseEntity<>(HttpStatus.OK);
    }




}
