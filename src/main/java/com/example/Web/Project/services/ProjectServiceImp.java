package com.example.Web.Project.services;

import com.example.Web.Project.controller.ProjectController;
import com.example.Web.Project.model.Project;
import com.example.Web.Project.model.User;
import com.example.Web.Project.repository.ProjectRepository;
import com.example.Web.Project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class ProjectServiceImp implements ProjectService{

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Project addProject(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public List<Project> getUserProject(String userID) {
        //User user = userRepository.getById(userID);
        //return user.getProjectSet();
        return projectRepository.getUserProject(userID);
    }

    @Override
    public void addMember(String projectID, String userID) {
        projectRepository.addMember(projectID,userID);
    }

    @Override
    public void deleteMember(String projectID, String userID) {
        projectRepository.deleteMember(projectID,userID);
    }

    @Override
    public Project getOneProject(String projectID) {
        return projectRepository.getById(projectID);
    }

    @Override
    public void updateProjectCompletion(String projectID) {
        projectRepository.updateProjectComplete(projectID);
    }


    @Override
    public List<ProjectController.CustomUser> getMembers(String projectID) {
        List<String> memberList = projectRepository.projectMembers(projectID);
        List<ProjectController.CustomUser> memberUserList = new ArrayList<>();

        for(int i=0;i< memberList.size();i++)
        {
            String  userID= userRepository.getUserCertainInfoByEmail(memberList.get(i));
            ProjectController.CustomUser user = new ProjectController.CustomUser();
            user.setUserID(userID);
            user.setUserEmail(memberList.get(i));
            memberUserList.add(user);
        }
        return memberUserList;
    }



}
