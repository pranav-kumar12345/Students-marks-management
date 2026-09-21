package springboot.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.springframework.stereotype.Service;

import springboot.model.Student;
import springboot.model.studentMarks;
import springboot.model.test;
import springboot.repository.StudentRepository;
import springboot.repository.studentMarksRepository;
import springboot.repository.testRepository;

@Service
public class ExcelService {

    private final studentMarksRepository marksRepository;
    private final StudentRepository studentRepository;
    private final testRepository testRepository;


    // ======================================================
    // CONSTRUCTOR
    // ======================================================

    public ExcelService(
            studentMarksRepository marksRepository,
            StudentRepository studentRepository,
            testRepository testRepository) {

        this.marksRepository = marksRepository;
        this.studentRepository = studentRepository;
        this.testRepository = testRepository;
    }


    // ======================================================
    // GENERATE EXCEL
    // ======================================================

    public byte[] generateExcel() throws IOException {


        // Get all marks from database

        List<studentMarks> marks =
                marksRepository.findAll();


        // Create workbook

        Workbook workbook =
                new XSSFWorkbook();


        Sheet sheet =
                workbook.createSheet("Student Marks");


        // ==================================================
        // HEADER STYLE
        // ==================================================

        CellStyle headerStyle =
                workbook.createCellStyle();


        Font headerFont =
                workbook.createFont();

        headerFont.setBold(true);

        headerStyle.setFont(headerFont);

        headerStyle.setAlignment(
                HorizontalAlignment.CENTER
        );


        // ==================================================
        // HEADER
        // ==================================================

        String[] headers = {

            "Student ID",
            "Student Name",
            "Test Name",
            "Date",
            "Maximum Marks",
            "Telugu",
            "Hindi",
            "English",
            "Maths",
            "Science",
            "Social",
            "Total Marks",
            "Average",
            "Percentage"

        };


        Row headerRow =
                sheet.createRow(0);


        for (int i = 0;
             i < headers.length;
             i++) {

            Cell cell =
                    headerRow.createCell(i);

            cell.setCellValue(
                    headers[i]
            );

            cell.setCellStyle(
                    headerStyle
            );
        }


        // ==================================================
        // DATE FORMAT
        // ==================================================

        DateTimeFormatter dateFormatter =
                DateTimeFormatter.ofPattern(
                        "dd-MM-yyyy"
                );


        // ==================================================
        // PERCENTAGE STYLE
        // ==================================================

        CellStyle percentageStyle =
                workbook.createCellStyle();

        percentageStyle.setDataFormat(
                workbook.createDataFormat()
                        .getFormat("0.00%")
        );


        // ==================================================
        // DATA ROWS
        // ==================================================

        int rowNumber = 1;


        for (studentMarks mark : marks) {


            Row row =
                    sheet.createRow(
                            rowNumber++
                    );


            // ----------------------------------------------
            // STUDENT
            // ----------------------------------------------

            Student student =
                    studentRepository
                            .findById(
                                    mark.getStudentId()
                            )
                            .orElse(null);


            String studentName =
                    student != null
                            ? student.getStudentName()
                            : "Unknown";


            // ----------------------------------------------
            // TEST
            // ----------------------------------------------

            test testData =
                    testRepository
                            .findById(
                                    mark.getTestId()
                            )
                            .orElse(null);


            String testName =
                    testData != null
                            ? testData.getTestName()
                            : "Unknown";


            int maximumMarks =
                    testData != null
                            ? testData.getMaximumMarks()
                            : 0;


            // ----------------------------------------------
            // DATE
            // ----------------------------------------------

            String reportDate =
                    "";


            if (testData != null &&
                testData.getCreatedAt() != null) {

                LocalDateTime createdAt =
                        testData.getCreatedAt();

                LocalDate date =
                        createdAt.toLocalDate();

                reportDate =
                        date.format(
                                dateFormatter
                        );
            }


            // ----------------------------------------------
            // WRITE DATA
            // ----------------------------------------------

            row.createCell(0)
                    .setCellValue(
                            mark.getStudentId()
                    );


            row.createCell(1)
                    .setCellValue(
                            studentName
                    );


            row.createCell(2)
                    .setCellValue(
                            testName
                    );


            row.createCell(3)
                    .setCellValue(
                            reportDate
                    );


            row.createCell(4)
                    .setCellValue(
                            maximumMarks
                    );


            row.createCell(5)
                    .setCellValue(
                            mark.getTeluguMarks()
                    );


            row.createCell(6)
                    .setCellValue(
                            mark.getHindiMarks()
                    );


            row.createCell(7)
                    .setCellValue(
                            mark.getEnglishMarks()
                    );


            row.createCell(8)
                    .setCellValue(
                            mark.getMathsMarks()
                    );


            row.createCell(9)
                    .setCellValue(
                            mark.getScienceMarks()
                    );


            row.createCell(10)
                    .setCellValue(
                            mark.getSocialMarks()
                    );


            row.createCell(11)
                    .setCellValue(
                            mark.getTotalMarks()
                    );


            row.createCell(12)
                    .setCellValue(
                            mark.getAverageMarks()
                    );


            // ----------------------------------------------
            // PERCENTAGE
            // ----------------------------------------------

            Cell percentageCell =
                    row.createCell(13);


            percentageCell.setCellValue(
                    mark.getPercentage() / 100
            );


            percentageCell.setCellStyle(
                    percentageStyle
            );

        }


        // ==================================================
        // COLUMN WIDTH
        // ==================================================

        for (int i = 0;
             i < headers.length;
             i++) {

            sheet.autoSizeColumn(i);
        }


        // ==================================================
        // CREATE EXCEL FILE
        // ==================================================

        ByteArrayOutputStream outputStream =
                new ByteArrayOutputStream();


        workbook.write(
                outputStream
        );


        workbook.close();


        return outputStream.toByteArray();
    }
}