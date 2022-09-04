package be.intecbrussel.schoolManagementpackage.service.interfaces;

import be.intecbrussel.schoolManagementpackage.model.Student;

import java.util.List;

public interface StudentService {
    public List<Student> getAllStudents();
    public Student getStudentById(long id);
    public void createStudent(Student student);
    public boolean deleteStudent(long id);
    public long countStudents();
    public Student updateStudent(long id , Student student);
    public List<Student> getStudentsByName(String name);
    public List<Student> sortStudentsByName();
    public List<Student> sortStudentsByResult();
}
