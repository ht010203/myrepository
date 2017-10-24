package cn.smarty.bookstore.order.web.Servlet;

import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;
import cn.smarty.bookstore.cart.domain.Cart;
import cn.smarty.bookstore.cart.domain.CartItem;
import cn.smarty.bookstore.order.domain.Order;
import cn.smarty.bookstore.order.domain.OrderItem;
import cn.smarty.bookstore.order.service.OrderException;
import cn.smarty.bookstore.order.service.OrderService;
import cn.smarty.bookstore.user.domain.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class OrderServlet extends BaseServlet {
	private OrderService orderService = new OrderService();
	/**添加订单
	 * 
	 */
	public String add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		 * 1、从session中拿到车
		 * 2、使用cart 生成order对象
		 * 3、调函数 service中add方法完成添加订单
		 * 4、保存order对象到request域中
		 */
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		//创建订单对象
		Order order = new Order();
		order.setOid(CommonUtils.uuid());
		order.setOrdertime(new Date());
		order.setState(1);
		order.setOwner((User)request.getSession().getAttribute("session_user"));
		order.setTotal(cart.getTotal());
		//创建订单条目集合
		List<OrderItem> orderItemlist = new ArrayList<OrderItem>();
		//循环遍历cart中所有的cartItem 使用每一个cartItem对象创建一个OrderItem对象 并切添加到集合中
		for(CartItem cartItem : cart.getCartItems()){
			OrderItem oi = new OrderItem();
			oi.setIid(CommonUtils.uuid());
			oi.setCount(cartItem.getCount());
			oi.setBook(cartItem.getBook());
			oi.setSubtotal(cartItem.getSubTotal());
			oi.setOrder(order);
			
			orderItemlist.add(oi);
		}
		//吧所有的订单条目添加到订单中
		order.setOrderItemList(orderItemlist);
		//清空购物车
		cart.clear();
		//调用orderService 添加订单
		orderService.add(order);
		//保存order到request域中
		request.setAttribute("order", order);
		return "/jsps/order/desc.jsp";
	}
	/*
	 * 我的订单 
	 */
	public String myOrders(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/**
		 *  从session中获取当前用户，再获取uid
		 *   1. 从session得到当前用户，再获取其uid
		 * 2. 使用uid调用orderService#myOrders(uid)得到该用户的所有订单List<Order>
		 * 3. 把订单列表保存到request域中，转发到/jsps/order/list.jsp
		 */
		User user = (User) request.getSession().getAttribute("session_user");
		List<Order> orderList = orderService.myOrders(user.getUid());
		request.setAttribute("orderList", orderList);
		
		return  "f:/jsps/order/list.jsp";
		
	}
	/**
	 * 加载某一订单项
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String load(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String oid = request.getParameter("oid") ;
		Order order =orderService.load(oid);
		request.setAttribute("order", order) ;
		return "f:/jsps/order/desc.jsp";
	}
	/**
	 * 确认收货
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String confirm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String oid = request.getParameter("oid") ;
		try {
			orderService.confirm(oid);
			request.setAttribute("msg", "恭喜，交易成功！");
		} catch (OrderException e) {
			request.setAttribute("msg", e.getMessage());
		}
		return "f:/jsps/msg.jsp";
	}
}
