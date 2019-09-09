package com.tyx.security.service;

import com.tyx.security.dao.UserDao;
import com.tyx.security.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Create By C  2019-09-09 15:57
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public void addUser(User user){
        userDao.save(user);
    }

    public User findUserByUserName(String username) {
        return userDao.findUserByUserName(username);
    }
}
