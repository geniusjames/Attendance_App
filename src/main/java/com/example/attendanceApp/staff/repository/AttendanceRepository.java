package com.example.attendanceApp.staff.repository;

import com.example.attendanceApp.staff.model.AttendanceTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendanceRepository extends JpaRepository<AttendanceTable, Long> {
}
