package be.intecbrussel.schoolManagementpackage.service.interfaces;

import be.intecbrussel.schoolManagementpackage.model.MyClass;
import be.intecbrussel.schoolManagementpackage.model.Student;
import be.intecbrussel.schoolManagementpackage.model.Teacher;

import java.util.List;
import java.util.Set;

public interface TeacherService {
    public List<Teacher> getAllTeachers();
    public Teacher getTeacherById(long id);
    public void createTeacher(Teacher teacher);
    public boolean deleteTeacher(long id);
    public long countTeachers();
    public Teacher updateStudent(long id , Teacher teacher);
    public List<Teacher> getTeachersByName(String name);
    public List<Teacher> sortTeachersByName(List<Teacher> teacherList);
    public List<Teacher> getTeacherDataByPageNum(int pageNum);
}
