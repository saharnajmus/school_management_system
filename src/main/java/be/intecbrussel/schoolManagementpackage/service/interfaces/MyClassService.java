package be.intecbrussel.schoolManagementpackage.service.interfaces;

import be.intecbrussel.schoolManagementpackage.model.MyClass;
import be.intecbrussel.schoolManagementpackage.model.Student;

import java.util.List;
import java.util.Set;

public interface MyClassService {
     List<MyClass> getAllClasses();
     MyClass getClassById(long id);
     void createClass(MyClass myClass);
     boolean deleteClass(long id);
     long countClasses();
     MyClass updateClass(long id , MyClass myClass);
     List<MyClass> getClassesByName(String name);
     List<MyClass> sortClassesByName(List<MyClass> classList);
     List<MyClass> getClassByPageNum(int pageNum);
     public List<MyClass> getClassList();

}
