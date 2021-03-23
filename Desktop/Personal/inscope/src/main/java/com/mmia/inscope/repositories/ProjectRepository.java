package com.mmia.inscope.repositories;

import com.mmia.inscope.models.Project;
import com.mmia.inscope.views.IssueListGroupedByProject;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProjectRepository extends CrudRepository<Project, Long>
{
    @Query(value = "SELECT pj.projectid as projectid, pj.projectname as projectname, pj.projectdescription as projectdescription, " +
            "p.issuename as issuename, p.issuedescription as issuedescription " +
            "FROM projects pj " +
            "JOIN issue p " +
            "ON pj.projectid = p.projectid " +
            "GROUP BY pj.projectname, p.issuename",
            nativeQuery = true)
    List<IssueListGroupedByProject> getIssueListGroupedByProject();
}
