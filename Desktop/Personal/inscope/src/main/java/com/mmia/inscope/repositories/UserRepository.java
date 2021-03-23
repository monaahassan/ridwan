package com.mmia.inscope.repositories;

import com.mmia.inscope.models.User;
import com.mmia.inscope.views.IssueCountByUsername;
import com.mmia.inscope.views.IssueListByUsername;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long>
{
    /**
     * Find a user based off over username
     *
     * @param username the name (String) of user you seek
     * @return the first user object with the name you seek
     */
    User findByUsername(String username);

    /**
     * Find total issue count by username
     */
    @Query(value = "SELECT u.username as username, u.userid as userid, count(p.userid) as issuecount " +
            "FROM users u JOIN issue p ON u.userid = p.userid  " +
            "GROUP BY u.username  " +
            "ORDER BY u.userid",
            nativeQuery = true)
    List<IssueCountByUsername> getIssueCountByUsername();

    /**
     * Find list of issues by username
     */
    @Query(value = "SELECT u.username as username, u.userid as userid, p.issuename as issuename, " +
            "p.issuedescription as issuedescription, p.issueid as issueid, p.projectid as projectid," +
            " p.statustypeid as statustype, p.issuetypeid as issuetype " +
            "FROM users u JOIN issue p ON u.userid = p.userid " +
            "GROUP BY u.username, p.issuename " +
            "ORDER BY u.userid",
            nativeQuery = true)
    List<IssueListByUsername> getIssueListByUsername();


}
