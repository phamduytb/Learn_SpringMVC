package com.duy.QuanLyPhongBan.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.duy.QuanLyPhongBan.dto.PageDTO;
import com.duy.QuanLyPhongBan.dto.UserDTO;
import com.duy.QuanLyPhongBan.dto.UserSearchDTO;
import com.duy.QuanLyPhongBan.entity.User;
import com.duy.QuanLyPhongBan.repository.UserRepo;

// Tạo bean - tạo đối tượng(new UserService, quản lý bởi IoC container)
@Service
public class UserService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private ModelMapper modelMapper;

	// Lấy 1 user theo id
	public UserDTO getUserById(int id) {
		User user = userRepo.findById(id).orElse(null);

		if (user != null) {
			// convert User -> UserDTO
			UserDTO userDTO = modelMapper.map(user, UserDTO.class);

			return userDTO;
		}
		return null;
	}

	// Thêm mới user
	// Transactional Hữu ích khi thay đổi dữ liệu trong DB
	@Transactional
	public void create(UserDTO userDTO) {
		// convert UserDTO sang User.
		User user = modelMapper.map(userDTO, User.class);
		userRepo.save(user);
	}

	// Sửa 1 user
	@Transactional
	public void update(UserDTO userDTO) {
		// update thường phải check xem user có tồn tại hay không.
		User currentUser = userRepo.findById(userDTO.getId()).orElse(null);
		if (currentUser != null) {
			currentUser = modelMapper.map(userDTO, User.class);
			userRepo.save(currentUser);
		}

	}

	// Xóa 1 user
	@Transactional
	public void delete(int id) {
		userRepo.deleteById(id);

	}

	private Pageable getPageable(UserSearchDTO searchDTO) {

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

	//// Chuyển từ Page<User> sang PageDTO<UserDTO>
	private PageDTO<UserDTO> convertPageDTO(Page<User> page) {

		PageDTO<UserDTO> pageDTO = new PageDTO<>();
		pageDTO.setTotalPages(page.getTotalPages());
		pageDTO.setTotalElements(page.getTotalElements());
		pageDTO.setNumberOfElements(page.getNumberOfElements());

		List<User> users = page.getContent();
		// Chuyển từ List<User> sang List<UserDTO>
		// Cách 1:
//		List<UserDTO> userDTOs = new ArrayList<>();
//		for (User user : users) {
//			userDTOs.add(modelMapper.map(user, UserDTO.class));
//		}
		// Cách 2 dùng java 8
		List<UserDTO> userDTOs = users.stream().map(u -> modelMapper.map(u, UserDTO.class))
				.collect(Collectors.toList());

		pageDTO.setContent(userDTOs);

		return pageDTO;
	}

	// Tìm kiếm danh sách khi không nhập từ khóa tìm kiếm
	// Đồng thời cũng là trường hợp load toàn bộ danh sách User khi lần đầu truy
	// cập.
	public PageDTO<UserDTO> searchAllUser(UserSearchDTO searchDTO) {

		Pageable pageable = getPageable(searchDTO);

		Page<User> page = userRepo.findAll(pageable);

		// Chuyển từ page<User> sang pageDTO<UserDTO>
		PageDTO<UserDTO> pageDTO = convertPageDTO(page);

		return pageDTO;
	}

	public PageDTO<UserDTO> searchByName(UserSearchDTO searchDTO) {

		Pageable pageable = getPageable(searchDTO);

		Page<User> page = userRepo.searchByName("%" + searchDTO.getName() + "%", pageable);

		PageDTO<UserDTO> pageDTO = convertPageDTO(page);

		return pageDTO;
	}

	public PageDTO<UserDTO> searchByUsername(UserSearchDTO searchDTO) {

		Pageable pageable = getPageable(searchDTO);

		Page<User> page = userRepo.searchByUsername("%" + searchDTO.getUsername() + "%", pageable);
		
		PageDTO<UserDTO> pageDTO = convertPageDTO(page);

		return pageDTO;
	}

	public PageDTO<UserDTO> searchByNameOrUsername(UserSearchDTO searchDTO) {

		Pageable pageable = getPageable(searchDTO);

		Page<User> page = userRepo.searchByNameOrUsername("%" + searchDTO.getName() + "%", "%" + searchDTO.getUsername() + "%",
				pageable);
		
		PageDTO<UserDTO> pageDTO = convertPageDTO(page);

		return pageDTO;
	}

	// Tìm kiếm theo username nhập chính xác
	public User getByUsername(String username) {
		return userRepo.findByUsername(username);
	}

	// Kiểm tra username, password của 1 user
//	public boolean checkExistUser(String username, String password) {
//		try {
//			return userRepo.checkExistUser(username, password);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return false;
//	}

}
