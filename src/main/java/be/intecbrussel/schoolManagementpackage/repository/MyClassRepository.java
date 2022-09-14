package be.intecbrussel.schoolManagementpackage.repository;

import be.intecbrussel.schoolManagementpackage.model.MyClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface MyClassRepository extends JpaRepository<MyClass,Long> {

}
