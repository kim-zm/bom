package com.web.boot;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sun.istack.logging.Logger;

@RestController
public class RestWebController {
	private static final Logger Log = Logger.getLogger(RestWebController.class);
	
	@ResponseBody
	@GetMapping(value= "/hello")
    public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
		Map<String, Object> map = new HashMap<>();		
		map.put("url", request.getRequestURL().toString());
		map.put("uri", request.getRequestURI().toString());
		map.put("questString",request.getQueryString());
		map.put("method",request.getMethod());
		map.put("ContentLength",request.getContentLength());
		map.put("remoteAddr",request.getRemoteAddr());
		map.put("remoteHost",request.getRemoteHost());
		map.put("remotePort",request.getRemotePort());
		map.put("remoteUser",request.getRemoteUser());
		map.put("encoding",request.getCharacterEncoding());
		
		map.put("resContentLength",response.getContentType());
		
		Iterator<String> e = map.keySet().iterator();
		while(e.hasNext()){
			String key = e.next();
			System.out.println(key+" "+map.get(key));
		}
		
		
		
		return "hello";
    }

}
