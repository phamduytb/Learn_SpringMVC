package com.duy.QuanLyPhongBan.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import com.duy.QuanLyPhongBan.dto.PageDTO;
import com.duy.QuanLyPhongBan.dto.TicketDTO;
import com.duy.QuanLyPhongBan.dto.TicketSearchDTO;
import com.duy.QuanLyPhongBan.service.DepartmentService;
import com.duy.QuanLyPhongBan.service.TicketService;

@Controller
@RequestMapping("/ticket")
public class TicketController {

	@Autowired
	TicketService ticketService;
	@Autowired
	DepartmentService departmentService;

	@GetMapping("/list")
	public String get(Model model, @ModelAttribute("searchDTO") TicketSearchDTO searchDTO) throws ParseException {
		PageDTO<TicketDTO> pageDTO = null;
		Date startDate =null;
		Date endDate =null;
		if (searchDTO.getStartDate() != null) {
			startDate = new SimpleDateFormat("yyyy-MM-dd").parse(searchDTO.getStartDate());
		}
		
		if (searchDTO.getEndDate() != null) {
			endDate = new SimpleDateFormat("yyyy-MM-dd").parse(searchDTO.getEndDate());
		}
		// Nếu có nhập số điện thoại thì ta chỉ tìm theo số điện thoại
		if (StringUtils.hasText(searchDTO.getPhoneNumber())) {
			pageDTO = ticketService.searchByPhone(searchDTO);
		} else {
			if (searchDTO.getDepartmentId() != null && searchDTO.getStartDate() != null
					&& searchDTO.getEndDate() != null) {
				pageDTO = ticketService.searchByDepartmentAndDate(searchDTO,startDate, endDate);
			} else if (searchDTO.getDepartmentId() != null && searchDTO.getStartDate() != null) {
				pageDTO = ticketService.searchByDepartmentAndStartDate(searchDTO, startDate);
			} else if (searchDTO.getDepartmentId() != null && searchDTO.getEndDate() != null) {
				pageDTO = ticketService.searchByDepartmentAndEndDate(searchDTO, endDate);
			} else if (searchDTO.getStartDate() != null && searchDTO.getEndDate() != null) {
				pageDTO = ticketService.searchByDate(searchDTO, startDate, endDate);
			} else if (searchDTO.getDepartmentId() != null) {
				pageDTO = ticketService.searchByDepartment(searchDTO);
			} else if (searchDTO.getStartDate() != null) {
				pageDTO = ticketService.searchByStartDate(searchDTO, startDate);
			} else if (searchDTO.getEndDate() != null) {
				pageDTO = ticketService.searchByEndDate(searchDTO, endDate);
			} else {
				pageDTO = ticketService.searchAll(searchDTO);
			}
		}
		
 		model.addAttribute("pageDTO", pageDTO.getContent());
		model.addAttribute("searchDTO", searchDTO);
		model.addAttribute("totalPages", pageDTO.getTotalPages());
		model.addAttribute("totalElements", pageDTO.getTotalElements());
		model.addAttribute("totalElementOfCurrentPage", pageDTO.getNumberOfElements());
		model.addAttribute("departmentDTOs", departmentService.findAll());
		return "ticket/list.html";
	}
	
	@ModelAttribute("searchDTO")
    public TicketSearchDTO getDefaultEvent() {
		TicketSearchDTO searchDTO = new TicketSearchDTO();
        return searchDTO;
    }
	
	@GetMapping("/add")
	public String create(Model model) {
		TicketDTO ticketDTO = new TicketDTO();
		List<DepartmentDTO> departmentDTOs = departmentService.findAll();
		model.addAttribute("ticketDTO", ticketDTO);
		model.addAttribute("departmentDTOs", departmentDTOs);
		return "ticket/add.html";
	}
	
	@PostMapping("/add")
	public String create(Model model, @ModelAttribute("ticketDTO") @Valid TicketDTO ticketDTO,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("departmentDTOs", departmentService.findAll());
			return "ticket/add.html";
		}
		ticketService.create(ticketDTO);
		return "redirect:/ticket/list";
	}
	
	@GetMapping("/response")
	public String response(@RequestParam("id") Integer id, Model model) {
		TicketDTO ticketDTO = ticketService.searchById(id);
		if (ticketDTO != null) {
			model.addAttribute("ticketDTO", ticketDTO);
		}
		return "ticket/response.html";
	}
	
	@PostMapping("/response")
	public String response(@ModelAttribute("ticketDTO") @Valid TicketDTO ticketDTO,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "ticket/response.html";
		}
		ticketService.response(ticketDTO);
		return "redirect:/ticket/list";
	}
	
	@GetMapping("/detail")
	public String detail(@RequestParam("id") Integer id, Model model) {
		TicketDTO ticketDTO = ticketService.searchById(id);
		if (ticketDTO != null) {
			model.addAttribute("ticketDTO", ticketDTO);
		}
		return "ticket/detail.html";
	}
}
