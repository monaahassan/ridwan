package com.mmia.inscope.controllers;

import com.mmia.inscope.models.User;
import com.mmia.inscope.services.UserService;
import com.mmia.inscope.views.IssueCountByUsername;
import com.mmia.inscope.views.IssueListByUsername;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController
{
    @Autowired
    private UserService userService;

    // GET REQUEST'S
    // Get all users
    // http://localhost:2019/users/users
    @GetMapping(value = "/users", produces = "application/json")
    public ResponseEntity<?> listAllUsers()
    {
        List<User> userList = userService.findAllUsers();
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }


    // Get user by userid
    // http://localhost:2019/users/user/{userid}
    @GetMapping(value = "/user/{userid}", produces = "application/json")
    public ResponseEntity<?> findUserById(@PathVariable long userid)
    {
        User user = userService.findUserById(userid);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // Get user by username
    @GetMapping(value = "/user/name/{username}", produces = "application/json")
    public ResponseEntity<?> getUserByName(@PathVariable String username)
    {
        User u = userService.findByName(username);
        return new ResponseEntity<>(u, HttpStatus.OK);
    }

    // Get issue counts by username grouped by userid
    @GetMapping(value = "/issues", produces = "application/json")
    public ResponseEntity<?> getIssueCountByUsername()
    {
        List<IssueCountByUsername> myList = userService.getIssueCountByUsername();
        return new ResponseEntity<>(myList, HttpStatus.OK);
    }

    //Get list of issues ordered by user id
    @GetMapping(value = "/issuelist", produces = "application/json")
    public ResponseEntity<?> getListOfIssues()
    {
        List<IssueListByUsername> myList = userService.getIssueListByUsername();
        return new ResponseEntity<>(myList, HttpStatus.OK);
    }

    // PUT Request
    // Given the user id update the user obj
    @PutMapping(value = "/user/{userid}", consumes = "application/json")
    public ResponseEntity<?> updateFullUser(@Valid @RequestBody User updateUser, @PathVariable long userid)
    {
        updateUser.setUserid(userid);
        userService.save(updateUser);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    //PATCH Request
    // Updates user record associated with the given id with provided data
    @PatchMapping(value = "/user/{userid}", consumes = "application/json")
    public ResponseEntity<?> updateUser(@RequestBody User updateUser, @PathVariable long userid)
    {
        userService.update(updateUser, userid);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    //DELETE Request
    // Given user id, delete user
    @DeleteMapping(value = "/user/{userid}")
    public ResponseEntity<?> deleteUserById(@PathVariable long userid)
    {
        userService.delete(userid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Returns the User record for the currently authenticated user based off of the supplied access token
     * <br>Example: <a href="http://localhost:2019/users/getuserinfo">http://localhost:2019/users/getuserinfo</a>
     *
     * @param authentication The authenticated user object provided by Spring Security
     * @return JSON of the current user. Status of OK
     * @see UserService#findByName(String) UserService.findByName(authenticated user)
     */
    @ApiOperation(value = "returns the currently authenticated user", response = User.class)
    @GetMapping(value = "/getuserinfo", produces = {"application/json"})
    public ResponseEntity<?> getCurrentUserInfo(Authentication authentication)
    {
        User u = userService.findByName(authentication.getName());

        return new ResponseEntity<>(u, HttpStatus.OK);
    }
}
