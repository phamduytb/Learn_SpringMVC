package com.duy.QuanLyPhongBan.dto;

import java.util.Date;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class DepartmentDTO {

	
	private Integer id;
	
	@NotBlank(message = "{not.blank}")
	private String name;
	
	private Date createdAt;
	
	private Date updateAt;
}
