package com.duy.QuanLyPhongBan.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.duy.QuanLyPhongBan.entity.Department;
import com.duy.QuanLyPhongBan.entity.User;

public interface DepartmentRepo extends JpaRepository<Department, Integer>{

	// Tìm kiếm theo name ( Không cần nhập tên đầy đủ)
	@Query("SELECT d FROM Department d WHERE d.name LIKE :x")
	Page<Department> searchByName(@Param("x") String name, Pageable pageable);
}
