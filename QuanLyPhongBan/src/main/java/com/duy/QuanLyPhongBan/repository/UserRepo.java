package com.duy.QuanLyPhongBan.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.duy.QuanLyPhongBan.entity.User;

public interface UserRepo extends JpaRepository<User, Integer>{
	
	//Tìm kiếm theo username( Nhập chính xác)
	//Đặt tên hàm theo chuẩn(findBy...) thì tự gen cậu lệnh SQL cho mình
	User findByUsername(String username);
	
	// Tìm kiếm theo tên ( Không cần nhập tên đầy đủ)
	// Câu lệnh viết theo kiểu JPQL
	@Query("SELECT u FROM User u WHERE u.name LIKE :name")
	Page<User> searchByName(@Param("name") String s, Pageable pageable);
	
	// Tìm kiếm theo username ( Không cần nhập tên đầy đủ)
	@Query("SELECT u FROM User u WHERE u.username LIKE :username")
	Page<User> searchByUsername(@Param("username") String s, Pageable pageable);

	//Tìm kiếm theo tên hoặc username.
	@Query("SELECT u FROM User u WHERE u.name LIKE :name OR u.username LIKE :username")
	Page<User> searchByNameOrUsername(@Param("name") String name, @Param("username") String username, Pageable pageable);
	
	@Modifying
	@Query("DELETE FROM User u WHERE u.username = :x")
	void deleteByUser(@Param("x") String username);
	
	// Tự gen câu lệnh xóa
	void deleteByUsername(String username);
}
