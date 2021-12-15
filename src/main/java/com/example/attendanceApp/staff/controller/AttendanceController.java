package com.example.attendanceApp.staff.controller;

import com.example.attendanceApp.staff.model.AttendanceTable;
import com.example.attendanceApp.staff.model.Role;
import com.example.attendanceApp.staff.model.Staff;
import com.example.attendanceApp.staff.repository.AttendanceRepository;
import com.example.attendanceApp.staff.services.AttendanceServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class AttendanceController {

    private final AttendanceRepository attendanceRepository;
    private final AttendanceServices attendanceServices;

    @Autowired
    public AttendanceController(AttendanceRepository attendanceRepository, AttendanceServices attendanceServices) {
        this.attendanceRepository = attendanceRepository;
        this.attendanceServices = attendanceServices;
    }

    @RequestMapping("/attendance")
    public String addToAttendanceTable(HttpServletRequest request, Model model){

        HttpSession session = request.getSession(false);
        String fullName = session.getAttribute("fullName").toString();
        String status;
        boolean latenessCheck = java.time.LocalTime.now().isBefore(java.time.LocalTime.of(8, 0));
        if(latenessCheck) status = "early";
        else status = "late";

        int hour = java.time.LocalTime.now().getHour();
        int min = java.time.LocalTime.now().getMinute();
        int second = java.time.LocalTime.now().getSecond();

        String time = hour + ": " + min + ": " + second;


        AttendanceTable row = new AttendanceTable(fullName, time, status);
        attendanceServices.createNewAttendance(row);
        Staff staff = (Staff) session.getAttribute("staff");
        Long StaffId = (Long) ((Staff) session.getAttribute("staff")).getStaffId();
        String department = (String) ((Staff) session.getAttribute("staff")).getDepartment();

        model.addAttribute("staff", staff);


        if(staff.getRole().equals(Role.REGULAR)){
            return "clockIn";
        }else{
            return "admin";
        }

    }

    @GetMapping("/allStaffAttendance")
    public String getListOfStaffAttendance(HttpServletRequest request, Model model){

        List<AttendanceTable> allAttendance = attendanceServices.attendance();
        model.addAttribute("all_Staff_attendance", allAttendance);

        return "attendance";

    }
}
