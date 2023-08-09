package com.duy.QuanLyPhongBan.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.duy.QuanLyPhongBan.entity.Department;

import lombok.Data;

@Data
public class TicketDTO {

	private Integer id;
	
	@NotBlank(message = "{not.blank}")
	private String clientName;
	
	@NotBlank(message = "{not.blank}")
	private String phoneNumber;
	
	@NotBlank(message = "{not.blank}")
	 private String feedback;
	 
	 private Date feedbackDate;
	 
	 @NotBlank(message = "{not.blank}")
	 private String response;
	 
	 private Date responseDate;
	 
	// Để kiểu dữ liệu nguyên thủy cho status chỉ nhận 2 giá trị true, false(mặc định)
	 private boolean status;
	 
	 private DepartmentDTO department;
}
