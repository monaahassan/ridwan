package com.mmia.inscope.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "Issues")
public class Issue extends Auditable
{
    // Fields
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long issueid;

    @Column(nullable = false)
    private String issuename;

    @Column(nullable = false)
    private String issuedescription;

    /**
     * A foreign key to issue type table
     * Forms a Many-to_one relationship with the issuetypes table, Many issues to one issue type
     * Contains a object pointer to the full issue type object
     */
    @ManyToOne
    @JoinColumn(name = "issuetypeid", nullable = false)
    @JsonIgnoreProperties(value = "issues", allowSetters = true)
    private IssueType issuetype;

    /**
     * A foreign key to project table
     * Forms a Many-to_one relationship with the projects table, Many issues to one project
     * Contains a object pointer to the full issue object
     */
    @ManyToOne
    @JoinColumn(name = "projectid", nullable = false)
    @JsonIgnoreProperties(value = "issues", allowSetters = true)
    private Project project;

    /**
     * A foreign key to user table
     * Forms a Many-to_one relationship with the users table, Many issues to one user
     * Contains a object pointer to the full issue object
     */
    @ManyToOne
    @JoinColumn(name = "userid", nullable = false)
    @JsonIgnoreProperties(value = "issues", allowSetters = true)
    private User user;

    /**
     * A foreign key to status type table
     * Forms a Many-to_one relationship with the status type table, Many issues to one status type
     * Contains a object pointer to the full issue object
     */
    @ManyToOne
    @JoinColumn(name = "statustypeid", nullable = false)
    @JsonIgnoreProperties(value = "issues", allowSetters = true)
    private StatusType status;

    // Constructors


    public Issue()
    {
        //required by JPA
    }

    public Issue(
            String issuename,
            String issuedescription,
            IssueType issueType,
            Project project,
            User user,
            StatusType status)
    {
        this.issuename = issuename;
        this.issuedescription = issuedescription;
        this.issuetype = issuetype;
        this.project = project;
        this.user = user;
        this.status = status;
    }

    // getters and setters


    public long getIssueid()
    {
        return issueid;
    }

    public void setIssueid(long issueid)
    {
        this.issueid = issueid;
    }

    public String getIssuename()
    {
        return issuename;
    }

    public void setIssuename(String issuename)
    {
        this.issuename = issuename;
    }

    public String getIssuedescription()
    {
        return issuedescription;
    }

    public void setIssuedescription(String issuedescription)
    {
        this.issuedescription = issuedescription;
    }

    public IssueType getIssuetype()
    {
        return issuetype;
    }

    public void setIssuetype(IssueType issuetype)
    {
        this.issuetype = issuetype;
    }

    public Project getProject()
    {
        return project;
    }

    public void setProject(Project project)
    {
        this.project = project;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public StatusType getStatus()
    {
        return status;
    }

    public void setStatus(StatusType status)
    {
        this.status = status;
    }

    // add getter for createddate;

    public String getCreatedby()
    {
        return createdby;
    }

    public void setCreatedby()
    {
        this.createdby = createdby;
    }

    public String getLastmodifiedby()
    {
        return lastmodifiedby;
    }

    public void setLastmodifiedby()
    {
        this.lastmodifiedby = lastmodifiedby;
    }
}
