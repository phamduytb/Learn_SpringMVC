package com.duy.QuanLyPhongBan.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.duy.QuanLyPhongBan.dto.PageDTO;
import com.duy.QuanLyPhongBan.dto.UserDTO;
import com.duy.QuanLyPhongBan.dto.UserSearchDTO;
import com.duy.QuanLyPhongBan.service.UserService;

@RequestMapping("/user")
@Controller
public class UserController {
	
	//DI : dependency injection
	@Autowired
	UserService userService;
	
	@GetMapping("/list")
	public String getUsers(Model model, @ModelAttribute UserSearchDTO searchDTO) {
		PageDTO<UserDTO> pageDTO = null;
		
		if (StringUtils.hasText(searchDTO.getName()) && !searchDTO.getName().equals("null") && !StringUtils.hasText(searchDTO.getUsername())) {
			pageDTO = userService.searchByName(searchDTO);
		} else if (!StringUtils.hasText(searchDTO.getName()) && StringUtils.hasText(searchDTO.getUsername()) && !searchDTO.getUsername().equals("null")) {
			pageDTO = userService.searchByUsername(searchDTO);
		} else if (StringUtils.hasText(searchDTO.getName()) && StringUtils.hasText(searchDTO.getUsername()) 
				&& !searchDTO.getName().equals("null") && !searchDTO.getUsername().equals("null")) {
			pageDTO = userService.searchByNameOrUsername(searchDTO);
		} else {
			searchDTO.setName(null);
			searchDTO.setUsername(null);
			pageDTO = userService.searchAllUser(searchDTO);
		}
		
		model.addAttribute("pageDTO", pageDTO.getContent());
		model.addAttribute("searchDTO", searchDTO);
		model.addAttribute("totalPages", pageDTO.getTotalPages());
		model.addAttribute("totalElements", pageDTO.getTotalElements());
		model.addAttribute("totalElementOfCurrentPage", pageDTO.getNumberOfElements());
		
		return "user/users.html";
		
	}
	
	
	
	@GetMapping("/add")
	public String createUser(Model model) {
		UserDTO userDTO = new UserDTO();
		// Truyền đối tượng user sang view để có thể validate
		model.addAttribute("user", userDTO);
		return "user/add.html";
	}
	
	@PostMapping("/add")
	public String createUser(Model model, @ModelAttribute("user") @Valid UserDTO userDTO,
			BindingResult bindingResult) throws IllegalStateException, IOException {
		
		if (bindingResult.hasErrors()) {
//			model.addAttribute("user", user);
			return "user/add.html";
		}
		if (!userDTO.getFile().isEmpty()) {
			// Chỉ ra nơi lưu file trên ổ cứng
			//user.getFile() lấy file do người dùng upload lên
			// user.getFile().getOriginalFilename() lấy tên file do người dùng upload lên
			File saveFile = new File("E:\\workspace\\JMaster\\spring\\QuanLyPhongBan\\file_upload\\user\\" + userDTO.getFile().getOriginalFilename());
			
			// Lưu file vào thư mục đã chỉ định trên sever cụ thể ở đây là ổ E
			userDTO.getFile().transferTo(saveFile);
			
			// Trên DB chỉ lưu tên của file thôi.
			userDTO.setFileURL(userDTO.getFile().getOriginalFilename());
		}
		
		userService.create(userDTO);
		return "redirect:/user/list";
	}
	
	// lấy file lưu trên server để hiện thị lên web cho client
	@GetMapping("/showfile")
	public void download(@RequestParam("filename" ) String filename,
			HttpServletResponse resp) throws IOException {
		
				// Lấy file đã lưu ở ổ cứng hiện thị lên web cho client
				File file = new File("E:\\workspace\\JMaster\\spring\\QuanLyPhongBan\\file_upload\\user\\" + filename);
				Files.copy(file.toPath(), resp.getOutputStream());
			}

	@GetMapping("/edit")
	public String editUser(@RequestParam("id") Integer id,
							Model model
							) {
		UserDTO userDTO = userService.getUserById(id);
		model.addAttribute("user", userDTO);
		return "user/edit.html";
	}
	
	@PostMapping("/edit")
	public String editUser(@ModelAttribute("user") @Valid UserDTO userDTO,
			BindingResult bindingResult
			) throws IllegalStateException, IOException {
		
		if (bindingResult.hasErrors()) {
			return "user/edit.html";
		}
		if (!userDTO.getFile().isEmpty()) {
			// Chỉ ra nơi lưu file trên ổ cứng
			// uploadFile.getOriginalFilename() lấy file do người dùng upload lên
			File saveFile = new File("E:\\workspace\\JMaster\\spring\\QuanLyPhongBan\\file_upload\\user\\" + userDTO.getFile().getOriginalFilename());
			
			// Lưu file vào thư mục đã chỉ định trên sever cụ thể ở đây là ổ E
			userDTO.getFile().transferTo(saveFile);
			
			// Trên DB chỉ lưu tên của file thôi.
			userDTO.setFileURL(userDTO.getFile().getOriginalFilename());
		}
		userService.update(userDTO);
		return "redirect:/user/list";
	}
	
	@GetMapping("/delete")
	public String deleteUser(@RequestParam("id") Integer id) {
		userService.delete(id);
		return "redirect:/user/list";
	}
}
