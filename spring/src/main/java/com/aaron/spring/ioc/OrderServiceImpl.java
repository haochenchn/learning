package com.aaron.spring.ioc;

import com.aaron.spring.ioc.annotation.ExtService;

@ExtService
public class OrderServiceImpl implements OrderService {

	@Override
	public void addOrder() {
		System.out.println("addOrder");
	}

}
