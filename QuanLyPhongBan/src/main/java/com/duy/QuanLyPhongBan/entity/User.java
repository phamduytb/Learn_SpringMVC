package com.duy.QuanLyPhongBan.entity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

//Gen geT, set và constructor mặc định
@Data
//Map với bảng trong DB, nếu không đặt tên bảng thì tự động lấy tên Class làm tên bảng- Không cần thêm @Table nữa.
@Table(name = "user")
@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private Integer age;
	
	
	private String name;
	
	private String fileURL;
	
	
	@Column(unique = true)
	private String username;
	
	private String password;
	
}
