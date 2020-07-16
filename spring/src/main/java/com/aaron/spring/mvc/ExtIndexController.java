package com.aaron.spring.mvc;

import com.aaron.spring.mvc.myannotation.MyController;
import com.aaron.spring.mvc.myannotation.MyRequestMapping;

@MyController
@MyRequestMapping("/ext")
public class ExtIndexController {
	//ext/test/?name=122&age=6440654
	@MyRequestMapping("/test")
	public String test() {
		System.out.println("手写springmvc框架...");
		return "index";
	}

}
