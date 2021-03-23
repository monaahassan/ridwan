package com.mmia.inscope.services;

import com.mmia.inscope.models.User;
import com.mmia.inscope.views.IssueCountByUsername;
import com.mmia.inscope.views.IssueListByUsername;

import java.util.List;

public interface UserService
{

    List<User> findAllUsers(); // get all users

    User findUserById(long userid); // get user by id

    /**
     * Returns the user with the given name
     *
     * @param name The full name (String) of the User you seek.
     * @return The User with the given name or throws an exception if not found.
     */
    User findByName(String name);

    List<IssueCountByUsername> getIssueCountByUsername();

    List<IssueListByUsername> getIssueListByUsername();

    User save(User user);

    User update(User user, long id);

    void delete(long id);
}
