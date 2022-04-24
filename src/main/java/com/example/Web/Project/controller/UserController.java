package com.example.Web.Project.controller;

import com.example.Web.Project.model.User;
import com.example.Web.Project.services.UserService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.SessionScope;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
@SessionScope
@RequestMapping("/user")
@CrossOrigin(origins = {"http://localhost:3000"}, allowedHeaders = "*", allowCredentials = "true")
public class UserController {

    @Autowired
    private UserService userService;


    /*
    This post mapping is used for user registration. It calls the UserService interface addUser method.
    If the method returns 1 then user is registered otherwise user has already registered with the email.
     */
    @PostMapping(value = "/add")
    public PostResponse add(@RequestBody User user, HttpServletRequest request) {
        PostResponse postResponse = new PostResponse();
        int insertResult = userService.addUser(user);
        HttpSession session = request.getSession();

        postResponse.setResultCode(insertResult);
        if (insertResult == 1) {
            User user1 = userService.getUserByEmail(user.getEmailid());
            session.setAttribute("userID", user1.getId());
            postResponse.setResultInLng("");
        } else {
            postResponse.setResultInLng("The email exits. Try with another one");
        }
        return postResponse;
    }

    /*
    * This post mapping is used for user sign in. It checks first whether the provided email exists in the database.If exists
    * the provided password is checked with the database and if it is matched a session is created otherwise
    * Email and Password Doesn't Match message is returned to the user. If email doesn't exist Email Does Not Exist message
    * is returned to the user.
     */
    @PostMapping("/finduser")
    public PostResponse findUser(@RequestBody User user, HttpServletRequest request, HttpServletResponse response) {
        boolean isExist = userService.checkExistByEmail(user.getEmailid());
        PostResponse postResponse = new PostResponse();
        HttpSession session = request.getSession();

        if (isExist) {
            User user1 = userService.getUserByEmail(user.getEmailid());

            if (user1.getPassword().equals(user.getPassword())) {

                session.setAttribute("userID", user1.getId());
                postResponse.setResultCode(1);
                postResponse.setResultInLng("Authentication Successful");
                return postResponse;
            } else {
                postResponse.setResultCode(2);
                postResponse.setResultInLng("Email and Password Doesn't Match");
                return postResponse;
            }
        } else {
            postResponse.setResultCode(0);
            postResponse.setResultInLng("Email Does Not Exist");
            return postResponse;
        }
    }

    /*
    * This get mapping returns user basic information.
     */

    @GetMapping("/userDetails")
    public User getUserDetails(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        String userID = (String) session.getAttribute("userID");
        User user = userService.getUserById(userID);
        return user;
    }

    /*
    * As the name suggests, this url is called to update user information.
    * */
    @PostMapping("/updateUser")
    public void updateUserDetails(@RequestBody User user, HttpServletRequest request) {
        userService.updateUser(user);
    }

    /*
    * logOutUser method invalidate the session when user click on the logout button.
    * */
    @PostMapping("/logoutUser")
    public void logOutUser(HttpServletRequest request){
        request.getSession(false).invalidate();
    }

    /*
    * As our web application allows only project master do some special task such as assign task or mark project as complete.
    * This isMaster function returns 1 if currently accessing user is master otherwise 0 as a response.
    * */

    @PostMapping("/getRole")
    public PostResponse isMaster(HttpServletRequest request)
    {
        PostResponse postResponse = new PostResponse();
        if(request.getSession(false).getAttribute("role").equals("master"))
        {
            postResponse.setResultCode(1);
            postResponse.setResultInLng("User Has Permission");
        }
        else
        {
            postResponse.setResultCode(0);
            postResponse.setResultInLng("Only project master can assign task.");
        }
      return postResponse;
    }


    /*
    * This is a custom class to send response to the front end.
    * */
    @Data
    class PostResponse {
        private int resultCode;
        private String resultInLng;
    }
}
