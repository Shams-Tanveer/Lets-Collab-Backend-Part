package com.example.Web.Project.model;

import com.example.Web.Project.customID.CustomID;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.xml.crypto.Data;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_id")
    @GenericGenerator(name = "task_id",strategy = "com.example.Web.Project.customID.CustomID",parameters = {
            @org.hibernate.annotations.Parameter(name = CustomID.INCREMENT_PARAM, value = "1"),
            @org.hibernate.annotations.Parameter(name = CustomID.VALUE_PREFIX_PARAMETER, value = "task"),
            @org.hibernate.annotations.Parameter(name = CustomID.NUMBER_FORMAT_PARAMETER, value = "%05d")
    })
    private String id;
    private String name;
    private String type;
    private boolean isComplete;
    private int totalsubtask;
    private int completeIn;
    private int completedsubtask;
    private String projectID;
    private String assignedTo;
    private Date dueDate;

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    @JsonIgnore
    @OneToMany(targetEntity = SubTask.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "taskID",referencedColumnName = "id")
    private Set<SubTask> subTaskSet = new HashSet<>();

    public Set<SubTask> getSubTaskSet() {
        return subTaskSet;
    }

    public void setSubTaskSet(Set<SubTask> subTaskSet) {
        this.subTaskSet = subTaskSet;
    }

    public int getCompleteIn() {
        return completeIn;
    }

    public void setCompleteIn(int completeIn) {
        this.completeIn = completeIn;
    }

    public String getProjectID() {
        return projectID;
    }

    public void setProjectID(String projectID) {
        this.projectID = projectID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public int getTotalsubtask() {
        return totalsubtask;
    }

    public void setTotalsubtask(int totalsubtask) {
        this.totalsubtask = totalsubtask;
    }

    public int getCompletedsubtask() {
        return completedsubtask;
    }

    public void setCompletedsubtask(int completedsubtask) {
        this.completedsubtask = completedsubtask;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }
}
