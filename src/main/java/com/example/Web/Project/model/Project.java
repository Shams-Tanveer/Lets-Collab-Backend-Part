package com.example.Web.Project.model;

import com.example.Web.Project.customID.CustomID;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "project")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "project_id")
    @GenericGenerator(name = "project_id",strategy = "com.example.Web.Project.customID.CustomID",parameters = {
            @org.hibernate.annotations.Parameter(name = CustomID.INCREMENT_PARAM, value = "1"),
            @org.hibernate.annotations.Parameter(name = CustomID.VALUE_PREFIX_PARAMETER, value = "project"),
            @org.hibernate.annotations.Parameter(name = CustomID.NUMBER_FORMAT_PARAMETER, value = "%05d")
    })
    private String id;
    private String projectName;
    private String projectAdmin;
    private boolean isDone;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectAdmin() {
        return projectAdmin;
    }

    public void setProjectAdmin(String projectAdmin) {
        this.projectAdmin = projectAdmin;
    }

    public Set<User> getUserSet() {
        return userSet;
    }

    public void setUserSet(Set<User> userSet) {
        this.userSet = userSet;
    }

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinTable(name = "user_project",
            joinColumns = {@JoinColumn(name = "project_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    private Set<User> userSet = new HashSet<>();


    public Set<Task> getTaskSet() {
        return taskSet;
    }

    public void setTaskSet(Set<Task> taskSet) {
        this.taskSet = taskSet;
    }

    @JsonIgnore
    @OneToMany(targetEntity = Task.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "projectID",referencedColumnName = "id")
    private Set<Task> taskSet = new HashSet<>();



    public Project(String projectName, String projectAdmin) {
        this.projectName = projectName;
        this.projectAdmin = projectAdmin;
    }

    public Project() {

    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }
}
