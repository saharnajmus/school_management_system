package be.intecbrussel.schoolManagementpackage.controller;

import be.intecbrussel.schoolManagementpackage.model.Student;
import be.intecbrussel.schoolManagementpackage.service.interfaces.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class StudentController {
    private StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("allStudents")
    public String showAllStudent(Model model) {
        model.addAttribute("students", studentService.getAllStudents());
        model.addAttribute("count",studentService.countStudents());
        return "studentPage";
    }

    @PostMapping("addNewStudent")
    public String addStudent(@ModelAttribute("student") Student student){
        studentService.createStudent(student);
        return "redirect:/allStudents";
    }
    @PutMapping("updateStudent/{id}")
    public String updateStudent(@PathVariable long id, @ModelAttribute("student") Student student){
        studentService.updateStudent(id,student);
        return "redirect:/allStudents";
    }


    @DeleteMapping ("deleteStudent/{id}")
    public String deleteStudent(@PathVariable long id){
    studentService.deleteStudent(id);
    return "redirect:/allStudents";

    }
}
