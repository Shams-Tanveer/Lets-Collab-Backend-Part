package com.example.Web.Project.services;

import com.example.Web.Project.model.SubTask;

import java.util.List;

public interface SubTaskService {
    public List<SubTask> getSubTask(String taskID);
    public SubTask getSingleSubTask(String subTaskID);
    public void updateSubTask(String subTaskID,String subTaskName,boolean isComplete);
    public void addSubTask(SubTask subTask);
    public void deleteSubTask(String subTaskID);
}
