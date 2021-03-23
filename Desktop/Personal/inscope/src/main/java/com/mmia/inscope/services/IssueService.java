package com.mmia.inscope.services;

import com.mmia.inscope.models.Issue;
import com.mmia.inscope.views.IssueList;

import java.util.List;

public interface IssueService
{

    List<Issue> findAllIssues(); // get all issue

    Issue findIssueById(long issueid); // get issue by id

    List<IssueList> findAllIssuesJoinedList();  // custom view of issue list

    Issue save(Issue issue); // add new issue

    Issue update(Issue issue, long id); // update issue

    void delete(long id);
}
