package com.example.Web.Project.controller;

import com.example.Web.Project.model.SubTask;
import com.example.Web.Project.services.SubTaskService;
import com.example.Web.Project.services.TaskService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.SessionScope;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@SessionScope
@RequestMapping("/subtask")
@CrossOrigin(origins = { "http://localhost:3000" },allowedHeaders = "*", allowCredentials = "true")
@RequiredArgsConstructor
public class SubTaskController {

    @Autowired
    private SubTaskService subTaskService;

    @Autowired
    private TaskService taskService;

    @GetMapping("/getSubTask")
    public List<SubTask> getAllSubTask(HttpServletRequest request)
    {
        String taskID = (String) request.getSession(false).getAttribute("curTaskID");
        List<SubTask> subTaskList = subTaskService.getSubTask(taskID);
        return subTaskList;
    }

    @PostMapping("/updateSubTask")
    public void updateSubTask(@RequestBody SubTask subTask,HttpServletRequest request)
    {
        SubTask subTask1 = (SubTask) subTaskService.getSingleSubTask(subTask.getId());
        HttpSession session = request.getSession(false);
        String taskID = (String) session.getAttribute("curTaskID");
        if(subTask.isComplete() && !subTask1.isComplete())
        {
            taskService.updateCompleteSubTaskNumber(1,taskID);
        }
        else if(!subTask.isComplete())
        {
            taskService.updateCompleteSubTaskNumber(-1,taskID);
        }
        subTaskService.updateSubTask(subTask.getId(),subTask.getName(),subTask.isComplete());
    }


    @PostMapping("/selectTask")
    public void selectedTask(@RequestBody String taskID,HttpServletRequest request)
    {
        taskID = taskID.substring(1,taskID.length()-1);
        request.getSession(false).setAttribute("curTaskID", taskID);
    }

    @PostMapping("/addSubTask")
    public void addSubTask(@RequestBody SubTask subTask,HttpServletRequest request)
    {
        subTask.setTaskID((String) request.getSession(false).getAttribute("curTaskID"));
        subTaskService.addSubTask(subTask);
        taskService.updateTotalSubTaskNumber(1, subTask.getTaskID());
    }

    @PostMapping("/deleteSubTask")
    public void deleteSubTask(@RequestBody String subTaskID,HttpServletRequest request)
    {

        subTaskID = subTaskID.substring(1,subTaskID.length()-1);
        SubTask subTask = subTaskService.getSingleSubTask(subTaskID);
        subTaskService.deleteSubTask(subTaskID);
        taskService.updateTotalSubTaskNumber(-1, (String) request.getSession(false).getAttribute("curTaskID"));
        if(subTask.isComplete())
        {
            taskService.updateCompleteSubTaskNumber(-1,(String) request.getSession(false).getAttribute("curTaskID"));
        }
    }
    @Data
    public static class SubTaskInfo{
        private String subTaskID;
        private String subTaskName;
    }

}
