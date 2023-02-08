package com.prova.springboot_rest_api_example.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.opencsv.exceptions.CsvValidationException;
import com.prova.springboot_rest_api_example.dto.FilterDTO;
import com.prova.springboot_rest_api_example.dto.UserDTO;
import com.prova.springboot_rest_api_example.exception.UserException;
import com.prova.springboot_rest_api_example.mapper.FilterMapper;
import com.prova.springboot_rest_api_example.mapper.UserMapper;
import com.prova.springboot_rest_api_example.model.User;
import com.prova.springboot_rest_api_example.service.UserService;

@RestController
@RequestMapping("/users")
public class UsersController {

	private final UserService userService;
	private final UserMapper userMapper;
	private final FilterMapper filterMapper;

	public UsersController(
			final UserService userService,
			final UserMapper userMapper,
			final FilterMapper filterMapper) {
		this.userService = userService;
		this.userMapper = userMapper;
		this.filterMapper = filterMapper;
	}

	@PostMapping("/search")
    public List<UserDTO> search(@RequestBody FilterDTO filterDTO) {
		return this.userMapper.toDTOs(userService.searchFilteredUsers(this.filterMapper.toModel(filterDTO)));			
	}
	
	@PostMapping
    public Long save(@RequestBody UserDTO userDTO) {
		try {
			User user = this.userMapper.toModel(userDTO);
			if (user.getId() == null) {
				return this.userService.insert(user);
			} else {
				this.userService.update(user);
				return user.getId();
			}
		} catch (UserException exc) {
	         throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error saving User: " + exc.getMessage(), exc);
		}
	}
	
	@DeleteMapping("/{userId}")
    public void delete(@PathVariable(value = "userId", required = true) long userId) {
		this.userService.delete(userId);
	}
	
	@PostMapping("import")
    public void importUsers(@RequestPart("file") MultipartFile file) throws CsvValidationException, IOException {
		try {
			this.userService.importUsers(file.getBytes(), file.getOriginalFilename());
		} catch (UserException | CsvValidationException | IOException exc) {
	         throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error Importing User: " + exc.getMessage(), exc);
		}
	}
	
	
}
