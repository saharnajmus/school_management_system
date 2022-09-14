package be.intecbrussel.schoolManagementpackage.service.implementations;

import be.intecbrussel.schoolManagementpackage.model.MyClass;
import be.intecbrussel.schoolManagementpackage.model.Student;
import be.intecbrussel.schoolManagementpackage.model.Teacher;
import be.intecbrussel.schoolManagementpackage.repository.MyClassRepository;
import be.intecbrussel.schoolManagementpackage.repository.TeacherRepository;
import be.intecbrussel.schoolManagementpackage.service.interfaces.TeacherService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TeacherServiceImpl implements TeacherService {
    private TeacherRepository teacherRepository;

    @Autowired
    public TeacherServiceImpl(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @Override
    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    @Override
    public Teacher getTeacherById(long id) {
        Teacher teacher = teacherRepository.findById(id).get();
        return teacher;
    }

    @Override
    public void createTeacher(Teacher teacher) {
        teacherRepository.save(teacher);

    }

    @Override
    public boolean deleteTeacher(long id) {
        Optional<Teacher> teacher = teacherRepository.findById(id);
        if (teacher.isPresent()) {
            teacherRepository.deleteById(id);
            return true;
        } else return false;

    }

    @Override
    public long countTeachers() {
        return teacherRepository.count();
    }

    @Override
    public Teacher updateStudent(long id, Teacher teacher) {
        if (teacherRepository.findById(id).isPresent()) {
            Teacher existingTeacher = teacherRepository.findById(id).get();

            if (!(Strings.isBlank(teacher.getName()))) {
                existingTeacher.setName(teacher.getName());
            }
            if (!(Strings.isBlank(teacher.getAddress()))) {
                existingTeacher.setAddress(teacher.getAddress());
            }
            if (!(Strings.isBlank(teacher.getEmailAddress()))) {
                existingTeacher.setEmailAddress(teacher.getEmailAddress());
            }
            if (!(Strings.isBlank(teacher.getPhoneNumber())) && (teacher.getPhoneNumber().length() > 5)) {
            }

            {
                existingTeacher.setPhoneNumber(teacher.getPhoneNumber());
            }

            Teacher updatedTeacher = teacherRepository.save(existingTeacher);
            return updatedTeacher;
        } else {
            return new Teacher();
        }
    }

    @Override
    public List<Teacher> getTeachersByName(String name) {
        List<Teacher> teachers = new ArrayList<>();


        List<Teacher> existingTeachers = teacherRepository.findAll();
        for (Teacher teacher : existingTeachers) {
            String studentName = teacher.getName();
            if (!studentName.isBlank() && studentName.toLowerCase().contains(name.toLowerCase())) {
                teachers.add(teacher);
            }
        }
        return teachers;
    }

    @Override
    public List<Teacher> sortTeachersByName(List<Teacher> teacherList) {
        List<Teacher> sortedList =
                teacherList.stream().sorted(Comparator.comparing(Teacher::getName))
                        .collect(Collectors.toList());

        return sortedList;
    }



    @Override
    public List<Teacher> getTeacherDataByPageNum(int pageNum) {
        List<Teacher> teacherList = teacherRepository.findAll();
        int value = pageNum * 6;
        int index1 = 0;
        if(pageNum!=1){
            index1 = value/pageNum;
        }
        int index2 = index1+ 6;
        if(index2>teacherList.size()){
            index2 =teacherList.size();
        }
        return teacherList.subList(index1,index2);

    }


}

