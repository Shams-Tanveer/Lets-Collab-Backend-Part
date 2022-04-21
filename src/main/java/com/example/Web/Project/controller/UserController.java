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


    @PostMapping(value = "/add")
    public PostResponse add(@RequestBody User user, HttpServletRequest request) {
        System.out.println(user);
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
        System.out.println(session.getAttribute("userID"));
        return postResponse;
    }

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

    @GetMapping("/userDetails")
    public User getUserDetails(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        String userID = (String) session.getAttribute("userID");
        User user = userService.getUserById(userID);
        return user;
    }

    @PostMapping("/updateUser")
    public void updateUserDetails(@RequestBody User user, HttpServletRequest request) {
        System.out.println("Update User");
        userService.updateUser(user);
    }

    @PostMapping("/logoutUser")
    public void logOutUser(HttpServletRequest request){
        request.getSession(false).invalidate();
    }

    @Data
    class PostResponse {
        private int resultCode;
        private String resultInLng;
    }
}
