package com.example.Web.Project.services;

import com.example.Web.Project.model.User;
import com.example.Web.Project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository repository;

    @Override
    public int addUser(User user) {
        boolean isExist = repository.existsByEmail(user.getEmailid());

        System.out.println(isExist);
        if(isExist)
        {
            User user1 = repository.getUserByEmail(user.getEmailid());
            if(user1.getName().isEmpty() && user1.getPassword().isEmpty()){
                repository.save(user);
                return 1;
            }else
            {
                return  0;
            }
        }
        else
        {
           // String filename = StringUtils.cleanPath(user.getImage().get)
            repository.save(user);
            return 1;
        }

    }

    @Override
    public void updateUser(User user) {
        repository.updateUser(user.getId(),user.getName(),user.getEmailid(), user.getImage());
    }

    @Override
    public User getUserByEmail(String emailid) {
        return repository.getUserByEmail(emailid);
    }

    @Override
    public boolean checkExistByEmail(String emailid) {
        return repository.existsByEmail(emailid);
    }

    @Override
    public User addNoRegistered(String userEmail) {
        boolean isExist = repository.existsByEmail(userEmail);
        User user;
        if(!isExist)
        {
            user = new User(userEmail,"","","");
            user = repository.save(user);
        }
        else
        {
            user = repository.getUserByEmail(userEmail);
        }

        return user;
    }

    @Override
    public User getUserById(String userID) {
        return repository.getById(userID);
    }

}
