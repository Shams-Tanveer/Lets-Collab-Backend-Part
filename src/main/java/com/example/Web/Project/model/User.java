package com.example.Web.Project.model;

import com.example.Web.Project.customID.CustomID;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Entity(name = "user")
public class User {

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id")
    @GenericGenerator(name = "user_id",strategy = "com.example.Web.Project.customID.CustomID",parameters = {
            @org.hibernate.annotations.Parameter(name = CustomID.INCREMENT_PARAM, value = "1"),
            @org.hibernate.annotations.Parameter(name = CustomID.VALUE_PREFIX_PARAMETER, value = "user"),
            @org.hibernate.annotations.Parameter(name = CustomID.NUMBER_FORMAT_PARAMETER, value = "%05d")
    })
    private String id;


    private String emailid;

    private String name;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    private String image;

    public User(String emailid) {
        this.emailid = emailid;
    }

    private String password;

    public User(String emailid, String name, String image, String password) {
        this.emailid = emailid;
        this.name = name;
        this.image = image;
        this.password = password;
    }

    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,mappedBy = "userSet")
    private Set<Project> projectSet = new HashSet<>();


    @OneToMany(targetEntity = Task.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "assignedTo",referencedColumnName = "id")
    private Set<Task> taskSet = new HashSet<>();

    public User(String emailid, String name, String password) {
        this.emailid = emailid;
        this.name = name;
        this.password = password;
    }

    public User(String id, String emailid) {
        this.id = id;
        this.emailid = emailid;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Project> getProjectSet() {
        return projectSet;
    }

    public void setProjectSet(Set<Project> projectSet) {
        this.projectSet = projectSet;
    }

    public User() {

    }
}
