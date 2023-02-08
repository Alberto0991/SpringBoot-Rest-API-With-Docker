package com.prova.springboot_rest_api_example.service;

import java.util.List;

import com.opencsv.exceptions.CsvValidationException;
import com.prova.springboot_rest_api_example.model.Filter;
import com.prova.springboot_rest_api_example.model.User;

public interface UserService {

	List<User> searchFilteredUsers(Filter filter);

	Long insert(User user);

	void update(User user);

	void delete(long userId);
	
	void importUsers(byte[] file, String filename) throws CsvValidationException;

}
