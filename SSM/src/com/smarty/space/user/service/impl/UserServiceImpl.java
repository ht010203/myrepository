package com.smarty.space.user.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.smarty.space.user.mapper.UserMapper;
import com.smarty.space.user.model.User;
import com.smarty.space.user.service.dao.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Resource
	private UserMapper userMapper ;
	@Override
	public User getUser(int id) throws Exception {
		// TODO Auto-generated method stub
		return userMapper.getUser(id);
	}

}
