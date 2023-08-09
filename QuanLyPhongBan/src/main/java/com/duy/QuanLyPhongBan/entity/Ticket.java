package com.duy.QuanLyPhongBan.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class Ticket {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String clientName;
	
	private String phoneNumber;
	
	 private String feedback;
	 
	 @CreatedDate
	 @Column(updatable = false)
	 private Date feedbackDate;
	 
	 private String response;
	 
	 @LastModifiedDate
	 private Date responseDate;
	 
	 // Để kiểu dữ liệu nguyên thủy cho status chỉ nhận 2 giá trị true, false(mặc định)
	 private boolean status;
	 
	 @ManyToOne
	 private Department department;
}
