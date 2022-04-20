package com.example.Web.Project.repository;

import com.example.Web.Project.model.Project;
import com.example.Web.Project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project,String> {


    @Query(value = "SELECT * FROM project u where id in (select project_id from user_project where user_id=?1) and is_done = 0",nativeQuery = true)
    public List<Project> getUserProject(String projectID);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO user_project(project_id,user_id) VALUES(?1,?2)",nativeQuery = true)
    public void addMember(String projectID,String emailID);

    @Query(value = "SELECT u.emailid FROM user u where id in (select user_id from user_project where project_id=?1)",nativeQuery = true)
    public List<String> projectMembers(String projectID);

    @Modifying
    @Transactional
    @Query(value = "UPDATE project set is_done = true where id=?1",nativeQuery = true)
    public void updateProjectComplete(String projectID);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM user_project WHERE project_id=?1 and user_id=?2",nativeQuery = true)
    public void deleteMember(String projectID,String userID);
}
