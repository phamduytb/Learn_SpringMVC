package com.duy.QuanLyPhongBan.dto;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

//Gen geT, set và constructor mặc định
@Data
public class UserDTO {
	
	private Integer id;
	
	@NotNull(message = "{not.blank}")
	private Integer age;
	
	@NotBlank(message = "{not.blank}")
	private String name;
	
	private String fileURL;
	
	private MultipartFile file;
	
	@NotBlank(message = "{not.blank}")
	private String username;
	
	@NotBlank(message = "{not.blank}")
	private String password;
	
}
