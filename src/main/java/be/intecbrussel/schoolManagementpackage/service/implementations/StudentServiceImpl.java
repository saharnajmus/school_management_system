package be.intecbrussel.schoolManagementpackage.service.implementations;

import be.intecbrussel.schoolManagementpackage.model.Student;
import be.intecbrussel.schoolManagementpackage.repository.StudentRepository;
import be.intecbrussel.schoolManagementpackage.service.interfaces.StudentService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class StudentServiceImpl implements StudentService {
    private StudentRepository studentRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Student getStudentById(long id) {
        Student student = studentRepository.findById(id).get();
        return student;
    }

    @Override
    public void createStudent(Student student) {
        studentRepository.save(student);

    }

    @Override
    public boolean deleteStudent(long id) {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isPresent()) {
            studentRepository.deleteById(id);
            return true;
        } else return false;

    }

    @Override
    public long countStudents() {
        return studentRepository.count();
    }

    @Override
    public Student updateStudent(long id, Student student) {
        if (studentRepository.findById(id).isPresent()) {
            Student existingStudent = studentRepository.findById(id).get();

            if (!(Strings.isBlank(student.getName()))) {
                existingStudent.setName(student.getName());
            }
            if (!(Strings.isBlank(student.getAddress()))) {
                existingStudent.setAddress(student.getAddress());
            }
            if (!(Strings.isBlank(student.getEmailAddress()))) {
                existingStudent.setEmailAddress(student.getEmailAddress());
            }
            if (!(Strings.isBlank(student.getPhoneNumber()))&&(student.getPhoneNumber().length()>5)){
            }

            {
                existingStudent.setPhoneNumber(student.getPhoneNumber());
            }
            if (student.getResult()>0.0 && student.getResult() <=100) {
                existingStudent.setResult(student.getResult());
            }
            Student updatedStudent = studentRepository.save(existingStudent);
            return updatedStudent;
        } else {
            return new Student();
        }
    }

    @Override
    public List<Student> getStudentsByName(String name) {
        List<Student> students = new ArrayList<>();
        List<Student> existingStudents = studentRepository.findAll();
        for (Student student : existingStudents) {
            String studentName = student.getName();
            if (!studentName.isBlank() && studentName.toLowerCase().contains(name.toLowerCase())) {
                students.add(student);
            }
        }
        return students;
    }

    @Override
    public List<Student> sortStudentsByName(List<Student> studentList) {
        List<Student> sortedList =
        studentList.stream().sorted(Comparator.comparing(Student::getName))
                .collect(Collectors.toList());

        return sortedList;
    }

    @Override
    public List<Student> sortStudentsByResult(List<Student> studentList) {
        List<Student> sortedList =
                studentList.stream().sorted(Comparator.comparing(Student::getResult).reversed())
                        .collect(Collectors.toList());

        return sortedList;
    }

    @Override
    public List<Student> getStudentDataByPageNum(int pageNum) {
        List<Student> studentList = studentRepository.findAll();
        int value = pageNum * 6;
        int index1 = 0;
        if(pageNum!=1){
            index1 = value/pageNum;
        }
        int index2 = index1+ 6;
        if(index2>studentList.size()){
            index2 =studentList.size();
        }
        return studentList.subList(index1,index2);

    }


}
