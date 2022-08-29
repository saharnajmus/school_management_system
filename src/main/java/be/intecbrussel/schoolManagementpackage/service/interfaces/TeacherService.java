package be.intecbrussel.schoolManagementpackage.service.interfaces;

import be.intecbrussel.schoolManagementpackage.model.Teacher;

import java.util.List;

public interface TeacherService {
    public List<Teacher> getAllTeachers();
    public Teacher getTeacherById(long id);
    public void createTeacher(Teacher teacher);
    public boolean deleteTeacher(long id);
    public long countTeachers();
    public Teacher updateTeacher(long id , Teacher teacher);
}
