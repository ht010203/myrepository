package cn.smarty.bookstore.order.service;

import java.sql.SQLException;
import java.util.List;

import com.sun.org.apache.regexp.internal.recompile;

import cn.itcast.jdbc.JdbcUtils;
import cn.smarty.bookstore.order.dao.OrderDao;
import cn.smarty.bookstore.order.domain.Order;

public class OrderService {
	private OrderDao orderDao = new OrderDao();
	/**
	 * 添加订单
	 * 需要处理事务
	 */
	public void add(Order order){
		try {
			//开启事务
			JdbcUtils.beginTransaction();
			orderDao.addOrder(order);
			orderDao.addOrderItemList(order.getOrderItemList());
			//提交事务
			JdbcUtils.commitTransaction();
		} catch (Exception e) {
			// 回滚事务
			try {
				JdbcUtils.rollbackTransaction();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
			}
			throw new RuntimeException(e);
		}
	}
	/**
	 * 我的订单
	 * @param uid
	 * @return
	 */
	public List<Order> myOrders(String uid){
		return orderDao.findOrderByUid( uid);
	}
	public Order load(String oid) {
		return orderDao.load(oid);
	}
	public void confirm(String oid) throws OrderException {
		//1、检验订单状态，如果不是3 抛出异常
		int state =	orderDao.getStateByOid( oid);
		if(state !=3){
			throw new OrderException("订单确认失败，请联系我们！！");
		}
		/*
		 * 2. 修改订单状态为4，表示交易成功
		 */
		orderDao.updateState(oid, 4);
	}
}
