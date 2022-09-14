package be.intecbrussel.schoolManagementpackage.controller;

import be.intecbrussel.schoolManagementpackage.model.MyClass;
import be.intecbrussel.schoolManagementpackage.model.Student;
import be.intecbrussel.schoolManagementpackage.service.interfaces.MyClassService;
import be.intecbrussel.schoolManagementpackage.service.interfaces.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MyClassController {
    private MyClassService myClassService;


    @Autowired
    public MyClassController(MyClassService myClassService) {
        this.myClassService = myClassService;
    }

    @GetMapping("allClasses")
    public String showAllClasses(Model model) {

        List<MyClass> allClasses = myClassService.getAllClasses();
        double pageSize = Math.ceil(allClasses.size() / 6.0);
        List<MyClass> classData = myClassService.getClassByPageNum(1);

        if (allClasses.size() == 0) {
            pageSize = 1;
        }

        model.addAttribute("classes", classData);
        model.addAttribute("countClasses", myClassService.countClasses());
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("pageNumber", 1);

        return "classPage";
    }

    @PostMapping("addNewClass")
    public String addClass(@ModelAttribute("myClass") MyClass myClass) {

        myClassService.createClass(myClass);
        return "redirect:allClasses";
    }

    @PutMapping("updateClass/{id}")
    public String updateStudent(@PathVariable long id, @ModelAttribute("myClass") MyClass myClass) {
        myClassService.updateClass(id, myClass);
        return "redirect:/allClasses";

    }

    @DeleteMapping("deleteClass/{id}")
    public String deleteClass(@PathVariable long id) {
        myClassService.deleteClass(id);
        return "redirect:/allClasses";

    }

    @GetMapping("searchClassByName")
    public String searchClass(@ModelAttribute("myClass") MyClass myClass, Model model) {
        List<MyClass> allClasses = myClassService.getAllClasses();
        double pageSize = Math.ceil(allClasses.size() / 6.0);
        if (allClasses.size() == 0) {
            pageSize = 1;
        }
        List<MyClass> classesByName = myClassService.getClassesByName(myClass.getName());
        if (!(classesByName.isEmpty())) {
            model.addAttribute("classes", classesByName);

        } else {
            List<Student> emptyList = new ArrayList<>();
            model.addAttribute("classes", emptyList);
        }
        model.addAttribute("countClasses", myClassService.countClasses());
        model.addAttribute("pageNumber", 1);
        model.addAttribute("pageSize", pageSize);
        return "classPage";
    }

    @GetMapping("sortedClassList/{pageNum}")
    public String sortClassListByName(@PathVariable String pageNum, Model model) {
        List<MyClass> allClasses = myClassService.getAllClasses();
        double pageSize = Math.ceil(allClasses.size() / 6.0);
        if (allClasses.size() == 0) {
            pageSize = 1;
        }
        int pageNumInInt;
        try {
            pageNumInInt = Integer.parseInt(pageNum);
        } catch (NumberFormatException nfe) {
            System.out.println("give a proper number");
            pageNumInInt = 1;
        }
        List<MyClass> classData = myClassService.getClassByPageNum(pageNumInInt);
        List<MyClass> sortedList = myClassService.sortClassesByName(classData);


        model.addAttribute("classes", sortedList);
        model.addAttribute("countClasses", myClassService.countClasses());
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("pageNumber", pageNum);

        return "classPage";
    }

    @GetMapping("classData/{pageNum}")
    public String showStudentDataByPageNumber(@PathVariable String pageNum, Model model) {
        List<MyClass> allClasses = myClassService.getAllClasses();
        double pageSize = Math.ceil(allClasses.size() / 6.0);
        if (allClasses.size() == 0) {
            pageSize = 1;
        }

        int pageNumInInt;
        try {
            pageNumInInt = Integer.parseInt(pageNum);
        } catch (NumberFormatException nfe) {
            System.out.println("give a proper number");
            pageNumInInt = 1;
        }
        List<MyClass> classData = myClassService.getClassByPageNum(pageNumInInt);

        model.addAttribute("classes", classData);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("pageNumber", pageNum);
        model.addAttribute("countClasses",myClassService.countClasses() );

        return "classPage";
    }

}
