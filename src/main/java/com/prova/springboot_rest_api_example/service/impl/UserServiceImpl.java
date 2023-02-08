package com.prova.springboot_rest_api_example.service.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import com.prova.springboot_rest_api_example.dao.UserDAO;
import com.prova.springboot_rest_api_example.exception.NotFoundException;
import com.prova.springboot_rest_api_example.exception.UserException;
import com.prova.springboot_rest_api_example.model.Filter;
import com.prova.springboot_rest_api_example.model.User;
import com.prova.springboot_rest_api_example.service.UserService;
import com.prova.springboot_rest_api_example.validation.UserSaveValidationEngine;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	private final UserDAO userDAO;
	private final UserSaveValidationEngine userSaveValidationEngine;
	
	public UserServiceImpl(
			final UserDAO userDAO,
			final UserSaveValidationEngine userSaveValidationEngine) {
		this.userDAO = userDAO;
		this.userSaveValidationEngine = userSaveValidationEngine;
	}
	
	@Override
	public List<User> searchFilteredUsers(Filter filter) {
		return this.userDAO.searchFilteredUsers(filter);
	}
	
	@Override
	public Long insert(User user) {
		this.userSaveValidationEngine.validate(user);
		return this.userDAO.insert(user);
	}
	
	@Override
	public void update(User user) {
		this.userDAO.findById(user.getId()).orElseThrow(() -> new NotFoundException(User.class, user.getId()));
		this.userSaveValidationEngine.validate(user);
		this.userDAO.update(user);
	}
	
	@Override
	public void delete(long userId) {
		this.userDAO.delete(userId);
	}
	
	@Override
	public void importUsers(byte[] file, String filename) {
		if (file.length == 0) {
			throw new IllegalArgumentException("Cannot import an empty file");
		}
		
		if (!FilenameUtils.getExtension(filename).equalsIgnoreCase("csv")) {
			throw new UserException("Invalid file extension");
		}
		
		List<User> usersToImport = new ArrayList<>();
		int lineNumber = 0;
		CSVParser parser = new CSVParserBuilder()
			    .withSeparator(';')
			    .withIgnoreQuotations(true)
			    .build();
		try (InputStreamReader is = new InputStreamReader(new ByteArrayInputStream(file), StandardCharsets.UTF_8); 
				CSVReader reader = new CSVReaderBuilder(is).withSkipLines(0).withCSVParser(parser).build();
			) {
			
			if (!checkCorrectHeader(reader)) {
				throw new UserException("Invalid file header");
			}
			
			for (String[] line : reader) {
				validateFileLine(line, lineNumber, usersToImport);
				lineNumber++;
			}
			
		} catch (IOException e) {
			throw new IllegalStateException(e.getMessage());
		} catch (CsvException csve) {
			throw new UserException("Invalid file");
		}
		
		usersToImport.forEach(user -> this.userDAO.insert(user));
	}
	
	private boolean checkCorrectHeader(CSVReader reader) throws IOException, CsvException {
		List<String> headerModel = buildUserTemplate();
		List<String> fileHeader = List.of(reader.readNext());
		return headerModel.containsAll(fileHeader);
	}

	private void validateFileLine(String[] line, int lineNumber, List<User> usersToImport) {
		User user = buildUserFromFile(line);
		this.userSaveValidationEngine.validate(user, lineNumber);
		usersToImport.add(user);
	}

	private List<String> buildUserTemplate() {
		List<String> header = new ArrayList<>();
		header.add("Name");
		header.add("Surname");
		header.add("Email");
		header.add("Address");
		
		return header;
	}
	
	private User buildUserFromFile(String[] line) {
		int columnIndex = 0;
		User user = new User();
		user.setName(line[columnIndex++].trim());
		user.setSurname(line[columnIndex++].trim());
		user.setEmail(line[columnIndex++].trim());
		user.setAddress(line[columnIndex++]);

		return user;
	}

}
