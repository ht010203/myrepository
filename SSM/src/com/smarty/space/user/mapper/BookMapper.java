package com.smarty.space.user.mapper;

import java.util.List;

import com.smarty.space.user.model.Book;

public interface BookMapper {
	
	public Book queryById (Long id) throws Exception;
	
	public List<Book> queryAll() throws Exception;
	
	
}
