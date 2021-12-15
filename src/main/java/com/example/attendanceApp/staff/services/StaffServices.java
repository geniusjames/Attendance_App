package com.example.attendanceApp.staff.services;

import com.example.attendanceApp.staff.model.Role;
import com.example.attendanceApp.staff.model.Staff;
import com.example.attendanceApp.staff.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class StaffServices {

    private final StaffRepository staffRepo;

    @Autowired
    public StaffServices(StaffRepository staffRepo) {
        this.staffRepo = staffRepo;
    }


    public void createNewStaff(Staff staff){
            staffRepo.save(staff);
    }

    public Boolean checkIfStaffExist(String email){

        return  staffRepo.existsByEmail(email);
    }

    public List<Staff> allStaff() {
        List<Staff> allStaff = staffRepo.findAll();

        return allStaff;
    }

    public void deleteStaff(String email){

        boolean check = staffRepo.existsByEmail(email);

        if(check) {
            Staff staff = staffRepo.findByEmail(email);
            staffRepo.delete(staff);
        }
    }

    public Staff getStaffByPostEmail(String email){
        return staffRepo.findByEmail(email);
    }


}
