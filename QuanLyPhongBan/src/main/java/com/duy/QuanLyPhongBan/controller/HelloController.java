package com.duy.QuanLyPhongBan.controller;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class HelloController {
	@GetMapping("/hello")
	public String hello() {
		
		
		return "hello.html";
	}
	
	@PostMapping("/hello")
	public String hello(Model model, @RequestParam("inputDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
		
		model.addAttribute("date", date);
		System.out.println(date);
		return "hello.html";
	}
}
