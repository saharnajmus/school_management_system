package be.intecbrussel.schoolManagementpackage.controller;

import be.intecbrussel.schoolManagementpackage.model.Student;
import be.intecbrussel.schoolManagementpackage.service.interfaces.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Controller
public class StudentController {
    private StudentService studentService;
    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\images";

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("allStudents")
    public String showAllStudent(Model model) {
        model.addAttribute("students",studentService.getAllStudents());
        model.addAttribute("count", studentService.countStudents());
        return "studentPage";
    }

    @PostMapping("addNewStudent")
    public String addStudent(@ModelAttribute("student") Student student, @RequestParam("image") MultipartFile file) throws IOException {

       if(!file.isEmpty()) {
           StringBuilder fileNames = new StringBuilder();
           Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, file.getOriginalFilename());
           fileNames.append(file.getOriginalFilename());
           Files.write(fileNameAndPath, file.getBytes());

           student.setLink(file.getOriginalFilename());
       }
       else{
        student.setLink("noImage.jpg");}

          student.setDate(LocalDate.now());
        studentService.createStudent(student);
        return "redirect:/allStudents";
    }

    @PutMapping("updateStudent/{id}")
    public String updateStudent(@PathVariable long id, @ModelAttribute("student") Student student) {
        studentService.updateStudent(id, student);
        return "redirect:/allStudents";
    }


    @DeleteMapping("deleteStudent/{id}")
    public String deleteStudent(@PathVariable long id) {
        studentService.deleteStudent(id);
        return "redirect:/allStudents";

    }

    @GetMapping("searchByName")
    public String searchStudent(@ModelAttribute("student") Student student, Model model) {

        List<Student> studentsByName = studentService.getStudentsByName(student.getName());
        model.addAttribute("students", studentsByName);
        model.addAttribute("count", studentService.countStudents());
        return "studentPage";
    }
    @GetMapping("sortedList")
    public String sortStudentListByName(Model model){
        model.addAttribute("students" ,studentService.sortStudentsByName());
        return "studentPage";
    }
    @GetMapping("sortedData")
    public String sortStudentListByResult(Model model){
        model.addAttribute("students" ,studentService.sortStudentsByResult());
        return "studentPage";
    }
 @GetMapping("barChart")
    public String getBarChart(Model model){
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

        return "barChartExample";
 }

}
