package springboot.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import springboot.model.Student;
import springboot.model.StudentBatchReq;
import springboot.model.StudentData;
import springboot.model.studentMarks;
import springboot.model.test;
import springboot.service.StudentService;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // ==================================================
    // OLD STUDENT SYSTEM
    // ==================================================

    @GetMapping
    public List<StudentData> getStudents() {
        return studentService.getStudents();
    }

    @GetMapping("/{studentId}")
    public StudentData getStudentById(
            @PathVariable int studentId) {

        return studentService.getStudentById(studentId);
    }

    @PostMapping
    public List<StudentData> addStudents(
            @RequestBody StudentBatchReq request) {

        return studentService.addStudents(
                request.getStudents(),
                request.getMaximumMarks());
    }

    @PutMapping("/{studentId}")
    public StudentData updateStudent(
            @PathVariable int studentId,
            @RequestBody StudentData updatedStudent) {

        return studentService.updateStudent(
                studentId,
                updatedStudent);
    }

    @DeleteMapping("/{studentId}")
    public String deleteStudent(
            @PathVariable int studentId) {

        boolean deleted =
                studentService.deleteStudent(studentId);

        if (deleted) {
            return "Student deleted successfully";
        }

        return "Student not found";
    }

    // ==================================================
    // OLD RANKING / PERFORMANCE
    // ==================================================

    @GetMapping("/ranking")
    public List<StudentData> getRanking() {
        return studentService.getRankedStudents();
    }

    @GetMapping("/topper")
    public Map<String, StudentData> getSubjectWiseTopper() {
        return studentService.subjectWiseTopper();
    }

    @GetMapping("/performance")
    public Map<String, Double> getClassPerformance() {
        return studentService.getClassPerformance();
    }

    // ==================================================
    // MYSQL - STUDENTS
    // ==================================================

    @PostMapping("/db-test")
    public Student saveStudentToDatabase(
            @RequestBody Student student) {

        return studentService.saveStudent(student);
    }

    @GetMapping("/db")
    public List<Student> getStudentsFromDatabase() {

        return studentService.getStudentsFromDatabase();
    }

    // ==================================================
    // MYSQL - TESTS
    // ==================================================

    @PostMapping("/test-db")
    public test saveTestToDatabase(
            @RequestBody test test) {

        return studentService.saveTest(test);
    }

    // ==================================================
    // MYSQL - MARKS
    // ==================================================

    @PostMapping("/marks-db")
    public studentMarks saveStudentMarksToDatabase(
            @RequestBody studentMarks marks) {

        return studentService.saveStudentMarks(marks);
    }

    @GetMapping("/marks/student/{studentId}")
    public List<studentMarks> getMarksByStudentId(
            @PathVariable int studentId) {

        return studentService.getMarksByStudentId(studentId);
    }

    @GetMapping("/marks/test/{testId}")
    public List<studentMarks> getMarksByTestId(
            @PathVariable int testId) {

        return studentService.getMarksByTestId(testId);
    }

    @PutMapping("/marks/{testId}/{studentId}")
    public studentMarks updateStudentMarks(
            @PathVariable int testId,
            @PathVariable int studentId,
            @RequestBody studentMarks updatedMarks) {

        return studentService.updateStudentMarks(
                testId,
                studentId,
                updatedMarks);
    }

    @DeleteMapping("/marks/{testId}/{studentId}")
    public String deleteStudentMarks(
            @PathVariable int testId,
            @PathVariable int studentId) {

        studentService.deleteStudentMarks(
                testId,
                studentId);

        return "Student marks deleted successfully";
    }
    
    @GetMapping("/search/{studentId}")
    public List<studentMarks> searchStudentMarks(
            @PathVariable int studentId) {

        return studentService.searchStudentMarks(studentId);
    }
    
    @GetMapping("/test/{testId}")
    public test getTestById(@PathVariable int testId) {
        return studentService.getTestById(testId);
    }
}