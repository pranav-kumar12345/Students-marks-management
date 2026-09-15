package seleniumPro;

import java.io.FileOutputStream;
import java.util.ArrayList;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

public class ExcelWriter {

	public static void writeToExcel(StudentManager sm) throws Exception {

		// Check whether student data exists
		if (sm.getStudents().isEmpty()) {

			System.out.println("No student data available.");
			return;
		}

		// =====================================================
		// CREATE WORKBOOK
		// =====================================================

		XSSFWorkbook workbook = new XSSFWorkbook();

		// =====================================================
		// GET STUDENT DATA
		// =====================================================

		ArrayList<StudentData> students = sm.getStudents();

		StudentData firstStudent = students.get(0);

		String[] subjects = firstStudent.getSubjects();

		// =====================================================
		// SHEET 1 : STUDENT DETAILS
		// =====================================================

		XSSFSheet studentSheet = workbook.createSheet("Student Details");

		// =====================================================
		// IMPORTANT NOTE
		// =====================================================

		// Merge A1:G2
		studentSheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 6));

		XSSFRow noteRow = studentSheet.createRow(0);

		Cell noteCell = noteRow.createCell(0);

		noteCell.setCellValue("IMPORTANT – SAVE YOUR REPORT\n"
				+ "This Excel file contains the student marks and performance details. "
				+ "For future reference, please use \"Save Excel Report As...\" "
				+ "in the application to save a copy with your preferred name and location.");

		// =====================================================
		// NOTE STYLE
		// =====================================================

		CellStyle noteStyle = workbook.createCellStyle();

		// Yellow background
		noteStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
		noteStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		// Alignment
		noteStyle.setAlignment(HorizontalAlignment.CENTER);
		noteStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		// Wrap text
		noteStyle.setWrapText(true);

		// Border
		noteStyle.setBorderTop(BorderStyle.THIN);
		noteStyle.setBorderBottom(BorderStyle.THIN);
		noteStyle.setBorderLeft(BorderStyle.THIN);
		noteStyle.setBorderRight(BorderStyle.THIN);

		// =====================================================
		// NOTE FONT
		// =====================================================

		Font noteFont = workbook.createFont();

		noteFont.setColor(IndexedColors.BLACK.getIndex());
		noteFont.setBold(true);
		noteFont.setFontHeightInPoints((short) 11);

		noteStyle.setFont(noteFont);

		noteCell.setCellStyle(noteStyle);

		// Set note row heights
		studentSheet.getRow(0).setHeightInPoints(35);
		studentSheet.createRow(1).setHeightInPoints(35);

		// =====================================================
		// CREATE HEADER
		// =====================================================

		String[] headerData = new String[15];

		headerData[0] = "ID";
		headerData[1] = "Name";

		// Subject headers
		for (int i = 0; i < subjects.length; i++) {

			headerData[i + 2] = subjects[i];
		}

		headerData[8] = "Total";
		headerData[9] = "Average";
		headerData[10] = "Highest Marks";
		headerData[11] = "Highest Subject";
		headerData[12] = "Lowest Marks";
		headerData[13] = "Lowest Subject";
		headerData[14] = "Rank";

		// =====================================================
		// WRITE HEADER INTO EXCEL
		// =====================================================

		// Row 4 in Excel = index 3 in Java
		XSSFRow headerRow = studentSheet.createRow(3);

		for (int i = 0; i < headerData.length; i++) {

			headerRow.createCell(i).setCellValue(headerData[i]);
		}

		// =====================================================
		// WRITE ALL STUDENT DATA
		// =====================================================

		for (int i = 0; i < students.size(); i++) {

			StudentData student = students.get(i);

			// One student = one Excel row
			// Starting from Excel row 5
			XSSFRow row = studentSheet.createRow(i + 4);

			// -------------------------------------------------
			// ID
			// -------------------------------------------------

			row.createCell(0).setCellValue(student.getStudentId());

			// -------------------------------------------------
			// NAME
			// -------------------------------------------------

			row.createCell(1).setCellValue(student.getStudentName());

			// -------------------------------------------------
			// SUBJECT MARKS
			// -------------------------------------------------

			for (int j = 0; j < student.getMarks().length; j++) {

				row.createCell(j + 2).setCellValue(student.getMarks()[j]);
			}

			// -------------------------------------------------
			// TOTAL
			// -------------------------------------------------

			row.createCell(8).setCellValue(student.getTotal());

			// -------------------------------------------------
			// AVERAGE
			// -------------------------------------------------

			row.createCell(9).setCellValue(student.getAverage());

			// -------------------------------------------------
			// HIGHEST MARKS
			// -------------------------------------------------

			row.createCell(10).setCellValue(student.getHighestMarks());

			// Highest subject
			row.createCell(11).setCellValue(student.getSubjects()[student.getHighestIndex()]);

			// -------------------------------------------------
			// LOWEST MARKS
			// -------------------------------------------------

			row.createCell(12).setCellValue(student.getLowestMarks());

			// Lowest subject
			row.createCell(13).setCellValue(student.getSubjects()[student.getLowestIndex()]);

			// -------------------------------------------------
			// RANK
			// -------------------------------------------------

			row.createCell(14).setCellValue(i + 1);
		}

		// =====================================================
		// SHEET 2 : CLASS PERFORMANCE
		// =====================================================

		XSSFSheet classSheet = workbook.createSheet("Class Performance");

		// =====================================================
		// CLASS TOPPER
		// =====================================================

		/*
		 * calculateRanks() should be called before this method. Therefore
		 * students.get(0) is Rank 1.
		 */

		StudentData classTopper = students.get(0);

		XSSFRow topperTitle = classSheet.createRow(0);

		topperTitle.createCell(0).setCellValue("CLASS TOPPER");

		XSSFRow topperRow = classSheet.createRow(1);

		topperRow.createCell(0).setCellValue("ID");
		topperRow.createCell(1).setCellValue(classTopper.getStudentId());

		topperRow.createCell(2).setCellValue("Name");
		topperRow.createCell(3).setCellValue(classTopper.getStudentName());

		topperRow.createCell(4).setCellValue("Total");
		topperRow.createCell(5).setCellValue(classTopper.getTotal());

		// =====================================================
		// OVERALL HIGHEST / LOWEST MARKS
		// =====================================================

		int classHighestMarks = 0;

		int classLowestMarks = Integer.MAX_VALUE;

		StudentData highestStudent = null;

		StudentData lowestStudent = null;

		int highestSubjectIndex = 0;

		int lowestSubjectIndex = 0;

		// Check every student and every subject
		for (StudentData student : students) {

			for (int j = 0; j < student.getMarks().length; j++) {

				int mark = student.getMarks()[j];

				// Overall highest
				if (mark > classHighestMarks) {

					classHighestMarks = mark;

					highestStudent = student;

					highestSubjectIndex = j;
				}

				// Overall lowest
				if (mark < classLowestMarks) {

					classLowestMarks = mark;

					lowestStudent = student;

					lowestSubjectIndex = j;
				}
			}
		}

		// =====================================================
		// WRITE OVERALL HIGHEST
		// =====================================================

		XSSFRow highestTitle = classSheet.createRow(3);

		highestTitle.createCell(0).setCellValue("OVERALL HIGHEST MARK");

		XSSFRow highestRow = classSheet.createRow(4);

		highestRow.createCell(0).setCellValue("Marks");
		highestRow.createCell(1).setCellValue(classHighestMarks);

		highestRow.createCell(2).setCellValue("Student");
		highestRow.createCell(3).setCellValue(highestStudent.getStudentName());

		highestRow.createCell(4).setCellValue("Subject");
		highestRow.createCell(5).setCellValue(highestStudent.getSubjects()[highestSubjectIndex]);

		// =====================================================
		// WRITE OVERALL LOWEST
		// =====================================================

		XSSFRow lowestTitle = classSheet.createRow(6);

		lowestTitle.createCell(0).setCellValue("OVERALL LOWEST MARK");

		XSSFRow lowestRow = classSheet.createRow(7);

		lowestRow.createCell(0).setCellValue("Marks");
		lowestRow.createCell(1).setCellValue(classLowestMarks);

		lowestRow.createCell(2).setCellValue("Student");
		lowestRow.createCell(3).setCellValue(lowestStudent.getStudentName());

		lowestRow.createCell(4).setCellValue("Subject");
		lowestRow.createCell(5).setCellValue(lowestStudent.getSubjects()[lowestSubjectIndex]);

		// =====================================================
		// SUBJECT-WISE TOPPERS
		// =====================================================

		XSSFRow subjectTitle = classSheet.createRow(9);

		subjectTitle.createCell(0).setCellValue("SUBJECT-WISE TOPPERS");

		XSSFRow subjectHeader = classSheet.createRow(10);

		subjectHeader.createCell(0).setCellValue("Subject");
		subjectHeader.createCell(1).setCellValue("Topper");
		subjectHeader.createCell(2).setCellValue("Marks");

		int rowNumber = 11;

		// Outer loop = subjects
		// Inner loop = students
		for (int i = 0; i < subjects.length; i++) {

			int highestMark = 0;

			String topperName = "";

			for (StudentData student : students) {

				int currentMark = student.getMarks()[i];

				if (currentMark > highestMark) {

					highestMark = currentMark;

					topperName = student.getStudentName();
				}
			}

			XSSFRow row = classSheet.createRow(rowNumber);

			row.createCell(0).setCellValue(subjects[i]);

			row.createCell(1).setCellValue(topperName);

			row.createCell(2).setCellValue(highestMark);

			rowNumber++;
		}

		// =====================================================
		// AUTO SIZE COLUMNS
		// =====================================================

		for (int i = 0; i < headerData.length; i++) {

			studentSheet.autoSizeColumn(i);
		}

		for (int i = 0; i < 6; i++) {

			classSheet.autoSizeColumn(i);
		}

		// =====================================================
		// SAVE EXCEL FILE
		// =====================================================

		FileOutputStream fos = new FileOutputStream("Students.xlsx");

		workbook.write(fos);

		fos.close();

		workbook.close();

		System.out.println("All student data written to Excel successfully.");
	}
}