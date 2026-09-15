package seleniumPro;

import java.util.*;

public class marksEntry {

	public static void main(String[] args) throws Exception {

		Scanner sc = new Scanner(System.in);

		int noOfStudents;

		while (true) {

			System.out.print("Enter number of students: ");

			if (sc.hasNextInt()) {

				noOfStudents = sc.nextInt();

				if (noOfStudents > 0) {
					break;
				}

				System.out.println("Number of students must be greater than 0.");

			} else {

				System.out.println("Please enter a valid number.");

				sc.next();
			}
		}
		// Manager will store all students
		StudentManager manager = new StudentManager();

		// ==========================================
		// STUDENT INPUT
		// ==========================================

		for (int i = 1; i <= noOfStudents; i++) {

			StudentData sd = new StudentData();

			// Student ID
			int stuId;

			while (true) {

				System.out.print("Enter Student ID : ");

				if (sc.hasNextInt()) {

					stuId = sc.nextInt();

					if (stuId <= 0) {

						System.out.println("Student ID must be greater than 0.");

						continue;
					}

					if (manager.isStudentIdExists(stuId)) {

						System.out.println("This Student ID already exists. Enter another ID.");

						continue;
					}

					break;

				} else {

					System.out.println("Student ID must contain numbers only.");

					sc.next();
				}
			}

			sd.setStudentId(stuId);
			
			sc.nextLine();
			// Student Name
			String stuName;
			while (true) {
				System.out.print("Enter Student Name : ");
				stuName = sc.nextLine().trim();
				if (stuName.matches("[a-zA-Z ]+")) {
					break;
				}

				System.out.println("Name should contain letters and spaces only.");
			}

			sd.setStudentName(stuName);

			// ==========================================
			// ENTER MARKS
			// ==========================================

			for (int j = 0; j < sd.getSubjects().length; j++) {

				int mark;

				while (true) {

					System.out.print(sd.getSubjects()[j] + ": ");

					if (sc.hasNextInt()) {

						mark = sc.nextInt();

						if (mark >= 0 && mark <= 100) {
							break;
						}

						System.out.println("Marks must be between 0 and 100.");

					} else {

						System.out.println("Please enter numbers only.");

						sc.next();
					}
				}

				sd.setMarks(j, mark);
			}

			// ==========================================
			// CALCULATE INDIVIDUAL RESULTS
			// ==========================================

			sd.calculateResults();

			// ==========================================
			// ADD STUDENT TO MANAGER
			// ==========================================

			manager.addStudent(sd);

			// ==========================================
			// DISPLAY INDIVIDUAL STUDENT DETAILS
			// ==========================================

			System.out.println();

			System.out.println("Student ID : " + sd.getStudentId());

			System.out.println("Student Name : " + sd.getStudentName());

			System.out.println("Marks : " + Arrays.toString(sd.getMarks()));

			System.out.println("Total Marks : " + sd.getTotal());

			System.out.println("Average Marks : " + sd.getAverage());

			System.out.println("Highest Marks : " + sd.getHighestMarks() + " -- Subject : "
					+ sd.getSubjects()[sd.getHighestIndex()]);

			System.out.println(
					"Lowest Marks : " + sd.getLowestMarks() + " -- Subject : " + sd.getSubjects()[sd.getLowestIndex()]);

			System.out.println("----------------------------");
		}

		System.out.println("\nOverall Class Details");

		manager.calculateClassPerformance();

		manager.calculateRanks();

		manager.subjectWiseTopper();

		System.out.println("\n===== ALL STUDENT DATA =====");

		for (StudentData student : manager.getStudents()) {

			System.out.println("ID: " + student.getStudentId());
			System.out.println("Name: " + student.getStudentName());
			System.out.println("Marks: " + Arrays.toString(student.getMarks()));
			System.out.println("Total: " + student.getTotal());
			System.out.println("Average: " + student.getAverage());

			ExcelWriter.writeToExcel(manager);

			System.out.println("----------------------------");
		}

		sc.close();
	}
}