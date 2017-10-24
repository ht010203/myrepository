package cn.smarty.bookstore.user.web.servlet;

import cn.itcast.commons.CommonUtils;
import cn.itcast.mail.Mail;
import cn.itcast.mail.MailUtils;
import cn.itcast.servlet.BaseServlet;
import cn.smarty.bookstore.cart.domain.Cart;
import cn.smarty.bookstore.user.domain.User;
import cn.smarty.bookstore.user.service.UserException;
import cn.smarty.bookstore.user.service.UserService;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UserServlet
 */
public class UserServlet extends BaseServlet {
	private UserService userservice = new UserService();
	/**
	 * 注册
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public String regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/**
		 * 1、封装表单数据到form对象中
		 * 2、补全uid code
		 * 3、输入校验，保存错误信息和form对象到request域中，转发到 msg.jsp中
		 * 4、调service方法完成注册，保存错误信息和form对象到request域中，转发到msg.jsp中
		 * 5、发送邮件
		 * 6、将注册成功信息转发到msg。jsp中
		 */
		User form = CommonUtils.toBean(request.getParameterMap(), User.class);
		form.setUid(CommonUtils.uuid());
		form.setCode(CommonUtils.uuid()+CommonUtils.uuid());
		/**
		 * 输入检验，创建一个map 用来封装错误信息，其中key为表单字段名，值为错误信息
		 */
		Map<String, String> errors = new HashMap<String, String>();
		//开始校验
		String username = form.getUsername();
		if(username == null || username.trim().isEmpty()){
			errors.put("username", "用户名不能为空");
		}else if(username.length()<3 ||username.length()>10) {
			errors.put("username", "用户名字符长度必须在3-10个字符之间");
		}
		String password = form.getPassword();
		if(password == null || password.trim().isEmpty()){
			errors.put("password", "密码不能为空");
		}else if(password.length()<3 ||password.length()>10) {
			errors.put("password", "密码字符长度必须在3-10个字符之间");
		}
		String email = form.getEmail();
		if(email == null || email.trim().isEmpty()){
			errors.put("email", "邮箱不能为空");
		}else if(!email.matches("\\w+@\\w+\\.\\w+")) {
			errors.put("email", "email邮箱格式错误");
		}
		if(errors.size()>0){
			/*
			 * 保存错误信息
			 * 保存表单数据
			 * 转发到regist.jsp中
			 */
			request.setAttribute("errors", errors);
			request.setAttribute("form", form);
			return "f:/jsps/user/regist.jsp";
			
		}
		//开始注册
		try {
			userservice.regist(form);
			
		} catch (UserException e) {
			//如果异常，则保持错误信息，
			request.setAttribute("msg", e.getMessage());
			request.setAttribute("form", form);
			return "f:/jsps/user/regist.jsp";
		}
		//开始发邮件 准备配置文件
		//获取邮件配置文件内容
		Properties prop = new Properties();
		prop.load(this.getClass().getClassLoader().getResourceAsStream("email_template.properties"));
		String host = prop.getProperty("host");
		String uname = prop.getProperty("uname");
		String pwd = prop.getProperty("pwd");
		String from = prop.getProperty("from");
		String to = form.getEmail();
		String subject = prop.getProperty("subject");
		String content = prop.getProperty("content");
		content = MessageFormat.format(content, form.getCode());//替换content中的占位符
		//开始用mailUtils发邮件
		Session session =MailUtils.createSession(host, uname, pwd);
		Mail mail = new Mail(from, to, subject, content);
		try {
			MailUtils.send(session, mail);
			System.out.println("邮件已经发送！！");
		} catch (MessagingException e) {
		
			e.printStackTrace();
		}
		/**
		 * 保存成功信息，转发到msg.jsp中
		 */
		request.setAttribute("msg", "恭喜您,注册已经成功,请马上去邮箱激活");
		return "f:jsps/msg.jsp";
		
	}
	//激活功能
	public String active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String code = request.getParameter("code");
		try {
			userservice.active(code);
			request.setAttribute("msg", "恭喜，您激活成功了！请马上登录！");
		} catch (UserException e) {
			// TODO Auto-generated catch block
			request.setAttribute("msg", e.getMessage());
		}
		return "f:/jsps/msg.jsp";
	}
	public String login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User form = CommonUtils.toBean(request.getParameterMap(), User.class);
		try {
		User user =userservice.login(form);
		request.getSession().setAttribute("session_user", user);
		/**
		 * 登录成功之后添加要给购物车，即向session中保存一个cart对象
		 */
		request.getSession().setAttribute("cart",new Cart());
		return "r:/index.jsp";
		} catch (UserException e) {
			request.setAttribute("msg", e.getMessage());
			request.setAttribute("form", form);
			return "f:jsps/user/login.jsp";
		}
		
	}
	/**
	 * 退出
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String quit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession().invalidate();
		return "r:/index.jsp";
		
	}
}
