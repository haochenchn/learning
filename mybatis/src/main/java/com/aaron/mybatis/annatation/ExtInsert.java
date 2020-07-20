package com.aaron.mybatis.annatation;

import java.lang.annotation.*;

/**
 * 自定义插入注解 <br>
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ExtInsert {
	String value();
}
