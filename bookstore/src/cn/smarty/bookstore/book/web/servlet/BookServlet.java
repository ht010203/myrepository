package cn.smarty.bookstore.book.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.servlet.BaseServlet;
import cn.smarty.bookstore.book.service.BookService;

public class BookServlet extends BaseServlet {
	private BookService bookService = new BookService();
	public String findAll(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("booklist", bookService.findAll());
		return "f:jsps/book/list.jsp";
	}
	public String findByCategory(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String cid = request.getParameter("cid");
		request.setAttribute("booklist", bookService.findByCategory(cid));
		return "f:jsps/book/list.jsp";
	}
	public String load(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("book", bookService.load(request.getParameter("bid")));
		return "f:jsps/book/desc.jsp";
	}
}
