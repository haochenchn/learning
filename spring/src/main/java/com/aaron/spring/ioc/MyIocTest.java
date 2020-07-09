package com.aaron.spring.ioc;

import org.junit.Test;

/**
 * @author Aaron
 * @description
 * @date 2020/7/10
 */
public class MyIocTest {
    @Test
    public void testXmlApp() throws Exception {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("application.xml");
        User user = (User) applicationContext.getBean("user");
        System.out.println(user);
    }

    @Test
    public void testAnnotationApp() throws Exception {
        AnnotationApplicationContext app = new AnnotationApplicationContext("com.aaron.spring.ioc");
        UserService userService = (UserService) app.getBean("userServiceImpl");
        userService.add();

    }
}
