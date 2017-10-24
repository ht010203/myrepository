package cn.smarty.bookstore.test;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.junit.Test;

public class Demo {
	@Test
	public void fun1(){
		System.out.println(2.0-1.1);//0.8999999999999999
	}
	@Test
	public void fun2(){
		BigInteger a = BigInteger.valueOf(2);
		BigInteger b = BigInteger.valueOf(1);
		System.out.println(a.subtract(b));
	}
	@Test
	public void fun3(){
		BigInteger sum = BigInteger.valueOf(1);
		for(int i = 1;i<100;i++){
			BigInteger bi = BigInteger.valueOf(i);
			sum = sum.multiply(bi);
		}
		System.out.println(sum);
	}
	/**
	 * BigDecimal 处理二进制处理导致的误差
	 */
	@Test
	public void fun4(){
		 //创建BidDecimal 对象时 必须使用string 构造器
		BigDecimal a = new BigDecimal("2.0");
		BigDecimal b = new BigDecimal("1.2");
		System.out.println(a.subtract(b));
	}
}
