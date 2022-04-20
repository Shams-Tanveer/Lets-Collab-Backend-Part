package com.example.Web.Project.services;

import com.example.Web.Project.controller.ProjectController;
import com.example.Web.Project.model.Project;

import java.util.List;
import java.util.Set;

public interface ProjectService {
    public Project addProject(Project project);
    public List<Project> getUserProject(String userID);
    public void addMember(String projectID,String userID);
    public void deleteMember(String projectID,String userID);
    public Project getOneProject(String projectID);
    public void updateProjectCompletion(String projectID);
    public List<ProjectController.CustomUser> getMembers(String projectID);
}
