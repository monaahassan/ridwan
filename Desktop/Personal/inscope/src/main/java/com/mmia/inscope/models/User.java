package com.mmia.inscope.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "users")
public class User extends Auditable
{
    // Fields

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userid;

    @Column(nullable = false, unique = true)
    private String username;

    /**
     * A foreign key to role type table
     * Forms a Many-to_one relationship with the roletypes table
     * Many Users to one role type
     * Contains a object pointer to the full roletype object
     */
    @ManyToOne
    @JoinColumn(name = "roletypeid", nullable = false)
    @JsonIgnoreProperties(value = "users", allowSetters = true)
    private RoleType userrole;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    /**
     * List of issues associated with this user. Does not get save in the database
     * Forms a One-to-Many relationship with issues. One user to many issues
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties(value = "user", allowSetters = true)
    private Set<Issue> issues = new HashSet<>();

    // Constructors


    public User()
    {
        // required by JPA
    }

    public User(
            String username,
            RoleType userrole,
            String email,
            String password)
    {
        this.username = username;
        this.userrole = userrole;
        this.email = email;
        this.password = password;
    }

    // Getters and Setters

    public long getUserid()
    {
        return userid;
    }

    public void setUserid(long userid)
    {
        this.userid = userid;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }


    public RoleType getUserrole()
    {
        return userrole;
    }

    public void setUserrole(RoleType userrole)
    {
        this.userrole = userrole;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPassword()
    {
        return password;
    }

    /**
     * @param password the new password (String) for this user. Comes in plain text and goes out encrypted
     */
    public void setPassword(String password)
    {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.password = passwordEncoder.encode(password);
    }

    /**
     * Setter for password to be used internally, after the password has already been encrypted
     *
     * @param password the new password (String) for the user. Comes in encrypted and stays that way
     */
    public void setPasswordNoEncrypt(String password)
    {
        this.password = password;
    }

    /**
     * Getter for item combinations
     *
     * @return A list of issues(tickets) associated with this user
     */
    public Set<Issue> getIssues()
    {
        return issues;
    }

    public void setIssues(Set<Issue> issues)
    {
        this.issues = issues;
    }

    /**
     * Internally, user security requires a list of authorities, roles, that the user has. This method is a simple way to provide those.
     * Note that SimpleGrantedAuthority requests the format ROLE_role name all in capital letters!
     *
     * @return The list of authorities, roles, this user object has
     */
    @JsonIgnore
    public List<SimpleGrantedAuthority> getAuthority()
    {
        List<SimpleGrantedAuthority> rtnList = new ArrayList<>();

        String myRole = "ROLE_" + userrole.getRoletype().toUpperCase();

        rtnList.add(new SimpleGrantedAuthority(myRole));

        return rtnList;
    }
}
