package springboot.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import springboot.model.Student;
import springboot.model.StudentData;
import springboot.model.test;
import springboot.model.studentMarks;

import springboot.repository.StudentRepository;
import springboot.repository.testRepository;
import springboot.repository.studentMarksRepository;

@Service
public class StudentService {

    // ==================================================
    // OLD TEMPORARY STORAGE
    // ==================================================

    private List<StudentData> students = new ArrayList<>();


    // ==================================================
    // DATABASE REPOSITORIES
    // ==================================================

    private final StudentRepository studentRepository;

    private final testRepository testRepository;

    private final studentMarksRepository studentMarksRepository;


    // ==================================================
    // CONSTRUCTOR
    // ==================================================

    public StudentService(
            StudentRepository studentRepository,
            testRepository testRepository,
            studentMarksRepository studentMarksRepository) {

        this.studentRepository = studentRepository;
        this.testRepository = testRepository;
        this.studentMarksRepository = studentMarksRepository;
    }


    // ==================================================
    // TEST - SAVE TO MYSQL
    // ==================================================

    public test saveTest(test test) {

        return testRepository.save(test);
    }


    // ==================================================
    // STUDENT MARKS - SAVE TO MYSQL
    // ==================================================

    public studentMarks saveStudentMarks(studentMarks marks) {

        // ----------------------------------------------
        // 1. Check whether test exists
        // ----------------------------------------------

        test testData = testRepository.findById(marks.getTestId())
                .orElseThrow(() ->
                        new IllegalArgumentException("Test not found"));


        
        if (studentMarksRepository.existsByTestIdAndStudentId(
                marks.getTestId(),
                marks.getStudentId())) {

            throw new IllegalArgumentException(
                    "Marks already exist for this student in this test");
        }
        // ----------------------------------------------
        // 2. Check whether student exists
        // ----------------------------------------------

        Student student = studentRepository.findById(marks.getStudentId())
                .orElseThrow(() ->
                        new IllegalArgumentException("Student not found"));


        
        
        // ----------------------------------------------
        // 3. Get maximum marks from test
        // ----------------------------------------------

        int maximumMarks = testData.getMaximumMarks();


        // ----------------------------------------------
        // 4. Collect all subject marks
        // ----------------------------------------------

        int[] subjectMarks = {

            marks.getTeluguMarks(),
            marks.getHindiMarks(),
            marks.getEnglishMarks(),
            marks.getMathsMarks(),
            marks.getScienceMarks(),
            marks.getSocialMarks()
        };


        // ----------------------------------------------
        // 5. Validate marks
        // ----------------------------------------------

        for (int mark : subjectMarks) {

            if (mark < 0 || mark > maximumMarks) {

                throw new IllegalArgumentException(
                        "Marks must be between 0 and "
                        + maximumMarks);
            }
        }


        // ----------------------------------------------
        // 6. Calculate total, average and percentage
        // ----------------------------------------------

        marks.calculateResults(maximumMarks);


        // ----------------------------------------------
        // 7. Save marks to MySQL
        // ----------------------------------------------

        return studentMarksRepository.save(marks);
    }


    // ==================================================
    // GET STUDENTS FROM MYSQL
    // ==================================================

    public List<Student> getStudentsFromDatabase() {

        return studentRepository.findAll();
    }


    // ==================================================
    // GET MARKS BY STUDENT ID
    // ==================================================

    public List<studentMarks> getMarksByStudentId(int studentId) {

        return studentMarksRepository.findByStudentId(studentId);
    }


    // ==================================================
    // GET MARKS BY TEST ID
    // ==================================================

    public List<studentMarks> getMarksByTestId(int testId) {

        return studentMarksRepository.findByTestId(testId);
    }


    // ==================================================
    // OLD SYSTEM - GET ALL STUDENTS
    // ==================================================

    public List<StudentData> getStudents() {

        return students;
    }


    // ==================================================
    // OLD SYSTEM - GET STUDENT BY ID
    // ==================================================

