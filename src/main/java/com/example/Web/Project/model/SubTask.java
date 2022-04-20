package com.example.Web.Project.model;

import com.example.Web.Project.customID.CustomID;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "subtask")
public class SubTask {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subtask_id")
    @GenericGenerator(name = "subtask_id",strategy = "com.example.Web.Project.customID.CustomID",parameters = {
            @org.hibernate.annotations.Parameter(name = CustomID.INCREMENT_PARAM, value = "1"),
            @org.hibernate.annotations.Parameter(name = CustomID.VALUE_PREFIX_PARAMETER, value = "subTask"),
            @org.hibernate.annotations.Parameter(name = CustomID.NUMBER_FORMAT_PARAMETER, value = "%05d")
    })
    private String id;
    private String name;

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    private boolean isComplete;

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

    public String getTaskID() {
        return taskID;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    private String taskID;
}
