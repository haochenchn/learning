package com.aaron.mybatis;

import com.aaron.mybatis.annatation.ExtInsert;
import com.aaron.mybatis.annatation.ExtParam;
import com.aaron.mybatis.annatation.ExtSelect;


public interface UserMapper {

	@ExtInsert("insert into user(userName,userAge) values(#{userName},#{userAge})")
	public int insertUser(@ExtParam("userName") String userName, @ExtParam("userAge") Integer userAge);

	@ExtSelect("select * from User where userName=#{userName} and userAge=#{userAge} ")
	User selectUser(@ExtParam("userName") String name, @ExtParam("userAge") Integer userAge);

}
