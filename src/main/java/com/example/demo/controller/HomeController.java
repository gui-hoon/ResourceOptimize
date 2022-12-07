package com.example.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.service.UserService;
import com.example.demo.dto.UserVo;

@Controller
public class HomeController {
	private UserService uService;
	
	public HomeController(UserService u) {
		this.uService=u;
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model) {
		return "redirect:/login";
	}
	
	@GetMapping("/login")
    public String login(){
        return "home/login";
    }
	
	@GetMapping("/home")
    public String userAccess(Model model, Authentication authentication) {
        //Authentication 객체를 통해 유저 정보를 가져올 수 있다.
        UserVo userVo = (UserVo) authentication.getPrincipal();  //userDetail 객체를 가져옴
        model.addAttribute("member", userVo); 
		return "home/home";
    }
	
	@GetMapping("/access_denied")
    public String accessDenied() {
        return "home/access_denied";
    }
	
	@GetMapping("/signup")
    public String signUp() {
        return "home/signup";
    }
	
	@PostMapping("/signup")
    public String signUp(UserVo userVo) {
        uService.joinUser(userVo);
        return "redirect:/login";
    }
	
}
