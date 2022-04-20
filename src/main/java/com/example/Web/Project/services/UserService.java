package com.example.Web.Project.services;

import com.example.Web.Project.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    public int addUser(User user);

    public void updateUser(User user);

    public User getUserByEmail(String emailid);

    public boolean checkExistByEmail(String emailid);

    public User addNoRegistered(String userEmail);

    public User getUserById(String userID);
}
