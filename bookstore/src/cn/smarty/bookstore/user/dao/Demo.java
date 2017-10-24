package cn.smarty.bookstore.user.dao;

import java.io.IOException;
import java.sql.SQLException;

import javax.mail.MessagingException;
import javax.mail.Session;

import org.apache.commons.dbutils.QueryRunner;
import org.junit.Test;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import cn.itcast.jdbc.JdbcUtils;
import cn.itcast.mail.Mail;
import cn.itcast.mail.MailUtils;

public class Demo {
	@Test
	public void fun2() throws Exception{
		Session session =	MailUtils.createSession("smtp.ym.163.com", "admin@smarty.space", "1052661752ht");
		Mail mail = new Mail("admin@smarty.space", "1052661752@qq.com", "test", "这是一个测试邮件");
		MailUtils.send(session, mail);
	}
}
