package com.mmia.inscope.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "statustypes")
public class StatusType
{
    // Fields

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long statustypeid;

    private String status;

    /**
     * List of issue associated with this status type. Does not get save in the database
     * Forms a One-to-Many relationship with Issues. One status type to many issues
     */
    @OneToMany(mappedBy = "status", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties(value = "status", allowSetters = true)
    private Set<Issue> issues = new HashSet<>();

    // Constructor

    public StatusType()
    {
        // Required by JPA
    }

    public StatusType(String status)
    {
        this.status = status;
    }

    // Getters and Setters


    public long getStatustypeid()
    {
        return statustypeid;
    }

    public void setStatustypeid(long statustypeid)
    {
        this.statustypeid = statustypeid;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public Set<Issue> getIssues()
    {
        return issues;
    }

    public void setIssues(Set<Issue> issues)
    {
        this.issues = issues;
    }
}
