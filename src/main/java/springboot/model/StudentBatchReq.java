package springboot.model;

import java.util.List;

public class StudentBatchReq {

    private int maximumMarks;
    private List<StudentData> students;

    public StudentBatchReq() {
    }

    public int getMaximumMarks() {
        return maximumMarks;
    }

    public void setMaximumMarks(int maximumMarks) {
        this.maximumMarks = maximumMarks;
    }

    public List<StudentData> getStudents() {
        return students;
    }

    public void setStudents(List<StudentData> students) {
        this.students = students;
    }
}