package com.mmia.inscope.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "issuetypes")
public class IssueType
{
    // Fields

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long issuetypeid;

    private String issuetype;

    /**
     * List of issues associated with this issuetype. Does not get saved in the database
     * Forms a One-to-Many relationship with issues. One issue type to many issue
     */
    @OneToMany(mappedBy = "issuetype", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties(value = "issuetype", allowSetters = true)
    private Set<Issue> issue = new HashSet<>();  // this needs to be set or list?

    // Constructors


    public IssueType()
    {
        // required by JPA
    }

    public IssueType(String issuetype)
    {
        this.issuetype = issuetype;
    }

    // Getters and Setters


    public long getIssuetypeid()
    {
        return issuetypeid;
    }

    public void setIssuetypeid(long issuetypeid)
    {
        this.issuetypeid = issuetypeid;
    }

    public String getIssuetype()
    {
        return issuetype;
    }

    public void setIssuetype(String issuetype)
    {
        this.issuetype = issuetype;
    }

    public Set<Issue> getIssues()
    {
        return issue;
    }

    public void setIssue(Set<Issue> issue)
    {
        this.issue = issue;
    }
}

