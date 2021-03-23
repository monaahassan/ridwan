package com.mmia.inscope.repositories;

import com.mmia.inscope.models.Issue;
import com.mmia.inscope.views.IssueList;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IssueRepository extends CrudRepository<Issue, Long>
{
    @Query(value = "SELECT p.issueid, pj.projectname, p.issuename, p.issuedescription, u.username as assignedto, " +
            "u.email as assignedtoemail, pt.issuetype, s.status as status " +
            "FROM issue p " +
            "JOIN users u ON p.userid=u.userid " +
            "JOIN statustypes s ON p.statustypeid=s.statustypeid " +
            "JOIN projects pj ON p.statustypeid=pj.projectid " +
            "JOIN isssuetypes pt ON p.issuetypeid=pt.issuetypeid",
            nativeQuery = true)
    List<IssueList> getAllIssuesJoinedList();
}
