package cn.smarty.bookstore.order.domain;

import java.util.Date;
import java.util.List;

import cn.smarty.bookstore.user.domain.User;

public class Order {
	private String oid; //订单id
	private Date ordertime; //订单时间
	private double total ; //合计
	private int state ;//订单有四种状态，1未发货，2、已付款未发货3、已发货未确认收货 4、交易成功
	private User owner;
	private String address;
	
	//当前订单下的所有条目
	private List<OrderItem> orderItemList ;
	
	public List<OrderItem> getOrderItemList() {
		return orderItemList;
	}
	public void setOrderItemList(List<OrderItem> orderItemList) {
		this.orderItemList = orderItemList;
	}
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public Date getOrdertime() {
		return ordertime;
	}
	public void setOrdertime(Date ordertime) {
		this.ordertime = ordertime;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public User getOwner() {
		return owner;
	}
	public void setOwner(User owner) {
		this.owner = owner;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Order() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Order [oid=" + oid + ", ordertime=" + ordertime + ", total="
				+ total + ", state=" + state + ", owner=" + owner
				+ ", address=" + address + "]";
	}
	
}
