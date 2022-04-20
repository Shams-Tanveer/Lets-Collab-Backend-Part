package com.example.Web.Project.repository;

import com.example.Web.Project.model.VideoCall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoCallRepository extends JpaRepository<VideoCall,String> {
    @Query("SELECT CASE WHEN count(e) > 0 THEN true ELSE false END FROM videocall e where e.id= ?1")
    public boolean existsByVCallID(String vCallID);

}