    public StudentData getStudentById(int studentId) {

        for (StudentData student : students) {

            if (student.getStudentId() == studentId) {

                return student;
            }
        }

        return null;
    }


    // ==================================================
    // OLD SYSTEM - CHECK STUDENT ID
    // ==================================================

    public boolean isStudentIdExists(int studentId) {

        for (StudentData student : students) {

            if (student.getStudentId() == studentId) {

                return true;
            }
        }

        return false;
    }


    // ==================================================
    // OLD SYSTEM - ADD MULTIPLE STUDENTS
    // ==================================================

    public List<StudentData> addStudents(
            List<StudentData> newStudents,
            int maximumMarks) {


        // ----------------------------------------------
        // Validate student list
        // ----------------------------------------------

        if (newStudents == null || newStudents.isEmpty()) {

            throw new IllegalArgumentException(
                    "Student list cannot be empty");
        }


        // ----------------------------------------------
        // Validate maximum marks
        // ----------------------------------------------

        if (maximumMarks <= 0) {

            throw new IllegalArgumentException(
                    "Maximum marks must be greater than 0");
        }


        if (maximumMarks != 25
                && maximumMarks != 50
                && maximumMarks != 75
                && maximumMarks != 100) {

            throw new IllegalArgumentException(
                    "Maximum marks must be 25, 50, 75 or 100");
        }


        // ----------------------------------------------
        // Validate every student
        // ----------------------------------------------

        for (StudentData student : newStudents) {


            if (student == null) {

                throw new IllegalArgumentException(
                        "Student data cannot be null");
            }


            if (student.getStudentName() == null
                    || student.getStudentName().trim().isEmpty()) {

                throw new IllegalArgumentException(
                        "Student name cannot be empty");
            }


            if (student.getMarks() == null
                    || student.getMarks().length != 6) {

                throw new IllegalArgumentException(
                        "Exactly 6 subject marks are required");
            }


            // ------------------------------------------
            // Check duplicate ID
            // ------------------------------------------

            if (isStudentIdExists(student.getStudentId())) {

                throw new IllegalArgumentException(
                        "Student ID already exists: "
                        + student.getStudentId());
            }


            // ------------------------------------------
            // Validate marks
            // ------------------------------------------

            for (int i = 0;
                 i < student.getMarks().length;
                 i++) {

                int mark = student.getMarks()[i];

                if (mark < 0 || mark > maximumMarks) {

                    throw new IllegalArgumentException(
                            "Invalid mark for student ID "
                            + student.getStudentId()
                            + ". Marks must be between 0 and "
                            + maximumMarks);
                }
            }


            // ------------------------------------------
            // Calculate results
            // ------------------------------------------

            student.calculateResults();


            double percentage =
                    (double) student.getTotalMarks()
                    / (maximumMarks * 6)
                    * 100;

            student.setPercentage(percentage);
        }


        // ----------------------------------------------
        // Add students after validation
        // ----------------------------------------------

        for (StudentData student : newStudents) {

            students.add(student);
        }


        return newStudents;
    }


    // ==================================================
    // OLD SYSTEM - UPDATE STUDENT
    // ==================================================

    public StudentData updateStudent(
            int studentId,
            StudentData updatedStudent) {


        StudentData existingStudent =
                getStudentById(studentId);


        if (existingStudent == null) {

            return null;
        }


        existingStudent.setStudentName(
                updatedStudent.getStudentName());


        existingStudent.setMarks(
                updatedStudent.getMarks());


        existingStudent.calculateResults();


        return existingStudent;
    }


    // ==================================================
    // OLD SYSTEM - DELETE STUDENT
    // ==================================================

    public boolean deleteStudent(int studentId) {

        for (int i = 0; i < students.size(); i++) {

            if (students.get(i).getStudentId()
                    == studentId) {

                students.remove(i);

                return true;
            }
        }

        return false;
    }

    
    public List<studentMarks> searchStudentMarks(int studentId) {

        return studentMarksRepository
                .findByStudentId(studentId);
    }

