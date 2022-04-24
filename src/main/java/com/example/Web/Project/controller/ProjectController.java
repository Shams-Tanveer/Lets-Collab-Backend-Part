package com.example.Web.Project.controller;

import com.example.Web.Project.model.Project;
import com.example.Web.Project.model.User;
import com.example.Web.Project.services.ProjectService;
import com.example.Web.Project.services.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.SessionScope;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@SessionScope
@RequestMapping("/project")
@CrossOrigin(origins = { "http://localhost:3000" },allowedHeaders = "*", allowCredentials = "true")
@RequiredArgsConstructor
public class ProjectController {

    private String emailRegex = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

    @Autowired
    private ProjectService projectService;
    @Autowired
    private UserController userController;

    @Autowired
    private UserService userService;

    @PostMapping(value = "/newProject")
    public void addProject(@RequestBody Project project,HttpServletRequest request) {
        String latestProjectID = "";
        HttpSession session = request.getSession(false);
        if(project.getProjectAdmin().equals("You are the admin"))
        {
            project.setProjectAdmin((String) session.getAttribute("userID"));
        }
        else{

          User user =  userService.addNoRegistered(project.getProjectAdmin());
          project.setProjectAdmin(user.getId());
        }

        Project project1 = projectService.addProject(project);
        latestProjectID = project1.getId();
        session.setAttribute("curProjectID",project1.getId());
        projectService.addMember(latestProjectID,project.getProjectAdmin());

        if(!project.getProjectAdmin().equals(session.getAttribute("userID"))){
            projectService.addMember(latestProjectID, (String) session.getAttribute("userID"));
        }
    }

    @GetMapping("/getProject")
    public List<Project> getProjectList(HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        List<Project> projectList1 = projectService.getUserProject((String) session.getAttribute("userID"));

        return projectList1;
    }

    @PostMapping("/newProjectMember")
    public void addMember(@RequestBody MemberCredentials memberCredentials,HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        String projectID = (String) session.getAttribute("curProjectID");
        User user = userService.addNoRegistered(memberCredentials.getMemberID());
        projectService.addMember(projectID, user.getId());
    }

    @PostMapping("/deleteProjectMember")
    public void deleteMember(@RequestBody MemberCredentials memberCredentials,HttpServletRequest request)
    {
        HttpSession session = request.getSession(false);
        String projectID = (String) session.getAttribute("curProjectID");
        projectService.deleteMember(projectID,memberCredentials.getMemberID());
    }

    @GetMapping("/getMembers")
    public List<CustomUser> getMembers(HttpServletRequest request){
        List<CustomUser> members = projectService.getMembers((String) request.getSession(false).getAttribute("curProjectID"));
        return members;
    }

    @GetMapping("/completeProject")
    public PostResponse completeProject(HttpServletRequest request)
    {
        PostResponse postResponse = new PostResponse();
        HttpSession session = request.getSession(false);
        if(session.getAttribute("role").equals("master"))
        {
            postResponse.setResultCode(1);
            postResponse.setResultInLng("Project Mark As Complete");
            projectService.updateProjectCompletion((String) session.getAttribute("curProjectID"));
        }
        else
        {
            postResponse.setResultCode(0);
            postResponse.setResultInLng("You are not the Master");
        }

        return postResponse;
    }

    @PostMapping("/selectProject")
    public void setSelectedProject(@RequestBody String projectID, HttpServletRequest request) {
        projectID = projectID.substring(1, projectID.length() - 1);
        HttpSession session = request.getSession(false);
        session.setAttribute("curProjectID", projectID);
        Project project = projectService.getOneProject(projectID);
        if (project.getProjectAdmin().equals(session.getAttribute("userID"))) {
            session.setAttribute("role", "master");
        } else {
            session.setAttribute("role", "member");
        }
    }

    @GetMapping("/chat")
    public ChatInfo getChatInfo(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        ChatInfo chatInfo = new ChatInfo();
        String userId = (String) session.getAttribute("userID");
        String projectId = (String) session.getAttribute("curProjectID");
        User user = userService.getUserById(userId);
        Project project = projectService.getOneProject(projectId);
        chatInfo.setUserID(userId);
        chatInfo.setUserName(user.getName());
        chatInfo.setProjectID(projectId);
        chatInfo.setProjectName(project.getProjectName());
        return chatInfo;
    }



    @Data
    public static
    class CustomUser{
        private String userID;
        private String userEmail;
    }

    @Data
    public static class MemberCredentials{
        private String memberID;
    }

    @Data
    class PostResponse{
        private int resultCode;
        private String resultInLng;
    }

    @Data
    class ChatInfo{
        private String userID;
        private String userName;
        private String projectID;
        private String projectName;
    }

}
