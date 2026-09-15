package seleniumPro;

public class StudentData {

	private int studentId;
	private String studentName;

	private final String[] subjects = { "Telugu", "Hindi", "English", "Maths", "Science", "Social" };

	private int[] marks = new int[6];

	// Individual student results
	private int total;
	private double average;

	private int highestMarks;
	private int highestIndex;

	private int lowestMarks;
	private int lowestIndex;

	// -------------------------
	// Student ID
	// -------------------------

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public int getStudentId() {
		return studentId;
	}

	// -------------------------
	// Student Name
	// -------------------------

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getStudentName() {
		return studentName;
	}

	// -------------------------
	// Marks
	// -------------------------

	public void setMarks(int index, int mark) {
		marks[index] = mark;
	}

	public int[] getMarks() {
		return marks;
	}

	// -------------------------
	// Subjects
	// -------------------------

	public String[] getSubjects() {
		return subjects;
	}

	// -------------------------
	// Calculate Results
	// -------------------------

	public void calculateResults() {

		total = 0;
		highestMarks = 0;
		lowestMarks = Integer.MAX_VALUE;

		for (int i = 0; i < marks.length; i++) {

			total += marks[i];

			if (marks[i] > highestMarks) {
				highestMarks = marks[i];
				highestIndex = i;
			}

			if (marks[i] < lowestMarks) {
				lowestMarks = marks[i];
				lowestIndex = i;
			}
		}

		average = total / (double) marks.length;
	}
	//-----------------------------
	// Student_Marks_Entry
	//-----------------------------

	// -------------------------
	// Result Getters
	// -------------------------

	public int getTotal() {
		return total;
	}

	public double getAverage() {
		return average;
	}

	public int getHighestMarks() {
		return highestMarks;
	}

	public int getHighestIndex() {
		return highestIndex;
	}

	public int getLowestMarks() {
		return lowestMarks;
	}

	public int getLowestIndex() {
		return lowestIndex;
	}
}