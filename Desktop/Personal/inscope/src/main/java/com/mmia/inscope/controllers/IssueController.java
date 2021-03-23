package com.mmia.inscope.controllers;

import com.mmia.inscope.models.Issue;
import com.mmia.inscope.services.IssueService;
import com.mmia.inscope.views.IssueList;
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
@RequestMapping("/issues")
public class IssueController
{
    @Autowired
    public IssueService issueService;

    // GET REQUESTS

    // get all Issues
    //http://localhost:2019/issues/issues  // Not working same 500 error ask john or jamie
    @GetMapping(value = "/issues", produces = "application/json")
    public ResponseEntity<?> listAllIssue()
    {
        List<Issue> issueList = issueService.findAllIssues();

        return new ResponseEntity<>(issueList, HttpStatus.OK);
    }

    // get all Issues custom view joined list
    //http://localhost:2019/issues/issuesjoinedlist
    @GetMapping(value = "/issuesjoinedlist", produces = "application/json")
    public ResponseEntity<?> listAllIssueCustom()
    {
        List<IssueList> issueList = issueService.findAllIssuesJoinedList();

        return new ResponseEntity<>(issueList, HttpStatus.OK);
    }

    // get  Issue by issueid
    //http://localhost:2019/issues/issue/{issueid}  // Not working same 500 error ask john or jamie
    @GetMapping(value = "/issue/{issueid}", produces = "application/json")
    public ResponseEntity<?> findIssueById(@PathVariable long issueid)
    {
        Issue issue = issueService.findIssueById(issueid);

        return new ResponseEntity<>(issue, HttpStatus.OK);
    }

    //POST
    //Create new Issues
    @PostMapping(value = "/issues", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> addNewIssue(@Valid @RequestBody Issue newissue) throws URISyntaxException
    {
        newissue.setIssueid(0);
        newissue = issueService.save(newissue);

        // set the location header for the newly created resource
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newIssueURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("{/issueid}")
                .buildAndExpand(newissue.getIssueid())
                .toUri();
        responseHeaders.setLocation(newIssueURI);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    //PUT
    // Update Issue by issues id
    @PutMapping(value = "issues/{issueid}", consumes = "application/json")
    public ResponseEntity<?> updateIssue(@Valid @RequestBody Issue updateIssue, @PathVariable long issueid)
    {
        updateIssue.setIssueid(issueid);
        issueService.save(updateIssue);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    //DELETE
    // Delete Issue by id
    @DeleteMapping(value = "/issues/{issueid}")
    public ResponseEntity<?> deleteItemById(@PathVariable long issueid)
    {
        issueService.delete(issueid);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
