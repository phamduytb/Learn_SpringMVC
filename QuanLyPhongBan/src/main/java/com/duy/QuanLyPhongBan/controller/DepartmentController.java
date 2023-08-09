package com.duy.QuanLyPhongBan.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.duy.QuanLyPhongBan.dto.DepartmentDTO;
import com.duy.QuanLyPhongBan.dto.DepartmentSearchDTO;
import com.duy.QuanLyPhongBan.dto.PageDTO;
import com.duy.QuanLyPhongBan.service.DepartmentService;

@RequestMapping("/department")
@Controller
public class DepartmentController {
	
	@Autowired
	DepartmentService departmentService;
	
	@GetMapping("/list")
	public String get(Model model,@ModelAttribute DepartmentSearchDTO searchDTO) {
		PageDTO<DepartmentDTO> pageDTO = null;
		if (StringUtils.hasText(searchDTO.getName()) && !searchDTO.getName().equals("null")) {
			pageDTO = departmentService.searchByname(searchDTO);
		} else {
			searchDTO.setName(null);
			pageDTO = departmentService.searchAll(searchDTO);
		}
		
		model.addAttribute("pageDTO", pageDTO.getContent());
		model.addAttribute("searchDTO", searchDTO);
		model.addAttribute("totalPages", pageDTO.getTotalPages());
		model.addAttribute("totalElements", pageDTO.getTotalElements());
		model.addAttribute("totalElementOfCurrentPage", pageDTO.getNumberOfElements());
		
		return "department/list.html";
	}
	
	@GetMapping("/add")
	public String create(Model model) {
		DepartmentDTO departmentDTO = new DepartmentDTO();
		model.addAttribute("departmentDTO", departmentDTO);
		return "department/add.html";
	}
	
	@PostMapping("/add")
	public String create(@ModelAttribute("departmentDTO") @Valid DepartmentDTO departmentDTO,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "department/add.html";
		}
		departmentService.create(departmentDTO);
		return "redirect:/department/list";
	}
	
	@GetMapping("/edit")
	public String update(Model model, @RequestParam("id") Integer id) {
		DepartmentDTO departmentDTO = departmentService.searchById(id);
		model.addAttribute("departmentDTO", departmentDTO);
		return "department/edit.html";
	}
	
	@PostMapping("/edit")
	public String update(@ModelAttribute("departmentDTO") @Valid DepartmentDTO departmentDTO,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "department/edit.html";
		}
		departmentService.update(departmentDTO);
		return "redirect:/department/list";
	}
	
	@GetMapping("/delete")
	public String delete(@RequestParam("id") Integer id) {
		departmentService.delete(id);
		return "redirect:/department/list";
	}
}
