package com.example.attendanceApp.staff.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;



@Entity
@Table
@Data
public class Staff {

    @Id
    @SequenceGenerator(
        name = "staff_sequence",
        sequenceName = "staff_sequence",
        allocationSize = 1
    )

    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "staff_sequence"
    )
    private Long id;

    @Column(nullable = false, unique = true)
    private Long staffId;

    @Column(nullable = false, length = 30)
    private String firstName;

    @Column(nullable = false, length = 30)
    private String lastName;

    @Column(nullable = false, unique = true, length = 45)
    private String email;

    @Column(nullable = false, length = 64)
    private String password;


    @Column(nullable = false, length = 30)
    private String department;

    @Column(nullable = false)
    private Role role;

    public Staff(Long staffId, String firstName, String lastName, String email, String password, String department, Role role) {
        this.staffId = staffId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.department = department;
        this.role = role;
    }

    public Staff() {
    }
}
