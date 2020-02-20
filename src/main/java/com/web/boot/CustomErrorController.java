package com.web.boot;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import groovy.util.logging.Slf4j;

@Controller
@Slf4j
public class CustomErrorController implements ErrorController{
	
	private static final String ERROR_PATH = "/error";

	@Override
	public String getErrorPath() {
		// TODO Auto-generated method stub
		return ERROR_PATH;
	}

	@RequestMapping(value=ERROR_PATH)
	public String handleError(HttpServletRequest request) {
		return "error";
	}
}
