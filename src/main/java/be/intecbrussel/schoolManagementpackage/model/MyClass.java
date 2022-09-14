package be.intecbrussel.schoolManagementpackage.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class MyClass {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    String name;
    char section;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Teacher teacher;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "myClass")
    private Set<Student> students;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getSection() {
        return section;
    }

    public void setSection(char section) {
        this.section = section;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
}
