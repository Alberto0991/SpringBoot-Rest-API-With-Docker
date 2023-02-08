package com.prova.springboot_rest_api_example.validation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.prova.springboot_rest_api_example.exception.UserException;
import com.prova.springboot_rest_api_example.model.User;

@Component
public class UserSaveValidationEngine {

	public static final String MAIL_REGEX = "^(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
	
	public void validate(User user, Integer... lineNumber) {

		String errorOnSave = "";
		String errorOnImport = "";
		if (lineNumber != null && lineNumber.length > 0) {
			errorOnImport = " line " + lineNumber[0];
		}
		if (StringUtils.isEmpty(user.getName())) {
			errorOnSave = "name cannot be empty;";
		}
		
		if (StringUtils.isEmpty(user.getSurname())) {
			errorOnSave += "surname cannot be empty;";
		}
		
		if (StringUtils.isEmpty(user.getAddress())) {
			errorOnSave += "address cannot be empty;";
		}
		
		if (StringUtils.isNotEmpty(user.getEmail()) && !user.getEmail().matches(MAIL_REGEX)) {
			errorOnSave += "email not valid;";
		}
		
		if (!StringUtils.isEmpty(errorOnSave)) {
			throw new UserException(errorOnSave + errorOnImport);
		}

	}
}
