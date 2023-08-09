package com.duy.QuanLyPhongBan.service;

import java.util.Date;

import com.duy.QuanLyPhongBan.dto.PageDTO;
import com.duy.QuanLyPhongBan.dto.TicketDTO;
import com.duy.QuanLyPhongBan.dto.TicketSearchDTO;

public interface ITicketService {

	void create(TicketDTO ticketDTO);
	
	void response(TicketDTO ticketDTO);
	
	void delete(int id);
	
	TicketDTO searchById(int id);
	
	PageDTO<TicketDTO> searchAll(TicketSearchDTO searchDTO);
	
	PageDTO<TicketDTO> searchByPhone(TicketSearchDTO searchDTO);
	
	PageDTO<TicketDTO> searchByDepartment(TicketSearchDTO searchDTO);

	PageDTO<TicketDTO> searchByDepartmentAndDate(TicketSearchDTO searchDTO, Date startDate, Date endDate);

	PageDTO<TicketDTO> searchByDepartmentAndStartDate(TicketSearchDTO searchDTO, Date startDate);

	PageDTO<TicketDTO> searchByDepartmentAndEndDate(TicketSearchDTO searchDTO, Date endDate);

	PageDTO<TicketDTO> searchByDate(TicketSearchDTO searchDTO, Date startDate, Date endDate);
	
	PageDTO<TicketDTO> searchByStartDate(TicketSearchDTO searchDTO, Date startDate);

	PageDTO<TicketDTO> searchByEndDate(TicketSearchDTO searchDTO, Date endDate);

	
	
	
}
