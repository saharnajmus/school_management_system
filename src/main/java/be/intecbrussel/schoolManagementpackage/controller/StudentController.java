package be.intecbrussel.schoolManagementpackage.controller;

import be.intecbrussel.schoolManagementpackage.model.MyClass;
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
import java.util.*;

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

        List<Student> allStudents = studentService.getAllStudents();
        double pageSize = Math.ceil(allStudents.size() / 6.0);
        List<Student> studentData = studentService.getStudentDataByPageNum(1);
        if (allStudents.size() == 0) {
            pageSize = 1;
        }


        model.addAttribute("students", studentData);
        model.addAttribute("count", studentService.countStudents());
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("pageNumber", 1);


        return "studentPage";
    }

    @PostMapping("addNewStudent")
    public String addStudent(@ModelAttribute("student") Student student, @RequestParam("image") MultipartFile file) throws IOException {

        if (!file.isEmpty()) {
            StringBuilder fileNames = new StringBuilder();
            Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, file.getOriginalFilename());
            fileNames.append(file.getOriginalFilename());
            Files.write(fileNameAndPath, file.getBytes());

            student.setLink(file.getOriginalFilename());
        } else {
            student.setLink("noImage.jpg");
        }
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
        List<Student> allStudents = studentService.getAllStudents();
        double pageSize = Math.ceil(allStudents.size() / 6.0);
        if (allStudents.size() == 0) {
            pageSize = 1;
        }
        List<Student> studentsByName = studentService.getStudentsByName(student.getName());
        if (!(studentsByName.isEmpty())) {
            model.addAttribute("students", studentsByName);

        } else {
            List<Student> emptyList = new ArrayList<>();
            model.addAttribute("students", emptyList);
        }
        model.addAttribute("count", studentService.countStudents());
        model.addAttribute("pageNumber", 1);
        model.addAttribute("pageSize", pageSize);


        return "studentPage";
    }

    @GetMapping("sortedList/{pageNum}")
    public String sortStudentListByName(@PathVariable String pageNum, Model model) {
        List<Student> allStudents = studentService.getAllStudents();
        double pageSize = Math.ceil(allStudents.size() / 6.0);
        if (allStudents.size() == 0) {
            pageSize = 1;
        }
        int pageNumInInt;
        try {
            pageNumInInt = Integer.parseInt(pageNum);
        } catch (NumberFormatException nfe) {
            System.out.println("give a proper number");
            pageNumInInt = 1;
        }
        List<Student> studentData = studentService.getStudentDataByPageNum(pageNumInInt);
        List<Student> sortedList = studentService.sortStudentsByName(studentData);


        model.addAttribute("students", sortedList);
        model.addAttribute("count", studentService.countStudents());
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("pageNumber", pageNum);


        return "studentPage";
    }


    @GetMapping("sortedData/{pageNum}")
    public String sortStudentListByResult(@PathVariable String pageNum, Model model) {
        List<Student> allStudents = studentService.getAllStudents();
        double pageSize = Math.ceil(allStudents.size() / 6.0);
        if (allStudents.size() == 0) {
            pageSize = 1;
        }
        int pageNumInInt;
        try {
            pageNumInInt = Integer.parseInt(pageNum);
        } catch (NumberFormatException nfe) {
            System.out.println("give a proper number");
            pageNumInInt = 1;
        }
        List<Student> studentData = studentService.getStudentDataByPageNum(pageNumInInt);
        List<Student> sortedData = studentService.sortStudentsByResult(studentData);


        model.addAttribute("students", sortedData);
        model.addAttribute("count", studentService.countStudents());
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("pageNumber", pageNum);


        return "studentPage";
    }

    @GetMapping("barChart")
    public String getBarChart(Model model) {
        Map<Integer, Integer> graphData = new TreeMap<>();
        Integer newEnrollments = 1;

        List<Student> studentList = studentService.getAllStudents();
        for (Student student : studentList) {
            int year = student.getDate().getYear();


            if (graphData.containsKey(year)) {
                Integer value = graphData.get(year);
                value++;
                graphData.put(year, value);

            } else {
                graphData.put(year, 1);
            }
        }
        model.addAttribute("chartData", graphData);

        return "barChartExample";
    }

    @GetMapping("studentData/{pageNum}")
    public String showStudentDataByPageNumber(@PathVariable String pageNum, Model model) {
        List<Student> allStudents = studentService.getAllStudents();
        double pageSize = Math.ceil(allStudents.size() / 6.0);
        if (allStudents.size() == 0) {
            pageSize = 1;
        }

        int pageNumInInt;
        try {
            pageNumInInt = Integer.parseInt(pageNum);
        } catch (NumberFormatException nfe) {
            System.out.println("give a proper number");
            pageNumInInt = 1;
        }
        List<Student> studentData = studentService.getStudentDataByPageNum(pageNumInInt);

        model.addAttribute("students", studentData);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("pageNumber", pageNum);
        model.addAttribute("count", studentService.countStudents());

        return "studentPage";
    }

}
