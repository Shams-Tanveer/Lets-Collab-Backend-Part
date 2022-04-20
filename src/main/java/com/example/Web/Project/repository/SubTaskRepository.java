package com.example.Web.Project.repository;

import com.example.Web.Project.model.SubTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface SubTaskRepository extends JpaRepository<SubTask,String> {

    @Query(value = "select * from subtask where taskid = ?1",nativeQuery = true)
    public List<SubTask> getSubTaskByTaskID(String taskID);

    @Modifying
    @Transactional
    @Query(value = "UPDATE subtask set name = ?2, is_complete= ?3 where id=?1",nativeQuery = true)
    public void updateSubTaskName(String subTaskID,String subTaskName,boolean isComplete);

    @Modifying
    @Transactional
    @Query(value = "UPDATE subtask_id set next_val = next_val-?1",nativeQuery = true)
    public void updateSequence(int value);
}
