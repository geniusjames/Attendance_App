package com.example.attendanceApp.staff.services;

import com.example.attendanceApp.staff.model.AttendanceTable;
import com.example.attendanceApp.staff.model.Staff;
import com.example.attendanceApp.staff.repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttendanceServices {

    private final  AttendanceRepository attendanceRepository;

    @Autowired
    public AttendanceServices(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }


    public void createNewAttendance(AttendanceTable attendanceTable)
    {
        attendanceRepository.save(attendanceTable);

    }

    public List<AttendanceTable> attendance() {
        List<AttendanceTable> allAttendance = attendanceRepository.findAll();

        return allAttendance;
    }

}
