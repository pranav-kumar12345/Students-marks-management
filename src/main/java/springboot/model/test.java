package springboot.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "tests")
public class test {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "test_id")
    private int testId;

    @Column(name = "test_name", nullable = false)
    private String testName;

    @Column(name = "maximum_marks", nullable = false)
    private int maximumMarks;

    @Column(name = "created_at")
    private LocalDateTime createdAt;


    // ==========================================
    // CONSTRUCTORS
    // ==========================================

    public test() {
    }


    public test(
            String testName,
            int maximumMarks) {

        this.testName = testName;
        this.maximumMarks = maximumMarks;
    }


    // ==========================================
    // GETTERS
    // ==========================================

    public int getTestId() {
        return testId;
    }


    public String getTestName() {
        return testName;
    }


    public int getMaximumMarks() {
        return maximumMarks;
    }


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }


    // ==========================================
    // SETTERS
    // ==========================================

    public void setTestId(int testId) {
        this.testId = testId;
    }


    public void setTestName(String testName) {
        this.testName = testName;
    }


    public void setMaximumMarks(int maximumMarks) {
        this.maximumMarks = maximumMarks;
    }


    public void setCreatedAt(
            LocalDateTime createdAt) {

        this.createdAt = createdAt;
    }


    // ==========================================
    // AUTOMATIC DATE
    // ==========================================

    @PrePersist
    protected void onCreate() {

        if (createdAt == null) {

            createdAt =
                    LocalDateTime.now();

        }
    }
}