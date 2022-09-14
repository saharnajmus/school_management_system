package be.intecbrussel.schoolManagementpackage.controller;

import be.intecbrussel.schoolManagementpackage.model.MyClass;
import be.intecbrussel.schoolManagementpackage.model.Student;
import be.intecbrussel.schoolManagementpackage.model.Teacher;
import be.intecbrussel.schoolManagementpackage.service.interfaces.MyClassService;
import be.intecbrussel.schoolManagementpackage.service.interfaces.TeacherService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
public class TeacherController {
    private TeacherService teacherService;
    private MyClassService myClassService;
    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\images";


    @Autowired
    public TeacherController(TeacherService teacherService, MyClassService myClassService) {
        this.teacherService = teacherService;
        this.myClassService = myClassService;
    }

    @GetMapping("allTeachers")
    public String showAllTeachers(Model model) {

        List<Teacher> allTeachers = teacherService.getAllTeachers();
        double pageSize = Math.ceil(allTeachers.size() / 6.0);
        List<Teacher> teacherData = teacherService.getTeacherDataByPageNum(1);
        if (allTeachers.size() == 0) {
            pageSize = 1;
        }

        model.addAttribute("teachers", teacherData);
        model.addAttribute("classList" , myClassService.getClassList());
        model.addAttribute("countTeachers", teacherService.countTeachers());
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("pageNumber", 1);

        return "teacherPage";
    }

    @PostMapping("addNewTeacher")
    public String addTeacher(@ModelAttribute("teacher") Teacher teacher, @RequestParam("image") MultipartFile file) throws IOException {

        if (!file.isEmpty()) {
            StringBuilder fileNames = new StringBuilder();
            Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, file.getOriginalFilename());
            fileNames.append(file.getOriginalFilename());
            Files.write(fileNameAndPath, file.getBytes());

            teacher.setLink(file.getOriginalFilename());
        } else {
            teacher.setLink("noImage.jpg");
        }
        teacher.setDate(LocalDate.now());
        teacherService.createTeacher(teacher);
        return "redirect:/allTeachers";
    }

    @PutMapping("updateTeacher/{id}")
    public String updateTeacher(@PathVariable long id, @ModelAttribute("teacher") Teacher teacher) {
        teacherService.updateStudent(id, teacher);
        return "redirect:/allTeachers";
    }
    @DeleteMapping("deleteTeacher/{id}")
    public String deleteTeacher(@PathVariable long id) {
        teacherService.deleteTeacher(id);
        return "redirect:/allTeachers";

    }
    @GetMapping("searchTeacherByName")
    public String searchTeacher(@ModelAttribute("teacher") Teacher teacher, Model model) {
        List<Teacher> allTeachers = teacherService.getAllTeachers();
        double pageSize = Math.ceil(allTeachers.size() / 6.0);
        if (allTeachers.size() == 0) {
            pageSize = 1;
        }
        List<Teacher> teachersByName = teacherService.getTeachersByName(teacher.getName());
        if (!(teachersByName.isEmpty())) {
            model.addAttribute("teachers", teachersByName);

        } else {
            List<Teacher> emptyList = new ArrayList<>();
            model.addAttribute("teachers", emptyList);
        }
        model.addAttribute("countTeachers", teacherService.countTeachers());
        model.addAttribute("pageNumber", 1);
        model.addAttribute("pageSize", pageSize);


        return "teacherPage";
    }

    @GetMapping("sortedTeacherList/{pageNum}")
    public String sortTeacherListByName(@PathVariable String pageNum, Model model) {
        List<Teacher> allTeachers = teacherService.getAllTeachers();
        double pageSize = Math.ceil(allTeachers.size() / 6.0);
        if (allTeachers.size() == 0) {
            pageSize = 1;
        }
        int pageNumInInt;
        try {
            pageNumInInt = Integer.parseInt(pageNum);
        } catch (NumberFormatException nfe) {
            System.out.println("give a proper number");
            pageNumInInt = 1;
        }
        List<Teacher> teacherData = teacherService.getTeacherDataByPageNum(pageNumInInt);
        List<Teacher> sortedList = teacherService.sortTeachersByName(teacherData);


        model.addAttribute("teachers", sortedList);
        model.addAttribute("countTeachers", teacherService.countTeachers());
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("pageNumber", pageNum);

        return "teacherPage";
    }

    @GetMapping("teacherData/{pageNum}")
    public String showStudentDataByPageNumber(@PathVariable String pageNum, Model model) {
        List<Teacher> allTeachers = teacherService.getAllTeachers();
        double pageSize = Math.ceil(allTeachers.size() / 6.0);
        if (allTeachers.size() == 0) {
            pageSize = 1;
        }

        int pageNumInInt;
        try {
            pageNumInInt = Integer.parseInt(pageNum);
        } catch (NumberFormatException nfe) {
            System.out.println("give a proper number");
            pageNumInInt = 1;
        }
        List<Teacher> teacherData = teacherService.getTeacherDataByPageNum(pageNumInInt);

        model.addAttribute("teachers", teacherData);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("pageNumber", pageNum);
        model.addAttribute("countTeachers", teacherService.countTeachers());

        return "teacherPage";
    }



}