    // ==================================================
    // OLD SYSTEM - RANKING
    // ==================================================

    public List<StudentData> getRankedStudents() {

        List<StudentData> rankedStudents =
                new ArrayList<>(students);


        // Bubble sort - highest total first

        for (int i = 0;
             i < rankedStudents.size() - 1;
             i++) {


            for (int j = 0;
                 j < rankedStudents.size() - 1 - i;
                 j++) {


                if (rankedStudents.get(j).getTotalMarks()
                        < rankedStudents.get(j + 1)
                                .getTotalMarks()) {


                    StudentData temp =
                            rankedStudents.get(j);


                    rankedStudents.set(
                            j,
                            rankedStudents.get(j + 1));


                    rankedStudents.set(
                            j + 1,
                            temp);
                }
            }
        }


        return rankedStudents;
    }


    // ==================================================
    // OLD SYSTEM - SUBJECT WISE TOPPER
    // ==================================================

    public Map<String, StudentData> subjectWiseTopper() {

        Map<String, StudentData> toppers =
                new HashMap<>();


        if (students.isEmpty()) {

            return toppers;
        }


        String[] subjects =
                students.get(0).getSubjects();


        for (int subjectIndex = 0;
             subjectIndex < subjects.length;
             subjectIndex++) {


            StudentData topper =
                    students.get(0);


            for (StudentData student : students) {


                if (student.getMarks()[subjectIndex]
                        > topper.getMarks()[subjectIndex]) {


                    topper = student;
                }
            }


            toppers.put(
                    subjects[subjectIndex],
                    topper);
        }


        return toppers;
    }


    
    public studentMarks updateStudentMarks(
            int testId,
            int studentId,
            studentMarks updatedMarks) {

        studentMarks existingMarks =
                studentMarksRepository.findByTestIdAndStudentId(
                        testId, studentId);

        if (existingMarks == null) {
            throw new IllegalArgumentException(
                    "Marks not found for this student in this test");
        }

        existingMarks.setTeluguMarks(updatedMarks.getTeluguMarks());
        existingMarks.setHindiMarks(updatedMarks.getHindiMarks());
        existingMarks.setEnglishMarks(updatedMarks.getEnglishMarks());
        existingMarks.setMathsMarks(updatedMarks.getMathsMarks());
        existingMarks.setScienceMarks(updatedMarks.getScienceMarks());
        existingMarks.setSocialMarks(updatedMarks.getSocialMarks());

        test testData = testRepository.findById(testId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Test not found"));

        int maximumMarks = testData.getMaximumMarks();

        existingMarks.calculateResults(maximumMarks);

        return studentMarksRepository.save(existingMarks);
    }
    // ==================================================
    // OLD SYSTEM - CLASS PERFORMANCE
    // ==================================================

    public Map<String, Double> getClassPerformance() {

        Map<String, Double> performance =
                new HashMap<>();


        if (students.isEmpty()) {

            return performance;
        }


        double totalMarks = 0;


        for (StudentData student : students) {

            totalMarks += student.getTotalMarks();
        }


        double averageTotal =
                totalMarks / students.size();


        performance.put(
                "Average Total Marks",
                averageTotal);


        return performance;
    }

    
    public void deleteStudentMarks(int testId, int studentId) {

        studentMarks existingMarks =
                studentMarksRepository.findByTestIdAndStudentId(
                        testId, studentId);

        if (existingMarks == null) {
            throw new IllegalArgumentException(
                    "Marks not found for this student in this test");
        }

        studentMarksRepository.delete(existingMarks);
    }
    
    public test getTestById(int testId) {
        return testRepository.findById(testId)
                .orElseThrow(() ->
                    new IllegalArgumentException("Test not found"));
    }

    // ==================================================
    // SAVE STUDENT TO MYSQL
    // ==================================================

    public Student saveStudent(Student student) {

        return studentRepository.save(student);
    }
}