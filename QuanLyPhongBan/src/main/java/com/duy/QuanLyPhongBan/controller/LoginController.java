package com.duy.QuanLyPhongBan.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
	@GetMapping("/login")
	public String login() {
		return "login.html";
	}
	
	@PostMapping("/login")
	public String login(
			HttpSession session,
			@RequestParam("username") String username,
			@RequestParam("password") String password) {
//		String username = request.getParameter("username");
//		String password = request.getParameter("password");
		
		if (username.equals("admin") && password.equals("123")) {
			return "redirect:/hello";
		} else {
			return "redirect:/login";
		}
	}
}
