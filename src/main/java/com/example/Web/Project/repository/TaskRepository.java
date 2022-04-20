package com.example.Web.Project.repository;

import com.example.Web.Project.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task,String> {

    @Query(value = "SELECT * FROM task WHERE projectid= ?1",nativeQuery = true)
    public List<Task> getTaskByProjectID(String projectID);

    @Modifying
    @Transactional
    @Query(value = "UPDATE task set type=?1 where id=?2",nativeQuery = true)
    public void updateTaskType(String type,String taskID);

    @Modifying
    @Transactional
    @Query(value = "UPDATE task_id set next_val = next_val-1",nativeQuery = true)
    public void updateSequence();

    @Modifying
    @Transactional
    @Query(value = "UPDATE task set completedsubtask=completedsubtask + ?1 where id=?2",nativeQuery = true)
    public void updateCompleteSubTask(int value,String taskID);

    @Modifying
    @Transactional
    @Query(value = "UPDATE task set totalsubtask=totalsubtask + ?1 where id=?2",nativeQuery = true)
    public void updateTotalSubTask(int value,String taskID);

    @Modifying
    @Transactional
    @Query(value = "UPDATE task set is_complete = true where id=?1",nativeQuery = true)
    public void updateTaskComplete(String taskID);


    @Query(value = "SELECT * FROM task WHERE assigned_to=?1 and projectid= ?2",nativeQuery = true)
    public List<Task> getIndividualTask(String userID,String projectID);
}
