package com.duy.QuanLyPhongBan.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class Department {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String name;
	
	@CreatedDate
	@Column(updatable =  false)
	private Date createdAt;
	
	@LastModifiedDate
	private Date updateAt;
	
	//Không bắt buộc, dùng khi cần lấy danh sách Users bên department
	//department là tên thuộc tính ManyToOne bên user.
	@OneToMany(mappedBy = "department")
	private List<User> users;
}
