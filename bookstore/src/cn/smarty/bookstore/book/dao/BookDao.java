package cn.smarty.bookstore.book.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import cn.itcast.jdbc.TxQueryRunner;
import cn.smarty.bookstore.book.domain.Book;

public class BookDao {
	private QueryRunner qr = new TxQueryRunner();
	/**
	 * 查询所有的图书
	 * @return
	 */
	public List<Book> findAll(){
		try {
			String sql = "select * from book ";
			return qr.query(sql, new BeanListHandler<Book>(Book.class));
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public List<Book> findByCategory (String cid){
		try {
			String sql = "select * from book where cid = ?";
			return qr.query(sql, new BeanListHandler<Book> (Book.class), cid);
		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}
	public Book findByBid (String bid){
		try {
			String sql = "select * from book where bid = ?";
			return qr.query(sql, new BeanHandler<Book> (Book.class), bid);
		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}
}
