package com.duy.QuanLyPhongBan.repository;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.duy.QuanLyPhongBan.entity.Ticket;

public interface TicketRepo extends JpaRepository<Ticket, Integer> {

	// Tìm kiếm theo số điện thoại
	@Query("SELECT t FROM Ticket t WHERE t.phoneNumber = :num")
	Page<Ticket> searchByPhone(@Param("num") String phoneNumber, Pageable pageable);

	// Tìm kiếm theo phòng ban
	@Query("SELECT t FROM Ticket t WHERE t.department.id = :departmentId")
	Page<Ticket> searchByDepartment(@Param("departmentId") Integer departmentId, Pageable pageable);

	// Tìm kiếm theo phòng ban, ngày bắt đầu, ngày kết thúc
	@Query("SELECT t FROM Ticket t WHERE t.department.id = :departmentId AND"
			+ " t.feedbackDate >= :startDate AND t.feedbackDate <= :endDate")
	Page<Ticket> searchByDepartmentAndDate(@Param("departmentId") Integer departmentId,
			@Param("startDate") Date startDate, @Param("endDate") Date endDate, Pageable pageable);

	// Tìm kiếm theo phòng ban, ngày bắt đầu
	@Query("SELECT t FROM Ticket t WHERE t.department.id = :departmentId AND" + " t.feedbackDate >= :startDate")
	Page<Ticket> searchByDepartmentAndStartDate(@Param("departmentId") Integer departmentId,
			@Param("startDate") Date startDate, Pageable pageable);

	// Tìm kiếm theo phòng ban, ngày kết thúc
	@Query("SELECT t FROM Ticket t WHERE t.department.id = :departmentId AND" + " t.feedbackDate <= :endDate")
	Page<Ticket> searchByDepartmentAndEndDate(@Param("departmentId") Integer departmentId,
			@Param("endDate") Date endDate, Pageable pageable);

	// Tìm kiếm theo ngày bắt đầu, ngày kết thúc
	@Query("SELECT t FROM Ticket t WHERE " + " t.feedbackDate >= :startDate AND t.feedbackDate <= :endDate")
	Page<Ticket> searchByDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate, Pageable pageable);

	// Tìm kiếm theo ngày bắt đầu
	@Query("SELECT t FROM Ticket t WHERE " + " t.feedbackDate >= :startDate")
	Page<Ticket> searchByStartDate(@Param("startDate") Date startDate, Pageable pageable);

	// Tìm kiếm theo ngày kết thúc
	@Query("SELECT t FROM Ticket t WHERE " + "t.feedbackDate <= :endDate")
	Page<Ticket> searchByEndDate(@Param("endDate") Date endDate, Pageable pageable);
}
