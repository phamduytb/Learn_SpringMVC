package com.duy.QuanLyPhongBan.service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.duy.QuanLyPhongBan.dto.DepartmentDTO;
import com.duy.QuanLyPhongBan.dto.DepartmentSearchDTO;
import com.duy.QuanLyPhongBan.dto.PageDTO;
import com.duy.QuanLyPhongBan.entity.Department;
import com.duy.QuanLyPhongBan.repository.DepartmentRepo;

@Service
public class DepartmentService implements IDepartmentService{
	@Autowired
	private DepartmentRepo departmentRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public void create(DepartmentDTO departmentDTO) {
		
		Department department = modelMapper.map(departmentDTO, Department.class);
		departmentRepo.save(department);
	}

	@Override
	public void update(DepartmentDTO departmentDTO) {
		Department currentDepartment = departmentRepo.findById(departmentDTO.getId()).orElse(null);
		if (currentDepartment != null) {
			currentDepartment = modelMapper.map(departmentDTO, Department.class);
			departmentRepo.save(currentDepartment);
		}
	}

	@Override
	public void delete(int id) {
		departmentRepo.deleteById(id);
	}

	@Override
	public DepartmentDTO searchById(int id) {
		Department department = departmentRepo.findById(id).orElse(null);
		if (department != null) {
			DepartmentDTO departmentDTO = modelMapper.map(department, DepartmentDTO.class);
			return departmentDTO;
		}
		return null;
	}
	
	// Tìm kiếm toàn bộ danh sách department không có phân trang.
	public List<DepartmentDTO> findAll() {
		List<Department> departments =  departmentRepo.findAll();
		List<DepartmentDTO> departmentDTOs = new ArrayList<>();
		for (Department d : departments) {
			departmentDTOs.add(modelMapper.map(d, DepartmentDTO.class));
		}
		return departmentDTOs;
	}
	
	private Pageable getPageable(DepartmentSearchDTO searchDTO) {

		Integer currentPage = searchDTO.getCurrentPage() == null ? 0 : searchDTO.getCurrentPage();
		Integer size = searchDTO.getSize() == null ? 5 : searchDTO.getSize();
		searchDTO.setCurrentPage(currentPage);
		searchDTO.setSize(size);

		Sort sortBy = Sort.by("id").ascending();

		if (StringUtils.hasText(searchDTO.getSortField()) && !searchDTO.getSortField().equals("null")) {
			sortBy = Sort.by(searchDTO.getSortField()).ascending();
		}

		Pageable pageable = PageRequest.of(currentPage, size, sortBy);

		return pageable;
	}
	
	private PageDTO<DepartmentDTO> convertPageDTO(Page<Department> page) {

		PageDTO<DepartmentDTO> pageDTO = new PageDTO<>();
		pageDTO.setTotalPages(page.getTotalPages());
		pageDTO.setTotalElements(page.getTotalElements());
		pageDTO.setNumberOfElements(page.getNumberOfElements());

		List<Department> departments = page.getContent();
		// Chuyển từDepartmentDTO List<User> sang List<UserDTO>
		// Cách 1:
//		List<UserDTO> userDTOs = new ArrayList<>();
//		for (User user : users) {
//			userDTOs.add(modelMapper.map(user, UserDTO.class));
//		}
		// Cách 2 dùng java 8
		List<DepartmentDTO> departmentDTOs = departments.stream().map(d -> modelMapper.map(d, DepartmentDTO.class))
				.collect(Collectors.toList());

		pageDTO.setContent(departmentDTOs);

		return pageDTO;
	}

	@Override
	public PageDTO<DepartmentDTO> searchAll(DepartmentSearchDTO searchDTO) {
		Pageable pageable = getPageable(searchDTO);

		Page<Department> page = departmentRepo.findAll(pageable);

		PageDTO<DepartmentDTO> pageDTO = convertPageDTO(page);

		return pageDTO;
	}

	@Override
	public PageDTO<DepartmentDTO> searchByname(DepartmentSearchDTO searchDTO) {
		Pageable pageable = getPageable(searchDTO);

		Page<Department> page = departmentRepo.searchByName("%" + searchDTO.getName() + "%", pageable);

		PageDTO<DepartmentDTO> pageDTO = convertPageDTO(page);

		return pageDTO;
	}
	
	
}
