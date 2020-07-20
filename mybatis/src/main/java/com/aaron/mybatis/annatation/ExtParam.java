package com.aaron.mybatis.annatation;

import java.lang.annotation.*;

/**
 * 自定义参数注解<br>
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface ExtParam {
	String value();
}
