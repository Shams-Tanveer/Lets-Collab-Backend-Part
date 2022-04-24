package com.example.Web.Project.repository;

import com.example.Web.Project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,String> {

    @Query("SELECT u FROM user u WHERE u.emailid=?1")
    public User getUserByEmail(String emailid);

    @Query("SELECT CASE WHEN count(e) > 0 THEN true ELSE false END FROM user e where e.emailid= ?1")
    public boolean existsByEmail(String emailid);

    @Query(value = "SELECT u.id FROM user u where u.emailid=?1",nativeQuery = true)
    public String getUserCertainInfoByEmail(String emailid);

    @Modifying
    @Transactional
    @Query(value = "UPDATE user set name=?2, emailid=?3,image=?4 where id=?1",nativeQuery = true)
    public void updateUser(String taskID,String name,String emailid,String image);

    @Modifying
    @Transactional
    @Query(value = "UPDATE user set name=?2, password=?3,image=?4 where id=?1",nativeQuery = true)
    public void registerAssignedUser(String taskID,String name,String password,String image);
}
