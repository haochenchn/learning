package com.aaron.spring.proxy;

/**
 * 静态代理设计模式
 */
public class UserServiceStaticProxy {
	private UserService userService;

	public UserServiceStaticProxy(UserService userService) {
		this.userService = userService;
	}

	public void add() {
		System.out.println("静态代理 开启事务");
		userService.add();
		System.out.println("静态代理  提交事务");
	}

}
