package com.example.Web.Project.controller;

import com.example.Web.Project.model.Project;
import com.example.Web.Project.model.SubTask;
import com.example.Web.Project.model.Task;
import com.example.Web.Project.model.User;
import com.example.Web.Project.services.ProjectService;
import com.example.Web.Project.services.SubTaskService;
import com.example.Web.Project.services.TaskService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.SessionScope;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
@SessionScope
@RequestMapping("/task")
@CrossOrigin(origins = {"http://localhost:3000"}, allowedHeaders = "*", allowCredentials = "true")
@RequiredArgsConstructor
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private SubTaskService subTaskService;

    @GetMapping("/groupTask")
    public GroupTaskInfo getGroupTask(HttpServletRequest request) {
        List<Task> taskList = new ArrayList<Task>();
        String projectID = (String) request.getSession(false).getAttribute("curProjectID");
        Project project = projectService.getOneProject(projectID);
        taskList = taskService.getGroupTask(projectID);
        GroupTaskInfo groupTaskInfo = new GroupTaskInfo();
        groupTaskInfo.setProjectName(project.getProjectName());
        groupTaskInfo.setTaskList(taskList);
        return groupTaskInfo;
    }

    @GetMapping("/individualTask")
    public List<Task> getIndividualTask(HttpServletRequest request) {
        List<Task> taskList = new ArrayList<Task>();
        String projectID = (String) request.getSession(false).getAttribute("curProjectID");
        String userID = (String) request.getSession(false).getAttribute("userID");
        System.out.println(projectID);
        taskList = taskService.getIndividualTask(userID, projectID);
        return taskList;
    }

    @GetMapping("/specificTask")
    public CustomTask getSpeificTask(HttpServletRequest request) {

        String taskID = (String) request.getSession(false).getAttribute("curTaskID");
        Task task = taskService.getSpecificTask(taskID);
        System.out.println(task.getProjectID());
        List<ProjectController.CustomUser> members = projectService.getMembers(task.getProjectID());
        CustomTask customTask = new CustomTask();
        customTask.setTask(task);
        customTask.setMembers(members);
        return customTask;
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
        System.out.println(projectID);
    }


    @PostMapping("/movetask")
    public ResponseMessage moveTask(@RequestBody MovedTaskInfo movedTaskInfo, HttpServletRequest request) {
        ResponseMessage responseMessage = new ResponseMessage();

        if (movedTaskInfo.getSource().equals("1") && movedTaskInfo.getDestination().equals("2")) {
            taskService.updateTaskType("inprogress", movedTaskInfo.getTaskID());
        } else if (movedTaskInfo.getSource().equals("2") && movedTaskInfo.getDestination().equals("3")) {
            taskService.updateTaskType("review", movedTaskInfo.getTaskID());
        } else if (movedTaskInfo.getSource().equals("3") && movedTaskInfo.getDestination().equals("2")) {
            taskService.updateTaskType("inprogress", movedTaskInfo.getTaskID());
        }

        if (movedTaskInfo.getDestination().equals("4") && request.getSession(false).getAttribute("role").equals("master")) {
            if (movedTaskInfo.getSource().equals("2") && movedTaskInfo.getDestination().equals("4")) {
                taskService.updateTaskType("done", movedTaskInfo.getTaskID());
            } else if (movedTaskInfo.getSource().equals("3") && movedTaskInfo.getDestination().equals("4")) {
                taskService.updateTaskType("done", movedTaskInfo.getTaskID());
            }
            taskService.updateTaskComplete(movedTaskInfo.getTaskID());
            responseMessage.setResultCode(1);
            responseMessage.setResultInMsg("Task Move Complete");
        } else if (movedTaskInfo.getDestination().equals("4") && !request.getSession(false).getAttribute("role").equals("master")) {
            responseMessage.setResultCode(0);
            responseMessage.setResultInMsg("Only project master can mark task as done");
        }
        return responseMessage;
    }

    @PostMapping("/addTask")
    public void addTask(@RequestBody Task task, HttpServletRequest request) {
        System.out.println(task.getName());
        task.setCompletedsubtask(0);
        task.setComplete(false);
        task.setProjectID((String) request.getSession(false).getAttribute("curProjectID"));
        task.setTotalsubtask(1);
        task.setType("todo");
        Task task1 = taskService.addTask(task);
        System.out.println(task1.getName());
    }

    @GetMapping("/deleteTask")
    public void deleteTask(HttpServletRequest request) {
        String taskID = (String) request.getSession(false).getAttribute("curTaskID");
        taskService.deleteTask(taskID);
    }


    @Data
    class GroupTaskInfo {
        private String projectName;
        private List<Task> taskList;
    }

    @Data
    class CustomTask {
        private Task task;
        private List<ProjectController.CustomUser> members;
    }

    @Data
    public static class MovedTaskInfo {
        private String source;
        private String destination;
        private String taskID;
    }

    @Data
    class ResponseMessage {
        private int resultCode;
        private String resultInMsg;
    }

}
