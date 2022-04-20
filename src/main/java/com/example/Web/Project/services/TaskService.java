package com.example.Web.Project.services;

import com.example.Web.Project.model.Task;

import java.util.List;

public interface TaskService {
    public List<Task> getGroupTask(String projectID);
    public Task getSpecificTask(String taskID);
    public void updateTaskType(String type,String taskID);
    public void updateTaskComplete(String taskID);
    public void updateCompleteSubTaskNumber(int value,String taskID);
    public void updateTotalSubTaskNumber(int value,String taskID);
    public Task addTask(Task task);
    public List<Task> getIndividualTask(String userID,String projectID);
    public void deleteTask(String taskID);
}
