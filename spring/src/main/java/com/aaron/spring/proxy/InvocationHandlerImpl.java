package com.aaron.spring.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

// 每次生成动态代理类对象时,实现了InvocationHandler接口的调用处理器对象 
public class InvocationHandlerImpl implements InvocationHandler {
	private Object target;// 这其实业务实现类对象，用来调用具体的业务方法
	// 通过构造函数传入目标对象

	public InvocationHandlerImpl(Object target) {
		this.target = target;
	}

	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Object result = null;
		System.out.println("使用jdk动态代理 开启事务");
		result = method.invoke(target, args);
		System.out.println("使用jdk动态代理 提交事务");
		return result;
	}

	public static void main(String[] args) throws NoSuchMethodException, SecurityException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		// 被代理对象
		 UserService userService = new UserServiceImpl();
		InvocationHandlerImpl invocationHandlerImpl = new InvocationHandlerImpl(userService);
		ClassLoader loader = userService.getClass().getClassLoader();
		Class<?>[] interfaces = userService.getClass().getInterfaces();
		// 主要装载器、一组接口及调用处理动态代理实例
		UserService newProxyInstance = (UserService) Proxy.newProxyInstance(loader, interfaces,
				invocationHandlerImpl);
		newProxyInstance.add();
	}

}
