package com.prova.springboot_rest_api_example.dao;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.prova.springboot_rest_api_example.model.Filter;
import com.prova.springboot_rest_api_example.model.User;

@Mapper
public interface UserDAO {

	List<User> searchFilteredUsers(@Param("filter") Filter filter);
	
	Optional<User> findById(@Param("userId") long userId);
	
	Long insert(@Param("user") User user);
	
	void update(@Param("user") User user);
	
	void delete(@Param("userId") long userId);
	
}
