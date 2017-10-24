package cn.smarty.bookstore.category.dao;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.sun.org.apache.bcel.internal.generic.NEW;

import cn.itcast.jdbc.TxQueryRunner;
import cn.smarty.bookstore.category.domain.Category;

public class CategoryDao {
	private QueryRunner qRunner =new TxQueryRunner ();
	public List<Category> findAll(){
		try {
			String sqlString = "select * from category";
		return	qRunner.query(sqlString, new BeanListHandler<Category> (Category.class));
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
}
