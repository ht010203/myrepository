package cn.smarty.bookstore.category.service;

import java.util.List;

import cn.smarty.bookstore.category.dao.CategoryDao;
import cn.smarty.bookstore.category.domain.Category;

public class CategoryService {
	private CategoryDao categorydao = new CategoryDao();
	/**
	 * 
	 * @return
	 */
	public List<Category> findAll(){
		return categorydao.findAll();
	}
}
