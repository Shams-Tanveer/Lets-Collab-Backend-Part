package com.example.Web.Project.services;

import com.example.Web.Project.model.Task;
import com.example.Web.Project.repository.SubTaskRepository;
import com.example.Web.Project.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private SubTaskRepository subTaskRepository;

    @Override
    public List<Task> getGroupTask(String projectID) {
        return taskRepository.getTaskByProjectID(projectID);
    }

    @Override
    public Task getSpecificTask(String taskID) {
        return taskRepository.findById(taskID).get();
    }

    @Override
    public void updateTask(Task task) {
        taskRepository.updateTask(task.getId(),task.getName(),task.getAssignedTo(),task.getCompleteIn());
    }

    @Override
    public void updateTaskType(String type, String taskID) {
        taskRepository.updateTaskType(type, taskID);
    }

    @Override
    public void updateTaskComplete(String taskID) {
        taskRepository.updateTaskComplete(taskID);
    }

    @Override
    public void updateCompleteSubTaskNumber(int value, String taskID) {
        taskRepository.updateCompleteSubTask(value, taskID);
    }

    @Override
    public void updateTotalSubTaskNumber(int value, String taskID) {
        taskRepository.updateTotalSubTask(value, taskID);
    }

    @Override
    public Task addTask(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public List<Task> getIndividualTask(String userID, String projectID) {
        return taskRepository.getIndividualTask(userID, projectID);
    }

    @Override
    public void deleteTask(String taskID) {
        Task task = taskRepository.getById(taskID);
        taskRepository.deleteById(taskID);
        taskRepository.updateSequence();
        subTaskRepository.updateSequence(2);

    }
}
