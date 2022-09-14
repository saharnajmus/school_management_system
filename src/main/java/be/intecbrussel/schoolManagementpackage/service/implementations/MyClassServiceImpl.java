package be.intecbrussel.schoolManagementpackage.service.implementations;

import be.intecbrussel.schoolManagementpackage.model.MyClass;
import be.intecbrussel.schoolManagementpackage.model.Student;
import be.intecbrussel.schoolManagementpackage.repository.MyClassRepository;
import be.intecbrussel.schoolManagementpackage.service.interfaces.MyClassService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MyClassServiceImpl implements MyClassService {
   private MyClassRepository classRepository;

   @Autowired
    public MyClassServiceImpl(MyClassRepository classRepository) {
        this.classRepository = classRepository;
    }

    @Override
    public List<MyClass> getAllClasses() {
        return (List<MyClass>) classRepository.findAll();
    }

    @Override
    public MyClass getClassById(long id) {
       MyClass myClass = classRepository.findById(id).get();
        return myClass;
    }

    @Override
    public void createClass(MyClass myClass) {
       classRepository.save(myClass);

    }

    @Override
    public boolean deleteClass(long id) {
        Optional<MyClass> myClass = classRepository.findById(id);
        if (myClass.isPresent()) {
            classRepository.deleteById(id);
            return true;
        } else return false;
    }

    @Override
    public long countClasses() {
        return classRepository.count();
    }

    @Override
    public MyClass updateClass(long id, MyClass myClass) {

        if (classRepository.findById(id).isPresent()) {
            MyClass existingClass = classRepository.findById(id).get();

            if (!(Strings.isBlank(myClass.getName()))) {
                existingClass.setName(myClass.getName());
            }
            if(Character.isAlphabetic(myClass.getSection())){
                existingClass.setSection(myClass.getSection());
            }
            MyClass updatedClass = classRepository.save(existingClass);
            return updatedClass;
        }
        else return new MyClass();
    }

    @Override
    public List<MyClass> getClassesByName(String name) {
        List<MyClass> classes = new ArrayList<>();
        List<MyClass> existingClasses = (List<MyClass>) classRepository.findAll();
        for (MyClass myClass : existingClasses) {
            String className = myClass.getName();
            if (!className.isBlank() && className.toLowerCase().contains(name.toLowerCase())) {
                classes.add(myClass);
            }
        }
        return classes;    }

    @Override
    public List<MyClass> sortClassesByName(List<MyClass> classList) {
        List<MyClass> sortedList =
                classList.stream().sorted(Comparator.comparing(MyClass::getName))
                        .collect(Collectors.toList());

        return sortedList;
    }

    @Override
    public List<MyClass> getClassByPageNum(int pageNum) {
        List<MyClass> classList = (List<MyClass>) classRepository.findAll();
        int value = pageNum * 6;
        int index1 = 0;
        if(pageNum!=1){
            index1 = value/pageNum;
        }
        int index2 = index1+ 6;
        if(index2>classList.size()){
            index2 =classList.size();
        }
        return classList.subList(index1,index2);
    }

    @Override
    public List<MyClass> getClassList() {
        List<MyClass> classList = new ArrayList<>();
        List<MyClass> allClasses= classRepository.findAll();
        for(MyClass myClass:allClasses){
            if(!(classList.contains(myClass))){
            classList.add(myClass);
        }}
        return classList;
    }

}
