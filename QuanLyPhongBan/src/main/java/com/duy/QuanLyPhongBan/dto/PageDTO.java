package com.duy.QuanLyPhongBan.dto;

import java.util.List;

import lombok.Data;

@Data
public class PageDTO<T> {

	private int totalPages;
	
	private long totalElements;
	
	private int numberOfElements;
	
	private List<T> content;
}
