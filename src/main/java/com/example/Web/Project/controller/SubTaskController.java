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
        System.out.println(taskID);
        System.out.println("Get SUb Task");
        List<SubTask> subTaskList = subTaskService.getSubTask(taskID);
        return subTaskList;
    }

    @PostMapping("/updateSubTask")
    public void updateSubTask(@RequestBody SubTask subTask,HttpServletRequest request)
    {
        System.out.println(subTask.isComplete());
        subTaskService.updateSubTask(subTask.getId(),subTask.getName(),subTask.isComplete());

        HttpSession session = request.getSession(false);
        System.out.println(session.getAttribute("userID")+"HLWLW");
        String taskID = (String) session.getAttribute("curTaskID");
        if(subTask.isComplete())
        {
            taskService.updateCompleteSubTaskNumber(1,taskID);
        }
        else
        {
            taskService.updateCompleteSubTaskNumber(-1,taskID);
        }
    }


    @PostMapping("/selectTask")
    public void selectedTask(@RequestBody String taskID,HttpServletRequest request)
    {
        taskID = taskID.substring(1,taskID.length()-1);
        request.getSession(false).setAttribute("curTaskID", taskID);
        System.out.println( request.getSession(false).getAttribute("curTaskID"));
    }

    @PostMapping("/addSubTask")
    public void addSubTask(@RequestBody SubTask subTask,HttpServletRequest request)
    {
        System.out.println(subTask.getName());
        subTask.setTaskID((String) request.getSession(false).getAttribute("curTaskID"));
        subTaskService.addSubTask(subTask);
        taskService.updateTotalSubTaskNumber(1, subTask.getTaskID());
    }

    @PostMapping("/deleteSubTask")
    public void deleteSubTask(@RequestBody String subTaskID,HttpServletRequest request)
    {
        subTaskID = subTaskID.substring(1,subTaskID.length()-1);
        subTaskService.deleteSubTask(subTaskID);
        taskService.updateTotalSubTaskNumber(-1, (String) request.getSession(false).getAttribute("curTaskID"));
    }
    @Data
    public static class SubTaskInfo{
        private String subTaskID;
        private String subTaskName;
    }

}
