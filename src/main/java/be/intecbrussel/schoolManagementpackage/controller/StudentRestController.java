package be.intecbrussel.schoolManagementpackage.controller;

import be.intecbrussel.schoolManagementpackage.model.Student;
import be.intecbrussel.schoolManagementpackage.service.interfaces.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.*;

@RestController
@RequestMapping("/student")
public class StudentRestController {
    private StudentService studentService;

    @Autowired
    public StudentRestController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/all")
    public ResponseEntity getAllStudents() {
        return ResponseEntity.ok(List.of(studentService.getAllStudents()));
    }

    @GetMapping("/{id}")
    public ResponseEntity getStudentById(@PathVariable long id) {
        Student student = studentService.getStudentById(id);
        return ResponseEntity.ok(student);
    }

    @PostMapping(value = "/add", consumes = {"application/json"})
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity addNewStudent(@RequestBody Student student) {
        studentService.createStudent(student);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping("/count")
    public ResponseEntity countStudents() {
        long totalStudents = studentService.countStudents();
        return ResponseEntity.ok("total number of Students: " + totalStudents);
    }
    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity updateStudent(@PathVariable long id,@RequestBody Student student) {
        Student updatedStudent =studentService.updateStudent(id, student);
        if(updatedStudent.getId()!=0){
            return new ResponseEntity(updatedStudent ,HttpStatus.OK);
        }
      else{
        return new  ResponseEntity(HttpStatus.NOT_FOUND);}
    }

    @DeleteMapping("/{id}")
    public ResponseEntity removeStudent(@PathVariable long id) {
        boolean isRemoved = studentService.deleteStudent(id);
        if (!isRemoved) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity("student with id " + id + " is removed " , HttpStatus.OK);
        }
    }


}
