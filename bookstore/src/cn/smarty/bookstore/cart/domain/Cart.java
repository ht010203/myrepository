package cn.smarty.bookstore.cart.domain;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class Cart {
	private Map<String, CartItem> map = new LinkedHashMap<String, CartItem>();
	/**
	 * 添加条目到车中
	 * @param cartItem
	 */
	public void add(CartItem cartItem){
		if(map.containsKey(cartItem.getBook().getBid())){ //判断购物车是否存在该条目
			//如果存在该条目
		CartItem _cartltem =map.get(cartItem.getBook().getBid());
		_cartltem.setCount(_cartltem.getCount() + cartItem.getCount());
		map.put(cartItem.getBook().getBid(), _cartltem);
		}
		else{
			map.put(cartItem.getBook().getBid(), cartItem);
		}
	}
	/**
	 * 清空购物车
	 */
	public void clear (){
		map.clear();
	}
	/**
	 * 删除指定条目
	 * @param bid
	 */
	public void delete (String bid){
		map.remove(bid);
	}
	
	public double getTotal(){
		BigDecimal total = new BigDecimal("0");
		for(CartItem cartItem : map.values()){
			BigDecimal subtotal = new BigDecimal(""+ cartItem.getSubTotal());
			total = total.add(subtotal);
		}
		return total.doubleValue();
	}
	public Collection<CartItem> getCartItems(){
		return map.values();
	}
}
