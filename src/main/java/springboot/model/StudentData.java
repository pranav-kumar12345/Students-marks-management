package springboot.model;

public class StudentData {

	private int studentId;
	private String studentName;

	private String[] subjects = { "Telugu", "Hindi", "English", "Maths", "Science", "Social" };

	private int[] marks = new int[6];

	private int totalMarks;
	private double averageMarks;
	private double percentage;

	private int highestMarks;
	private int highestMarksIndex;

	private int lowestMarks;
	private int lowestMarksIndex;
	

	// Required by Spring Boot
	public StudentData() {

	}

	// Constructor
	public StudentData(int studentId, String studentName, int[] marks) {

		this.studentId = studentId;
		this.studentName = studentName;
		this.marks = marks;

		calculateResults();
	}

	// Calculate results
	public void calculateResults() {

		if (marks == null || marks.length == 0) {
			return;
		}

		totalMarks = 0;

		highestMarks = marks[0];
		lowestMarks = marks[0];

		highestMarksIndex = 0;
		lowestMarksIndex = 0;

		for (int i = 0; i < marks.length; i++) {

			totalMarks = totalMarks + marks[i];

			if (marks[i] > highestMarks) {

				highestMarks = marks[i];
				highestMarksIndex = i;
			}

			if (marks[i] < lowestMarks) {

				lowestMarks = marks[i];
				lowestMarksIndex = i;
			}
		}

		averageMarks = (double) totalMarks / marks.length;
		percentage = (double) totalMarks / (marks.length * 100) * 100;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String[] getSubjects() {
		return subjects;
	}

	public void setSubjects(String[] subjects) {
		this.subjects = subjects;
	}

	public int[] getMarks() {
		return marks;
	}

	public void setMarks(int[] marks) {
		this.marks = marks;
	}

	public int getTotalMarks() {
		return totalMarks;
	}

	public void setTotalMarks(int totalMarks) {
		this.totalMarks = totalMarks;
	}

	public double getAverageMarks() {
		return averageMarks;
	}

	public void setAverageMarks(double averageMarks) {
		this.averageMarks = averageMarks;
	}

	public int getHighestMarks() {
		return highestMarks;
	}

	public void setHighestMarks(int highestMarks) {
		this.highestMarks = highestMarks;
	}

	public int getHighestMarksIndex() {
		return highestMarksIndex;
	}

	public void setHighestMarksIndex(int highestMarksIndex) {
		this.highestMarksIndex = highestMarksIndex;
	}

	public int getLowestMarks() {
		return lowestMarks;
	}

	public void setLowestMarks(int lowestMarks) {
		this.lowestMarks = lowestMarks;
	}

	public int getLowestMarksIndex() {
		return lowestMarksIndex;
	}

	public void setLowestMarksIndex(int lowestMarksIndex) {
		this.lowestMarksIndex = lowestMarksIndex;
	}
	

	public double getPercentage() {
	    return percentage;
	}

	public void setPercentage(double percentage) {
	    this.percentage = percentage;
	}
}