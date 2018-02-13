package com.ecopack.controllers;



import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class MainController {
		
	@RequestMapping(path="/login")
	public String getIndex()
	{		
		return "index.html";
	}
	
	@RequestMapping(path="/")
	public String home(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null){    
			return "homepage.html";
	    }
	   return "redirect:login";
	   
	}
	
	
	@RequestMapping(path="/loginFailed")
	public String loginFailed()
	{		
		return "redirect:/";
	}
	
	
	
	
	
	
	

}
