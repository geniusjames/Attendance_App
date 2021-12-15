package com.example.attendanceApp.staff.repository;

import com.example.attendanceApp.staff.model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {

    Boolean existsByEmail(String email);

    Staff findByEmail(String email);


}
