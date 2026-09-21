package springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import springboot.model.test;

public interface testRepository extends JpaRepository<test, Integer> {

}