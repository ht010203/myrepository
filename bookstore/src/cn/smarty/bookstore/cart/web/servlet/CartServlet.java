package cn.smarty.bookstore.cart.web.servlet;

import cn.itcast.servlet.BaseServlet;
import cn.smarty.bookstore.book.domain.Book;
import cn.smarty.bookstore.book.service.BookService;
import cn.smarty.bookstore.cart.domain.Cart;
import cn.smarty.bookstore.cart.domain.CartItem;

import java.io.IOException;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CartServlet
 */
public class CartServlet extends BaseServlet  {

	/**
	 * 添加购物条目
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1、等到车2、得到条目（图书和数量）3、把条目添加到车中
		//得到车
		Cart cart =	(Cart) request.getSession().getAttribute("cart");
		/**
		 * 表单中传递的只有bid和数量
		 * 2、得到条目
		 * >得到图书个数量
		 * 先得到图书的bid,然后我们需要通过bid查询数据库得到Book
		 * 图书数量表单中有
		 */
		String bid = request.getParameter("bid");
		Book book =	new BookService().load(bid);
		int count =Integer.parseInt(request.getParameter("count"));
		//得到条目
		 CartItem cartItem = new CartItem();
		 cartItem.setBook(book);
		 cartItem.setCount(count);
		 //将条目添加到车中
		 cart.add(cartItem);
		 return "f:jsps/cart/list.jsp";
	}
	/**
	 * 清除购物条目
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String clear(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Cart cart =	(Cart) request.getSession().getAttribute("cart");
		cart.clear();
		return "f:jsps/cart/list.jsp";
	}
	/**
	 * 删除购物条目
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Cart cart =	(Cart) request.getSession().getAttribute("cart");
		String bid = request.getParameter("bid");
		cart.delete(bid);
		return "f:jsps/cart/list.jsp";
	}



}
