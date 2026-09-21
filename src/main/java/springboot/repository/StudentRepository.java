package springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import springboot.model.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {

}