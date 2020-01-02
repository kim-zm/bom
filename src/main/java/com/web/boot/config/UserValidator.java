package com.web.boot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.web.boot.domain.User;
import com.web.boot.repository.UserRepository;

@Component
public class UserValidator implements Validator {
    @Autowired
    private UserRepository userRepository;
    
    boolean isAddValid = false;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;
        
        if(isAddValid){
        	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "roles", "NotEmpty");
        	isAddValid = false;
    	}
        
        if (userRepository.findByUsername(user.getUsername()) != null) {
            errors.rejectValue("username", "Duplicate.userForm.username");
        }        

    }
    
    public void setAddValid(boolean isAddValid) {
    	this.isAddValid = isAddValid;
    }
}
