package com.duy.QuanLyPhongBan.service;

import com.duy.QuanLyPhongBan.dto.DepartmentDTO;
import com.duy.QuanLyPhongBan.dto.DepartmentSearchDTO;
import com.duy.QuanLyPhongBan.dto.PageDTO;

public interface IDepartmentService {
	void create(DepartmentDTO departmentDTO);
	
	void update(DepartmentDTO departmentDTO);
	
	void delete(int id);
	
	DepartmentDTO searchById(int id);
	
	PageDTO<DepartmentDTO> searchAll(DepartmentSearchDTO searchDTO);
	
 	PageDTO<DepartmentDTO> searchByname(DepartmentSearchDTO searchDTO);
}
