package cn.smarty.bookstore.user.service;

import cn.smarty.bookstore.user.dao.UserDao;
import cn.smarty.bookstore.user.domain.User;

public class UserService {
	private UserDao userdao = new UserDao();
	/**
	 * 注册功能
	 * @throws UserException 
	 */
	public void regist(User form) throws UserException{
		//检验用户名
		User user = userdao.findByUsername(form.getUsername());
		if(user != null){
			throw new UserException("用户名已经注册");
		}
		//检验邮箱
		user = userdao.findByEmail(form.getEmail());
		if(user !=null){
			throw new UserException("邮箱已经被注册");
		}
		userdao.add(form);
	}
	public void active (String code) throws UserException{
		/*
		 * 通过这个code查询人所在的记录
		 */
		User user =	userdao.findByCode(code);
		if(user == null){
			throw new UserException("激活码错误！！");
		}
		if(user.isState()){
			throw new UserException("您已经激活过了，请不要重复激活！！");
		}
		userdao.updateState(user.getUid(), true);
	}
	/**
	 * 登录功能
	 * @throws UserException 
	 */
	public User login(User form) throws UserException{
		//使用username 查询得到user对象 检测用户信息
		User user = userdao.findByUsername(form.getUsername());
		if(user == null){
			throw new UserException("用户名不存在！！");
		}
		if(!user.getPassword().equals(form.getPassword())){
			throw new UserException("密码错误！！");
		}
		if(!user.isState()){
			throw new UserException("您的账号未激活！！");
		}
		return user;
	}
}	
