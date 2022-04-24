package com.example.Web.Project.services;

import com.example.Web.Project.model.SubTask;
import com.example.Web.Project.repository.SubTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubTaskServiceImpl implements SubTaskService{

    @Autowired
    private SubTaskRepository subTaskRepository;
    @Override
    public List<SubTask> getSubTask(String taskID) {
        return subTaskRepository.getSubTaskByTaskID(taskID);
    }

    @Override
    public SubTask getSingleSubTask(String subTaskID) {
        return subTaskRepository.getById(subTaskID);
    }

    @Override
    public void updateSubTask(String subTaskID,String subTaskName,boolean isComplete) {
        subTaskRepository.updateSubTaskName(subTaskID,subTaskName,isComplete);
    }

    @Override
    public void addSubTask(SubTask subTask) {
        subTaskRepository.save(subTask);
    }

    @Override
    public void deleteSubTask(String subTaskID) {
        subTaskRepository.deleteById(subTaskID);
    }


}
