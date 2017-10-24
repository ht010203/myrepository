package com.smarty.space.user.mapper;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.smarty.space.user.model.User;

public class UserMapperTest {
	//private  SqlSessionFactory sessionFactory ;
	@Before
	//public void setUp() throws Exception {
	//	ApplicationContext context = new ClassPathXmlApplicationContext("spring-mybatis.xml");
	//	sessionFactory = (SqlSessionFactory)context.getBean("sqlSessionFactory");
		
	//}

	@Test
	public void test() throws SQLException {
	//SqlSession sqlSession = sessionFactory.openSession();
	//UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
	//User sUser =userMapper.getUser(1);
	//System.out.println(sUser);
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-mybatis.xml");
		BasicDataSource dataSource = (BasicDataSource)context.getBean("dataSource");
		System.out.println(dataSource.getConnection());
	}

}
