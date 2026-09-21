package springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import springboot.model.studentMarks;

public interface studentMarksRepository extends JpaRepository<studentMarks, Integer> {

    List<studentMarks> findByStudentId(int studentId);

    List<studentMarks> findByTestId(int testId);
    
    boolean existsByTestIdAndStudentId(int testId, int studentId);
    
    studentMarks findByTestIdAndStudentId(int testId, int studentId);
    
    void deleteByTestIdAndStudentId(int testId, int studentId);
}