package seleniumPro;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class StudentGUI extends JFrame {

	private static final long serialVersionUID = 1L;

	JTextField idField;
	JTextField nameField;

	JTextField teluguField;
	JTextField hindiField;
	JTextField englishField;
	JTextField mathsField;
	JTextField scienceField;
	JTextField socialField;

	JButton addButton;
	JButton openExcelButton;
	JButton saveExcelButton;

	JTextArea resultArea;

	StudentManager manager = new StudentManager();

	public StudentGUI() {

		setTitle("Student Marks Management System");
		setSize(800, 750);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setLayout(new BorderLayout(10, 10));

		// ================= INPUT PANEL =================

		JPanel inputPanel = new JPanel();
		inputPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		inputPanel.setLayout(new GridLayout(8, 2, 10, 10));

		// Student ID
		inputPanel.add(new JLabel("Student ID:"));
		idField = new JTextField();
		inputPanel.add(idField);

		// Student Name
		inputPanel.add(new JLabel("Student Name:"));
		nameField = new JTextField();
		inputPanel.add(nameField);

		// Telugu
		inputPanel.add(new JLabel("Telugu:"));
		teluguField = new JTextField();
		inputPanel.add(teluguField);

		// Hindi
		inputPanel.add(new JLabel("Hindi:"));
		hindiField = new JTextField();
		inputPanel.add(hindiField);

		// English
		inputPanel.add(new JLabel("English:"));
		englishField = new JTextField();
		inputPanel.add(englishField);

		// Maths
		inputPanel.add(new JLabel("Maths:"));
		mathsField = new JTextField();
		inputPanel.add(mathsField);

		// Science
		inputPanel.add(new JLabel("Science:"));
		scienceField = new JTextField();
		inputPanel.add(scienceField);

		// Social
		inputPanel.add(new JLabel("Social:"));
		socialField = new JTextField();
		inputPanel.add(socialField);

		// ================= TITLE =================

		JLabel titleLabel = new JLabel("STUDENT MARKS MANAGEMENT SYSTEM");

		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setFont(new Font("Arial", Font.BOLD, 20));

		add(titleLabel, BorderLayout.NORTH);

		// ================= INPUT PANEL =================

		add(inputPanel, BorderLayout.CENTER);

		// ================= RESULT PANEL =================

		JPanel resultPanel = new JPanel(new BorderLayout());
		resultPanel.setPreferredSize(new Dimension(250, 0));
		resultPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		JLabel resultLabel = new JLabel("RESULT");

		resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
		resultLabel.setFont(new Font("Arial", Font.BOLD, 16));

		resultPanel.add(resultLabel, BorderLayout.NORTH);

		resultArea = new JTextArea();
		resultArea.setEditable(false);
		resultArea.setLineWrap(true);
		resultArea.setWrapStyleWord(true);

		resultPanel.add(resultArea, BorderLayout.CENTER);

		add(resultPanel, BorderLayout.EAST);

		// ================= BUTTON PANEL =================

		JPanel buttonPanel = new JPanel();

		// Add Student button
		addButton = new JButton("Add Student");
		buttonPanel.add(addButton);

		// Open Excel button
		openExcelButton = new JButton("Open Excel Report");
		buttonPanel.add(openExcelButton);

		saveExcelButton = new JButton("Save Excel Report As...");
		buttonPanel.add(saveExcelButton);

		add(buttonPanel, BorderLayout.SOUTH);

		// ================= ADD STUDENT BUTTON =================

		addButton.addActionListener(e -> {

			// Get Student ID

			String id = idField.getText().trim();

			if (id.isEmpty()) {

				JOptionPane.showMessageDialog(this, "Student ID cannot be empty.");

				return;
			}

			if (!id.matches("\\d+")) {

				JOptionPane.showMessageDialog(this, "Student ID must contain numbers only.");

				return;
			}

			int studentId = Integer.parseInt(id);

			if (studentId <= 0) {

				JOptionPane.showMessageDialog(this, "Student ID must be greater than 0.");

				return;
			}

			if (manager.isStudentIdExists(studentId)) {

				JOptionPane.showMessageDialog(this, "This Student ID already exists.");

				return;
			}

			// ================= STUDENT NAME =================

			String name = nameField.getText().trim();

			if (name.isEmpty()) {

				JOptionPane.showMessageDialog(this, "Student Name cannot be empty.");

				return;
			}

			if (!name.matches("[a-zA-Z ]+")) {

				JOptionPane.showMessageDialog(this, "Student Name should contain letters and spaces only.");

				return;
			}

			// ================= MARKS =================

			int telugu = getValidMark(teluguField, "Telugu");

			int hindi = getValidMark(hindiField, "Hindi");

			int english = getValidMark(englishField, "English");

			int maths = getValidMark(mathsField, "Maths");

			int science = getValidMark(scienceField, "Science");

			int social = getValidMark(socialField, "Social");

			// Stop if any mark is invalid

			if (telugu == -1 || hindi == -1 || english == -1 || maths == -1 || science == -1 || social == -1) {

				return;
			}

			// ================= CREATE STUDENT =================

			StudentData sd = new StudentData();

			// Set ID and Name

			sd.setStudentId(studentId);

			sd.setStudentName(name);

			// Set Marks

			sd.setMarks(0, telugu);
			sd.setMarks(1, hindi);
			sd.setMarks(2, english);
			sd.setMarks(3, maths);
			sd.setMarks(4, science);
			sd.setMarks(5, social);

			// ================= CALCULATE RESULTS =================

			sd.calculateResults();

			// ================= ADD TO MANAGER =================

			manager.addStudent(sd);

			// ================= WRITE TO EXCEL =================

			try {

				ExcelWriter.writeToExcel(manager);

			} catch (Exception e1) {

				e1.printStackTrace();
			}

			// ================= SHOW SUCCESS MESSAGE =================

			JOptionPane.showMessageDialog(this, "Student added successfully!");

			// ================= DISPLAY RESULT =================

			resultArea.setText(

					"Student ID   : " + sd.getStudentId() + "\n" + "Student Name : " + sd.getStudentName() + "\n\n" +

							"MARKS\n" + "------------------------\n" +

							"Telugu       : " + sd.getMarks()[0] + "\n" + "Hindi        : " + sd.getMarks()[1] + "\n"
							+ "English      : " + sd.getMarks()[2] + "\n" + "Maths        : " + sd.getMarks()[3] + "\n"
							+ "Science      : " + sd.getMarks()[4] + "\n" + "Social       : " + sd.getMarks()[5]
							+ "\n\n" +

							"PERFORMANCE\n" + "------------------------\n" +

							"Total        : " + sd.getTotal() + "\n" + "Average      : " + sd.getAverage() + "\n" +

							"Highest Marks: " + sd.getHighestMarks() + "\n" + "Highest Subject: "
							+ sd.getSubjects()[sd.getHighestIndex()] + "\n" +

							"Lowest Marks : " + sd.getLowestMarks() + "\n" + "Lowest Subject: "
							+ sd.getSubjects()[sd.getLowestIndex()] + "\n\n" +

							"For more detailed performance,\n" + "refer to the Excel file.");

			// ================= CLEAR FIELDS =================

			idField.setText("");
			nameField.setText("");

			teluguField.setText("");
			hindiField.setText("");
			englishField.setText("");
			mathsField.setText("");
			scienceField.setText("");
			socialField.setText("");

			// ================= CONSOLE OUTPUT =================

			System.out.println("Student added successfully!");

			System.out.println("Total: " + sd.getTotal());

			System.out.println("Average: " + sd.getAverage());
		});

		// ================= OPEN EXCEL BUTTON =================

		openExcelButton.addActionListener(e -> {

			try {

				File file = new File("Students.xlsx");

				if (file.exists()) {

					Desktop.getDesktop().open(file);

				} else {

					JOptionPane.showMessageDialog(this, "Excel file not found.");
				}

			} catch (Exception ex) {

				JOptionPane.showMessageDialog(this, "Unable to open Excel file.");
			}
		});

		// for saving excel
		saveExcelButton.addActionListener(e -> {

			JFileChooser fileChooser = new JFileChooser();

			fileChooser.setDialogTitle("Save Excel Report");

			fileChooser.setSelectedFile(new File("StudentMarks_Report.xlsx"));

			int result = fileChooser.showSaveDialog(this);

			if (result == JFileChooser.APPROVE_OPTION) {

				File selectedFile = fileChooser.getSelectedFile();

				File sourceFile = new File("Students.xlsx");

				try {

					if (!sourceFile.exists()) {

						JOptionPane.showMessageDialog(this,
								"Students.xlsx file not found. Please add a student first.");

						return;
					}

					Files.copy(sourceFile.toPath(), selectedFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

					JOptionPane.showMessageDialog(this, "Excel report saved successfully!");

				} catch (Exception ex) {

					JOptionPane.showMessageDialog(this, "Unable to save Excel report.");

					ex.printStackTrace();
				}
			}
		});

		// ================= WINDOW SETTINGS =================

		setLocationRelativeTo(null);

		setVisible(true);
	}

	// ================= MARK VALIDATION METHOD =================

	private int getValidMark(JTextField field, String subject) {

		String text = field.getText().trim();

		if (text.isEmpty()) {

			JOptionPane.showMessageDialog(this, subject + " marks cannot be empty.");

			return -1;
		}

		if (!text.matches("\\d+")) {

			JOptionPane.showMessageDialog(this, subject + " marks must contain numbers only.");

			return -1;
		}

		int mark = Integer.parseInt(text);

		if (mark < 0 || mark > 100) {

			JOptionPane.showMessageDialog(this, subject + " marks must be between 0 and 100.");

			return -1;
		}

		return mark;
	}

	// ================= MAIN METHOD =================

	public static void main(String[] args) {

		new StudentGUI();
	}
}