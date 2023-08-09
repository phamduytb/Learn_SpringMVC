package com.duy.QuanLyPhongBan.dto;

import lombok.Data;

@Data
public class DepartmentSearchDTO {

	private Integer currentPage;
	
	private Integer size;
	
	private String name;
	
	private String sortField;
}
