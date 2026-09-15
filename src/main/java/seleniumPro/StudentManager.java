package seleniumPro;

import java.util.*;

public class StudentManager {

	// Stores all students
	private ArrayList<StudentData> students = new ArrayList<>();

	// ==========================================
	// ADD STUDENT
	// ==========================================

	public void addStudent(StudentData student) {

		students.add(student);
	}
	//===========================================
	//CHECK IF STUDENT EXISTS
	//===========================================
	public boolean isStudentIdExists(int id) {

	    for (StudentData student : students) {

	        if (student.getStudentId() == id) {
	            return true;
	        }
	    }

	    return false;
	}

	// ==========================================
	// GET ALL STUDENTS
	// ==========================================

	public ArrayList<StudentData> getStudents() {

		return students;
	}

	// ==========================================
	// CLASS HIGHEST AND LOWEST
	// ==========================================

	public void calculateClassPerformance() {

		int classHighestMarks = 0;
		int classLowestMarks = Integer.MAX_VALUE;

		StudentData highestStudent = null;
		StudentData lowestStudent = null;

		int highestSubjectIndex = 0;
		int lowestSubjectIndex = 0;

		// Go through every student
		for (StudentData student : students) {

			// Go through every subject
			for (int j = 0; j < student.getMarks().length; j++) {

				int mark = student.getMarks()[j];

				// Class highest
				if (mark > classHighestMarks) {

					classHighestMarks = mark;
					highestStudent = student;
					highestSubjectIndex = j;
				}

				// Class lowest
				if (mark < classLowestMarks) {

					classLowestMarks = mark;
					lowestStudent = student;
					lowestSubjectIndex = j;
				}
			}
		}

		// ==========================================
		// DISPLAY CLASS HIGHEST
		// ==========================================

		System.out.println("Overall Highest Marks : " + classHighestMarks);

		System.out.println("Student : " + highestStudent.getStudentName());

		System.out.println("Subject : " + highestStudent.getSubjects()[highestSubjectIndex]);

		System.out.println();

		// ==========================================
		// DISPLAY CLASS LOWEST
		// ==========================================

		System.out.println("Overall Lowest Marks : " + classLowestMarks);

		System.out.println("Student : " + lowestStudent.getStudentName());

		System.out.println("Subject : " + lowestStudent.getSubjects()[lowestSubjectIndex]);
	}

	public void calculateRanks() {

		for (int i = 0; i < students.size() - 1; i++) {

			for (int j = 0; j < students.size() - 1 - i; j++) {

				if (students.get(j).getTotal() < students.get(j + 1).getTotal()) {

					StudentData temp = students.get(j);

					students.set(j, students.get(j + 1));

					students.set(j + 1, temp);
				}
			}
		}

		// Display ranks
		for (int i = 0; i < students.size(); i++) {

			StudentData student = students.get(i);

			System.out
					.println("Rank " + (i + 1) + " : " + student.getStudentName() + " - Total: " + student.getTotal());
		}

	}

	public void subjectWiseTopper() {

		int[] subWiseMark = new int[students.get(0).getMarks().length];
		String[] subWiseNames = new String[students.get(0).getMarks().length];

		
		// Outer loop → subjects
		for (int i = 0; i < subWiseMark.length; i++) {

			int mark = 0;

			// Inner loop → every student
			for (StudentData stu : students) {

				int currentMark = stu.getMarks()[i];

				if (mark < currentMark) {
					mark = currentMark;
					subWiseNames[i]=stu.getStudentName();
				}
			}

			subWiseMark[i] = mark;
		}

		// Display results
		String[] subjects = students.get(0).getSubjects();

		for (int i = 0; i < subWiseMark.length; i++) {

			System.out.println(subjects[i] + " : " + subWiseMark[i]+"-"+subWiseNames[i]);
		}
	}
}