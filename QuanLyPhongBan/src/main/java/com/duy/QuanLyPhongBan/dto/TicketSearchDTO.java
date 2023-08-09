package com.duy.QuanLyPhongBan.dto;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.StringUtils;

import lombok.Data;


public class TicketSearchDTO {

	private Integer currentPage;
	
	private Integer size;
	
	private String sortField;
	
	private String phoneNumber;
	
	private Integer departmentId;
	
	private String startDate;
	
	private String endDate;
	
	
	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		if (sortField.equals("null")) {
			this.sortField = null;
		} else {
			this.sortField = sortField;
		}
		
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		if (phoneNumber.equals("null")) {
			this.phoneNumber = null;
		} else {
			this.phoneNumber = phoneNumber;
		}
	}

	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		// Trường hợp không chọn phòng ban nhận departmentId = 0  từ view, gắn lại departmentId = null
		if (departmentId == 0) {
			this.departmentId = null;
		} else {
			this.departmentId = departmentId;
		}
		
	}
	

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDateString) throws ParseException {
        if (startDateString.equals("null") || !StringUtils.hasText(startDateString)) {
            this.startDate = null;
        } else {
            
            this.startDate = startDateString;
        }
    }

	public String getEndDate() {
		return endDate;
	}
	
	public void setEndDate(String endDateString) throws ParseException {
        if (endDateString.equals("null") || !StringUtils.hasText(endDateString)) {
            this.endDate = null;
        } else {
        	
            this.endDate = endDateString;
        }
    }
}
