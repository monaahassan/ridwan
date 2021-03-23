package com.mmia.inscope.services;

import com.mmia.inscope.exceptions.ResourceNotFoundException;
import com.mmia.inscope.models.Issue;
import com.mmia.inscope.repositories.IssueRepository;
import com.mmia.inscope.views.IssueList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.resource.OAuth2AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "issueService")
public class IssueServiceImpl implements IssueService
{
    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private HelperFunctions helperFunctions;

    @Override
    public List<Issue> findAllIssues()
    {
        List<Issue> issueList = new ArrayList<>();
        issueRepository.findAll().iterator().forEachRemaining(issueList::add);

        return issueList;
    }

    @Override
    public List<IssueList> findAllIssuesJoinedList()
    {
        List<IssueList> issueList = issueRepository.getAllIssuesJoinedList();
        return issueList;
    }

    @Override
    public Issue findIssueById(long issueid)
    {
        Issue issue = issueRepository.findById(issueid)
                .orElseThrow(() -> new EntityNotFoundException("Issue " + issueid + "Not Found"));

        return issue;
    }

    @Transactional
    @Override
    public Issue save(Issue issue)
    {
        Issue newIssue = new Issue();

        if (issue.getIssueid() != 0)
        {
            issueRepository.findById(issue.getIssueid())
                    .orElseThrow(() -> new ResourceNotFoundException("Issue Id + " + issue.getIssueid() + " not found!"));
            newIssue.setIssueid(issue.getIssueid());
        }


        newIssue.setIssuename(issue.getIssuename());
        newIssue.setIssuedescription(issue.getIssuedescription());
        newIssue.setIssuetype(issue.getIssuetype());
        newIssue.setProject(issue.getProject());
        newIssue.setUser(issue.getUser());
        newIssue.setStatus(issue.getStatus());

        return issueRepository.save(newIssue);
    }

    @Transactional
    @Override
    public Issue update(Issue issue, long id)
    {
        Issue currentIssue = findIssueById(id);

        if (helperFunctions.isAuthorizedToMakeChange(currentIssue.getIssuename()))
        {
            if (issue.getIssuename() != null)
            {
                currentIssue.setIssuename(issue.getIssuename());
            }
            if (issue.getIssuedescription() != null)
            {
                currentIssue.setIssuedescription(issue.getIssuedescription());
            }
            if (issue.getIssuetype() != null)
            {
                currentIssue.setIssuetype(issue.getIssuetype());
            }
            if (issue.getProject() != null)
            {
                currentIssue.setProject(issue.getProject());
            }
            if (issue.getUser() != null)
            {
                currentIssue.setUser(issue.getUser());
            }
            if (issue.getStatus() != null)
            {
                currentIssue.setStatus(issue.getStatus());
            }
            return issueRepository.save(currentIssue);
        } else
        {
            // note we should never get to this line but is needed for the compiler
            // to recognize that this exception can be thrown
            throw new OAuth2AccessDeniedException();
        }
    }

    @Transactional
    @Override
    public void delete(long id)
    {
        issueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Issue ID " + id + " not found!"));
        issueRepository.deleteById(id);
    }
}
