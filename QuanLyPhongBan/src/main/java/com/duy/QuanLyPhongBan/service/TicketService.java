package com.duy.QuanLyPhongBan.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.duy.QuanLyPhongBan.dto.DepartmentDTO;
import com.duy.QuanLyPhongBan.dto.DepartmentSearchDTO;
import com.duy.QuanLyPhongBan.dto.PageDTO;
import com.duy.QuanLyPhongBan.dto.TicketDTO;
import com.duy.QuanLyPhongBan.dto.TicketSearchDTO;
import com.duy.QuanLyPhongBan.entity.Department;
import com.duy.QuanLyPhongBan.entity.Ticket;
import com.duy.QuanLyPhongBan.repository.TicketRepo;

@Service
public class TicketService implements ITicketService {

	@Autowired
	TicketRepo ticketRepo;
	@Autowired
	ModelMapper modelMapper;

	@Override
	@Transactional
	public void create(TicketDTO ticketDTO) {
		Ticket ticket = modelMapper.map(ticketDTO, Ticket.class);
		ticketRepo.save(ticket);
	}

	@Override
	@Transactional
	public void response(TicketDTO ticketDTO) {
		Ticket ticket = ticketRepo.findById(ticketDTO.getId()).orElse(null);
		if (ticket != null) {
			ticket.setId(ticketDTO.getId());
			ticket.setResponse(ticketDTO.getResponse());
			ticket.setStatus(true);
			ticketRepo.save(ticket);
		}

	}

	@Override
	@Transactional
	public void delete(int id) {
		ticketRepo.deleteById(id);

	}

	@Override
	public TicketDTO searchById(int id) {
		Ticket ticket = ticketRepo.findById(id).orElse(null);
		if (ticket != null) {
			TicketDTO ticketDTO = modelMapper.map(ticket, TicketDTO.class);
			return ticketDTO;
		}
		return null;
	}

	private Pageable getPageable(TicketSearchDTO searchDTO) {

		Integer currentPage = searchDTO.getCurrentPage() == null ? 0 : searchDTO.getCurrentPage();
		Integer size = searchDTO.getSize() == null ? 5 : searchDTO.getSize();
		searchDTO.setCurrentPage(currentPage);
		searchDTO.setSize(size);

		Sort sortBy = Sort.by("id").ascending();

		if (StringUtils.hasText(searchDTO.getSortField())) {
			sortBy = Sort.by(searchDTO.getSortField()).ascending();
		}

		Pageable pageable = PageRequest.of(currentPage, size, sortBy);

		return pageable;
	}

	private PageDTO<TicketDTO> convertPageDTO(Page<Ticket> page) {

		PageDTO<TicketDTO> pageDTO = new PageDTO<>();
		pageDTO.setTotalPages(page.getTotalPages());
		pageDTO.setTotalElements(page.getTotalElements());
		pageDTO.setNumberOfElements(page.getNumberOfElements());

		List<Ticket> tickets = page.getContent();
		// Chuyển từDepartmentDTO List<User> sang List<UserDTO>
		// Cách 1:
//		List<UserDTO> userDTOs = new ArrayList<>();
//		for (User user : users) {
//			userDTOs.add(modelMapper.map(user, UserDTO.class));
//		}
		// Cách 2 dùng java 8
		List<TicketDTO> ticketDTOs = tickets.stream().map(t -> modelMapper.map(t, TicketDTO.class))
				.collect(Collectors.toList());

		pageDTO.setContent(ticketDTOs);

		return pageDTO;
	}

	@Override
	public PageDTO<TicketDTO> searchAll(TicketSearchDTO searchDTO) {
		Pageable pageable = getPageable(searchDTO);

		Page<Ticket> page = ticketRepo.findAll(pageable);

		PageDTO<TicketDTO> pageDTO = convertPageDTO(page);

		return pageDTO;
	}

	@Override
	public PageDTO<TicketDTO> searchByPhone(TicketSearchDTO searchDTO) {
		Pageable pageable = getPageable(searchDTO);

		Page<Ticket> page = ticketRepo.searchByPhone(searchDTO.getPhoneNumber(), pageable);

		PageDTO<TicketDTO> pageDTO = convertPageDTO(page);

		return pageDTO;
	}

	@Override
	public PageDTO<TicketDTO> searchByDepartment(TicketSearchDTO searchDTO) {
		Pageable pageable = getPageable(searchDTO);

		Page<Ticket> page = ticketRepo.searchByDepartment(searchDTO.getDepartmentId(), pageable);

		PageDTO<TicketDTO> pageDTO = convertPageDTO(page);

		return pageDTO;
	}

	@Override
	public PageDTO<TicketDTO> searchByDepartmentAndDate(TicketSearchDTO searchDTO, Date startDate, Date endDate) {
		Pageable pageable = getPageable(searchDTO);

		Page<Ticket> page = ticketRepo.searchByDepartmentAndDate(searchDTO.getDepartmentId(), startDate,
				endDate, pageable);

		PageDTO<TicketDTO> pageDTO = convertPageDTO(page);

		return pageDTO;
	}

	@Override
	public PageDTO<TicketDTO> searchByDepartmentAndStartDate(TicketSearchDTO searchDTO, Date startDate) {
		Pageable pageable = getPageable(searchDTO);

		Page<Ticket> page = ticketRepo.searchByDepartmentAndStartDate(searchDTO.getDepartmentId(),
				startDate, pageable);

		PageDTO<TicketDTO> pageDTO = convertPageDTO(page);

		return pageDTO;
	}

	@Override
	public PageDTO<TicketDTO> searchByDepartmentAndEndDate(TicketSearchDTO searchDTO, Date endDate) {
		Pageable pageable = getPageable(searchDTO);

		Page<Ticket> page = ticketRepo.searchByDepartmentAndEndDate(searchDTO.getDepartmentId(),
				endDate, pageable);

		PageDTO<TicketDTO> pageDTO = convertPageDTO(page);

		return pageDTO;
	}

	@Override
	public PageDTO<TicketDTO> searchByDate(TicketSearchDTO searchDTO, Date startDate, Date endDate) {
		Pageable pageable = getPageable(searchDTO);

		Page<Ticket> page = ticketRepo.searchByDate(startDate, endDate, pageable);

		PageDTO<TicketDTO> pageDTO = convertPageDTO(page);

		return pageDTO;
	}

	@Override
	public PageDTO<TicketDTO> searchByStartDate(TicketSearchDTO searchDTO, Date startDate) {
		Pageable pageable = getPageable(searchDTO);

		Page<Ticket> page = ticketRepo.searchByStartDate(startDate, pageable);

		PageDTO<TicketDTO> pageDTO = convertPageDTO(page);

		return pageDTO;
	}

	@Override
	public PageDTO<TicketDTO> searchByEndDate(TicketSearchDTO searchDTO, Date endDate) {
		Pageable pageable = getPageable(searchDTO);

		Page<Ticket> page = ticketRepo.searchByEndDate(endDate, pageable);

		PageDTO<TicketDTO> pageDTO = convertPageDTO(page);

		return pageDTO;
	}

	

}
