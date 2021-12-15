package com.example.attendanceApp.staff.model;


import lombok.Data;

import javax.persistence.*;


@Entity
@Table
@Data
public class AttendanceTable {

    @Id
    @SequenceGenerator(
            name = "attendance_sequence",
            sequenceName = "attendance_sequence",
            allocationSize = 1
    )

    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "attendance_sequence"
    )
    private Long id;


    @Column (nullable = false)
    private String fullName;

    @Column
    private String clockIn;

    @Column
    private String clockOut = "17:00:00";

    @Column
    private String attendanceFeedback;

    public AttendanceTable(String fullName, String clockIn, String attendanceFeedback) {
        this.fullName = fullName;
        this.clockIn = clockIn;
        this.attendanceFeedback = attendanceFeedback;

    }

    public AttendanceTable() {
    }
}
