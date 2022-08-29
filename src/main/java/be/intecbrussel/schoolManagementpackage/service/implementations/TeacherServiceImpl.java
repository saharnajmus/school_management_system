package be.intecbrussel.schoolManagementpackage.service.implementations;

import be.intecbrussel.schoolManagementpackage.model.Teacher;
import be.intecbrussel.schoolManagementpackage.service.interfaces.TeacherService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {
    @Override
    public List<Teacher> getAllTeachers() {
        return null;
    }

    @Override
    public Teacher getTeacherById(long id) {
        return null;
    }

    @Override
    public void createTeacher(Teacher teacher) {

    }

    @Override
    public boolean deleteTeacher(long id) {
        return false;
    }

    @Override
    public long countTeachers() {
        return 0;
    }

    @Override
    public Teacher updateTeacher(long id, Teacher teacher) {
        return null;
    }
}
