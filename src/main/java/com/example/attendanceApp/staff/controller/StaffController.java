package com.example.attendanceApp.staff.controller;

import com.example.attendanceApp.staff.model.Role;
import com.example.attendanceApp.staff.model.Staff;
import com.example.attendanceApp.staff.repository.StaffRepository;
import com.example.attendanceApp.staff.services.StaffServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;

@Controller
public class StaffController {

    private StaffServices staffServices;
    private StaffRepository staffRepository;

    @Autowired
    public StaffController(StaffServices staffServices, StaffRepository staffRepository) {
        this.staffServices = staffServices;
        this.staffRepository = staffRepository;
    }

    @GetMapping("/")
    public String viewLoginPage(){

        return "homepage";
    }

    @PostMapping("/login")
    public String loginStaff(HttpServletRequest request, @RequestParam ("email") String email, @RequestParam("password") String password, Model model) {
        Boolean status = staffRepository.existsByEmail(email);

        Staff staff = staffRepository.findByEmail(email);

        String password1 = staffRepository.findByEmail(email).getPassword();

        System.out.println("this role is a " + staff.getRole());

        if(status && password.equals(password1)) {

            HttpSession session = request.getSession();
            session.setAttribute("email", staffRepository.findByEmail(email).getEmail());
            model.addAttribute("staff", staff);
            session.setAttribute("fullName", staff.getFirstName() + " " + staff.getLastName());
            session.setAttribute("staff", staff);

            if(staff.getRole() == Role.SUPER_ADMIN){
                return "admin";
            }else {


                return "clockIn";
            }
        }else{
            return "homepage";
        }

    }

    @PostMapping("/register")
    public String processRegistration(@ModelAttribute("staff") Staff staff,Model model){

        System.out.println(staff.getFirstName());
        System.out.println("staff email is" + staff.getEmail());
        if(staffServices.checkIfStaffExist(staff.getEmail())){

            model.addAttribute("test", "false");
            return "register";

        }else{
            staffServices.createNewStaff(staff);
            List<Staff> allStaff = staffServices.allStaff();
            model.addAttribute("all_Staff", allStaff);
            model.addAttribute("test", "true");
            return "register";
        }
    }

    @GetMapping("/showregistrationpage")
    public String registrationPage(HttpServletRequest request, Model model){
        HttpSession session = request.getSession();
        Staff staff = (Staff) session.getAttribute("staff");

        model.addAttribute("staff", staff);

        return "register";
    }

    @GetMapping("/allStaff")
    public String getListOfStaff(HttpServletRequest request, Model model){

        List<Staff> allStaff = staffServices.allStaff();
        model.addAttribute("all_Staff", allStaff);

        //model.addAttribute("staff", staff);

        return "all_staff";
    }



    @RequestMapping( "/delete/{email}")
    public String deletePost(HttpServletRequest request, @PathVariable String email, Model model) {
            staffServices.deleteStaff(email);

            List<Staff> allStaff = staffServices.allStaff();
            model.addAttribute("all_Staff", allStaff);

        return "all_staff";

    }


    @GetMapping("/edit_form/{email}")
    public String edit_form(HttpServletRequest request, @PathVariable String email, Model model){
        HttpSession session = request.getSession(false);

        Staff staff1 = staffServices.getStaffByPostEmail(email);
        session.setAttribute("staff1", staff1);
        model.addAttribute("staff", staff1);


        return "edit_form";
    }

    @RequestMapping("/edit")
    public String edit(HttpServletRequest request, @ModelAttribute("staff") Staff staff,Model model){


        HttpSession session = request.getSession(false);
        Staff staff1 = (Staff) session.getAttribute("staff1");
        staff1.setStaffId(staff.getStaffId());
        staff1.setFirstName(staff.getFirstName());
        staff1.setLastName(staff.getLastName());
        staff1.setRole(staff.getRole());
        staff1.setDepartment(staff.getDepartment());

        staffServices.createNewStaff(staff1);
        List<Staff> allStaff = staffServices.allStaff();
        model.addAttribute("all_Staff", allStaff);

        session.removeAttribute("staff1");


        return "redirect:/allStaff";
    }

    @RequestMapping("/logout")
    public String logOutUser(HttpServletRequest request){
        HttpSession session =request.getSession(false);
        session.invalidate();
        return "homepage";
    }


}
