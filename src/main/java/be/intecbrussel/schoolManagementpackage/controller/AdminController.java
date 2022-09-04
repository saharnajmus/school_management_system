package be.intecbrussel.schoolManagementpackage.controller;

import be.intecbrussel.schoolManagementpackage.service.interfaces.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
    private StudentService studentService;

    @Autowired
    public AdminController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("homePage")
    public String ShowHomePage(Model model){
        model.addAttribute("totalStudents", studentService.countStudents());
        return "index";
    }
}
