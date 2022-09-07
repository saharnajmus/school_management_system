package be.intecbrussel.schoolManagementpackage.controller;

import be.intecbrussel.schoolManagementpackage.model.Student;
import be.intecbrussel.schoolManagementpackage.service.interfaces.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
        getBarChart(model);
        return "index";
    }
    public void getBarChart(Model model){
        Map<Integer, Integer> graphData = new TreeMap<>();
        Integer newEnrollments = 1;

        List<Student> studentList = studentService.getAllStudents();
        for(Student student:studentList){
            int year = student.getDate().getYear();


            if(graphData.containsKey(year)){
                Integer value= graphData.get(year);
                value++;
                graphData.put(year,value);

            }
            else{
                graphData.put(year,1);
            }
        }
        model.addAttribute("chartData" ,graphData);

    }

}
