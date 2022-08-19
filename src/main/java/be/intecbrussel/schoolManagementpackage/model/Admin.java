package be.intecbrussel.schoolManagementpackage.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String userName;
    private String password;
    @OneToMany
   private Set<Student> students;
    @OneToMany
    private Set<Teacher> teachers;
    @OneToMany
    private Set<MyClass> myClasses;

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    public Set<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(Set<Teacher> teachers) {
        this.teachers = teachers;
    }

    public Set<MyClass> getMyClasses() {
        return myClasses;
    }

    public void setMyClasses(Set<MyClass> myClasses) {
        this.myClasses = myClasses;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
