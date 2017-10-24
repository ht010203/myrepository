package cn.smarty.bookstore.order.dao;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;
import cn.smarty.bookstore.book.domain.Book;
import cn.smarty.bookstore.order.domain.Order;
import cn.smarty.bookstore.order.domain.OrderItem;

public class OrderDao {
	private QueryRunner qr = new TxQueryRunner();
	/*
	 * 添加订单
	 */
	public void addOrder(Order order){
		try {
			/*
			 * 由于数据库中ordertime保存的类型是datetime 
			 * 而我们order.getOrdertime得到是Date,
			 * 所以需要转换一下
			 */
			Timestamp timestamp = new Timestamp(order.getOrdertime().getTime());
			String sql = "insert into orders values(?,?,?,?,?,?)";
			Object [] params = {order.getOid(),timestamp,order.getTotal(),
					order.getState(),order.getOwner().getUid(),order.getAddress()};
			qr.update(sql, params);
		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}
	/**
	 * 插入订单条目
	 * @param orderItemlist
	 */
	public void addOrderItemList(List<OrderItem> orderItemlist){
		try {
			String sql = "insert into orderitem values (?,?,?,?,?)";
			/**
			 * 把orderitemlist转换为二维数如 params[1][2] = orderitem
			 */
			Object [][] params = new Object [orderItemlist.size()][];
			//循环遍历orderitemlist 使用每一个orderlist为params中的值
			for(int i =0 ; i<orderItemlist.size();i++){
				OrderItem item = orderItemlist.get(i);
				params[i] = new Object[]{item.getIid(),item.getCount(),item.getSubtotal(),
						item.getOrder().getOid(),item.getBook().getBid()
				};
			}
			qr.batch(sql, params);
		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}
	/**
	 * 按照uid查询订单
	 * @param uid
	 * @return
	 */
	public List<Order> findOrderByUid(String uid){
		/**
		 * 通过uid查询当前用户的所有orders 
		 * 循环遍历每一个orders 加载每一个orders的订单条目
		 */
		try {
		//获取当前用户的所有订单
			String sql = "select * from orders where uid =?";
		List<Order> orderList = qr.query(sql, new BeanListHandler<Order>(Order.class), uid);
		//循环遍历每一个订单， 为其加载自己的每一个订单条目
		for (Order order : orderList) {
			loadOrderItems(order); //为order对象添加它所有的订单条目项
			}
		return orderList;
		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}
	/**
	 * 加载指定订单项的订单条目
	 * @param order
	 * @throws SQLException 
	 */
	private void loadOrderItems(Order order) throws SQLException {
		/*
		 * 因为一行结果集对应的不再是一个javabean，没有javabean对象与之对应，所以不能再使用BeanListHandler，而是MapListHandler
		 */
		//String  sql = "select * from orderitem where oid = ?";
		//return qr.query(sql, new BeanListHandler<OrderItem>(OrderItem.class), order.getOid())
		/**
		 * 查询两张表，进行关联查询
		 */
		String sql = "select * from orderitem i ,book b where i.bid = b.bid and oid = ?";
		List<Map<String, Object>> mapList =	qr.query(sql, new MapListHandler(), order.getOid());
		/**
		 * maplist是多个map 每个map对应一个结果集
		 * 一个map 如{iid=C7AD5492F27D492189105FB50E55CBB6, count=2, subtotal=60.0, oid=1AE8A70354C947F8B81B80ADA6783155, bid=7, bname=精通Hibernate,price=30.0, author=张卫琴, image=book_img/8991366-1_l.jpg, cid=2}
		 * . * 
		 * 我们需要使用一个Map生成两个对象：OrderItem、Book，然后再建立两者的关系（把Book设置给OrderItem）
		 */
		/*
		 * 循环遍历每个Map，使用map生成两个对象，然后建立关系（最终结果一个OrderItem），把OrderItem保存起来
		 */
		List<OrderItem> orderItemList = toOrderItemList(mapList);
		order.setOrderItemList(orderItemList);
	}
	/**
	 * 把mapList中每个Map转换成两个对象，并建立关系
	 * @param mapList
	 * @return
	 */
	private List<OrderItem> toOrderItemList(List<Map<String, Object>> mapList) {
		List<OrderItem> orderItemList = new ArrayList<OrderItem>() ;
		for ( Map<String, Object> map : mapList) {
			OrderItem orderItem = toOrderItem(map);
			orderItemList.add(orderItem);
		}
		return orderItemList;
	}
	/**
	 * 把一个Map转换成一个OrderItem对象
	 * @param map
	 * @return
	 */
	private OrderItem toOrderItem(Map<String, Object> map) {
		OrderItem orderItem = CommonUtils.toBean(map, OrderItem.class);
		Book book = CommonUtils.toBean(map, Book.class);
		orderItem.setBook(book);
		return orderItem;
	}
	/**
	 * 加载订单
	 * @param oid
	 * @return
	 */
	public Order load(String oid) {
		try {
			//得到当前的订单
			String sql = "select * from orders where oid = ?" ;
			Order order =qr.query(sql, new BeanHandler<Order> (Order.class), oid);
			//加载当前订单的订单条目
			loadOrderItems(order);
			return order;
		} catch (SQLException e) {
			throw new RuntimeException();
		}
		
	}
	public int getStateByOid(String oid) {
		try {
			String sql = "select state from orders where oid = ?";
			return (Integer)qr.query(sql, new ScalarHandler(), oid);
		} catch (SQLException e) {
			 throw new RuntimeException();
		}
		
	}
	public void updateState(String oid, int i) {
		try {
			String sql = "update orders set state = ? where oid =?";
			 qr.update(sql,i,oid);
		} catch (SQLException e) {
			throw new RuntimeException();
		}
		
	}
	
}
