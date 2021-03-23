package com.mmia.inscope.services;

import com.mmia.inscope.exceptions.ResourceNotFoundException;
import com.mmia.inscope.models.User;
import com.mmia.inscope.repositories.UserRepository;
import com.mmia.inscope.views.IssueCountByUsername;
import com.mmia.inscope.views.IssueListByUsername;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.resource.OAuth2AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "userServices")
public class UserServiceImpl implements UserService
{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HelperFunctions helperFunctions;

    @Override
    public List<User> findAllUsers()
    {
        List<User> userList = new ArrayList<>();
        userRepository.findAll().iterator().forEachRemaining(userList::add);

        return userList;
    }

    @Override
    public User findUserById(long userid)
    {
        User user = userRepository.findById(userid)
                .orElseThrow(() -> new ResourceNotFoundException("User " + userid + " Not Found"));

        return user;
    }

    @Override
    public User findByName(String name)
    {
        User uu = userRepository.findByUsername(name);
        if (uu == null)
        {
            throw new ResourceNotFoundException("User name " + name + " not found!");
        }
        return uu;
    }

    @Override
    public List<IssueCountByUsername> getIssueCountByUsername()
    {
        List<IssueCountByUsername> count = userRepository.getIssueCountByUsername();
        return count;
    }

    @Override
    public List<IssueListByUsername> getIssueListByUsername()
    {
        List<IssueListByUsername> issueList = userRepository.getIssueListByUsername();
        return issueList;
    }

    @Transactional
    @Override
    public User save(User user)
    {
        User newUser = new User();

        if (user.getUserid() != 0)
        {
            userRepository.findById(user.getUserid())
                    .orElseThrow(() -> new ResourceNotFoundException("User id " + user.getUserid() + " not found!"));
            newUser.setUserid(user.getUserid());
        }
        newUser.setUsername(user.getUsername());
        newUser.setUserrole(user.getUserrole());
        newUser.setEmail(user.getEmail());
        newUser.setPasswordNoEncrypt(user.getPassword());

        return userRepository.save(newUser);
    }

    @Override
    public User update(User user, long id)
    {
        User currentuser = findUserById(id);

        if (helperFunctions.isAuthorizedToMakeChange(currentuser.getUsername()))
        {
            if (user.getUsername() != null)
            {
                currentuser.setUsername(user.getUsername());
            }
            if (user.getUserrole() != null)
            {
                currentuser.setUserrole(user.getUserrole());
            }
            if (user.getEmail() != null)
            {
                currentuser.setEmail(user.getEmail());
            }
            return userRepository.save(currentuser);
        } else
        {
            throw new OAuth2AccessDeniedException();
        }
    }

    @Override
    public void delete(long id)
    {
        userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User id "+ id +" not found!"));
        userRepository.deleteById(id);
    }
}
