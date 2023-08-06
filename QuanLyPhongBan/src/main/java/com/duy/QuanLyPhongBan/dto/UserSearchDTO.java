package com.duy.QuanLyPhongBan.dto;

import lombok.Data;

@Data
public class UserSearchDTO {
	private Integer currentPage;
	
	private Integer size;
	
	private String name;
	
	private String username;
	
	private String sortField;
}
