package com.mmia.inscope.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "roletypes")
public class RoleType
{
    //Fields

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long roletypeid;

    private String roletype;

    /**
     * List of users associated with this role type. Does not get save in the database
     * Forms a One-to-Many relationship with Users. One role to many users
     */

    @OneToMany(mappedBy = "userrole", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties(value = "userrole", allowSetters = true)
    private Set<User> users = new HashSet<>();  // does this need to be a set?

    // Constructor


    public RoleType()
    {
        // required by JPA
    }

    public RoleType(String roletype)
    {
        this.roletype = roletype;
    }

    // Getters and Setters


    public long getRoletypeid()
    {
        return roletypeid;
    }

    public void setRoletypeid(long roletypeid)
    {
        this.roletypeid = roletypeid;
    }

    public String getRoletype()
    {
        return roletype;
    }

    public void setRoletype(String roletype)
    {
        this.roletype = roletype;
    }

}
