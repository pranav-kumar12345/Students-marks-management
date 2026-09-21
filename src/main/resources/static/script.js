// ======================================================
// Student Marks Management System
// Frontend JavaScript
// ======================================================


let students = [];


// ======================================================
// HTML ELEMENTS
// ======================================================

const notification =
    document.getElementById("notification");

const notificationIcon =
    document.getElementById("notificationIcon");

const notificationMessage =
    document.getElementById("notificationMessage");


const studentCount =
    document.getElementById("studentCount");


const testNameInput =
    document.getElementById("testName");

const maximumMarksInput =
    document.getElementById("maximumMarks");


const studentIdInput =
    document.getElementById("studentId");

const studentNameInput =
    document.getElementById("studentName");


const teluguInput =
    document.getElementById("telugu");

const hindiInput =
    document.getElementById("hindi");

const englishInput =
    document.getElementById("english");

const mathsInput =
    document.getElementById("maths");

const scienceInput =
    document.getElementById("science");

const socialInput =
    document.getElementById("social");


const clearBtn =
    document.getElementById("clearBtn");

const addStudentBtn =
    document.getElementById("addStudentBtn");


const emptyMessage =
    document.getElementById("emptyMessage");

const tableContainer =
    document.getElementById("tableContainer");

const studentTableBody =
    document.getElementById("studentTableBody");


const saveSection =
    document.getElementById("saveSection");

const saveStudentsBtn =
    document.getElementById("saveStudentsBtn");
	
	const searchStudentIdInput =
	    document.getElementById("searchStudentId");

	const searchStudentBtn =
	    document.getElementById("searchStudentBtn");

	const clearSearchBtn =
	    document.getElementById("clearSearchBtn");

	const searchResults =
	    document.getElementById("searchResults");

	const searchResultsBody =
	    document.getElementById("searchResultsBody");


// ======================================================
// CHECK HTML ELEMENTS
// ======================================================

const requiredElements = {

    testName: testNameInput,

    maximumMarks: maximumMarksInput,

    studentId: studentIdInput,

    studentName: studentNameInput,

    telugu: teluguInput,

    hindi: hindiInput,

    english: englishInput,

    maths: mathsInput,

    science: scienceInput,

    social: socialInput,

    addStudentBtn: addStudentBtn,

    studentTableBody: studentTableBody

};


const missingElements =
    Object.entries(requiredElements)
        .filter(([name, element]) => !element)
        .map(([name]) => name);


if (missingElements.length > 0) {

    console.error(
        "Missing HTML elements:",
        missingElements.join(", ")
    );

}


// ======================================================
// SHOW NOTIFICATION
// ======================================================

function showNotification(message, type = "success") {

    if (!notification || !notificationMessage) {
        alert(message);
        return;
    }

    // Set message
    notificationMessage.textContent = message;

    // Set icon
    if (notificationIcon) {
        notificationIcon.textContent =
            type === "success" ? "✓" : "!";
    }

    // Remove hidden class
    notification.classList.remove("hidden");

    // Remove previous notification type
    notification.classList.remove("success");
    notification.classList.remove("error");

    // Add current type
    notification.classList.add(type);

    // Make sure it is visible
    notification.style.display = "flex";

    // Clear previous timer
    if (window.notificationTimer) {
        clearTimeout(window.notificationTimer);
    }

    // Hide after 3 seconds
    window.notificationTimer = setTimeout(function () {

        notification.classList.add("hidden");
        notification.style.display = "none";

    }, 3000);
}
// ======================================================
// GET MARKS
// ======================================================

function getMarks() {

    return [
        Number(teluguInput.value),
        Number(hindiInput.value),
        Number(englishInput.value),
        Number(mathsInput.value),
        Number(scienceInput.value),
        Number(socialInput.value)
    ];

}

// ======================================================
// UPDATE MARK LIMIT
// ======================================================

function updateMarkLimit() {

    const maximumMarks =
        Number(maximumMarksInput.value);

    const markInputs = [
        teluguInput,
        hindiInput,
        englishInput,
        mathsInput,
        scienceInput,
        socialInput
    ];

    markInputs.forEach(function (input) {

        if (maximumMarks > 0) {

            input.max = maximumMarks;

            // Clear mark if it is greater than maximum
            if (
                input.value !== "" &&
                Number(input.value) > maximumMarks
            ) {

                input.value = "";

            }

        } else {

            input.removeAttribute("max");

        }

    });

}


// ======================================================
// WHEN MAXIMUM MARKS CHANGES
// ======================================================

if (maximumMarksInput) {

    maximumMarksInput.addEventListener(
        "change",
        updateMarkLimit
    );

}

// ======================================================
// VALIDATE MARKS
// ======================================================

function validateMarks(marks, maximumMarks) {

    const subjectNames = [
        "Telugu",
        "Hindi",
        "English",
        "Maths",
        "Science",
        "Social"
    ];

    for (let i = 0; i < marks.length; i++) {

        const mark = marks[i];

        // Empty / invalid value
        if (!Number.isFinite(mark)) {

            return `Please enter marks for ${subjectNames[i]}.`;

        }

        // Below minimum
        if (mark < 0) {

            return `${subjectNames[i]} marks cannot be negative.`;

        }

        // Above maximum
        if (mark > maximumMarks) {

            return `${subjectNames[i]} marks cannot be greater than ${maximumMarks}.`;

        }
    }

    return null;
}


// ======================================================
// CLEAR STUDENT FIELDS
// ======================================================

function clearStudentFields() {

    studentIdInput.value = "";

    studentNameInput.value = "";

    teluguInput.value = "";

    hindiInput.value = "";

    englishInput.value = "";

    mathsInput.value = "";

    scienceInput.value = "";

    socialInput.value = "";


    studentIdInput.focus();

}


// ======================================================
// CLEAR BUTTON
// ======================================================

if (clearBtn) {

    clearBtn.addEventListener(
        "click",
        function () {

            clearStudentFields();

        }
    );

}


// ======================================================
// ADD STUDENT
// ======================================================

if (addStudentBtn) {

    addStudentBtn.addEventListener(
        "click",
        function () {


            // ------------------------------------------
            // CHECK REQUIRED ELEMENTS
            // ------------------------------------------

            if (
                !testNameInput ||
                !maximumMarksInput ||
                !studentIdInput ||
                !studentNameInput
            ) {

                showNotification(
                    "Form configuration error. Check index.html.",
                    "error"
                );

                return;

            }


            // ------------------------------------------
            // READ VALUES
            // ------------------------------------------

            const testName =
                testNameInput.value.trim();


            const maximumMarks =
                Number(maximumMarksInput.value);


            const studentId =
                Number(studentIdInput.value);


            const studentName =
                studentNameInput.value.trim();


            // ------------------------------------------
            // TEST NAME VALIDATION
            // ------------------------------------------

            if (testName === "") {

                showNotification(
                    "Please enter Test Name.",
                    "error"
                );

                testNameInput.focus();

                return;

            }


            // ------------------------------------------
            // MAXIMUM MARKS VALIDATION
            // ------------------------------------------

            if (
                !maximumMarksInput.value ||
                !Number.isFinite(maximumMarks) ||
                maximumMarks <= 0
            ) {

                showNotification(
                    "Please select valid Maximum Marks.",
                    "error"
                );

                maximumMarksInput.focus();

                return;

            }


            // ------------------------------------------
            // STUDENT ID VALIDATION
            // ------------------------------------------

            if (
                !studentIdInput.value ||
                !Number.isInteger(studentId) ||
                studentId <= 0
            ) {

                showNotification(
                    "Please enter a valid Student ID.",
                    "error"
                );

                studentIdInput.focus();

                return;

            }


            // ------------------------------------------
            // STUDENT NAME VALIDATION
            // ------------------------------------------

            if (studentName === "") {

                showNotification(
                    "Please enter Student Name.",
                    "error"
                );

                studentNameInput.focus();

                return;

            }


            // ------------------------------------------
            // DUPLICATE ID IN CURRENT TEST
            // ------------------------------------------

            const duplicate =
                students.some(
                    function (student) {

                        return (
                            student.studentId ===
                            studentId
                        );

                    }
                );


            if (duplicate) {

                showNotification(
                    "Student ID already exists in this test.",
                    "error"
                );

                studentIdInput.focus();

                return;

            }


			// ======================================================
			// GET MARKS
			// ======================================================

			const marks = getMarks();

			const marksError =
			    validateMarks(
			        marks,
			        maximumMarks
			    );


			// ======================================================
			// SHOW MARKS ERROR
			// ======================================================

			if (marksError) {

			    showNotification(
			        marksError,
			        "error"
			    );

			    return;
			}


            // ------------------------------------------
            // CALCULATE RESULTS
            // ------------------------------------------

            const totalMarks =
                marks.reduce(
                    function (sum, mark) {

                        return sum + mark;

                    },
                    0
                );


            const averageMarks =
                totalMarks / 6;


            const percentage =
                (
                    totalMarks /
                    (maximumMarks * 6)
                ) * 100;


            // ------------------------------------------
            // CREATE STUDENT OBJECT
            // ------------------------------------------

            const student = {

                studentId: studentId,

                studentName: studentName,

                testName: testName,

                maximumMarks: maximumMarks,

                marks: marks,

                totalMarks: totalMarks,

                averageMarks: averageMarks,

                percentage: percentage

            };


            // ------------------------------------------
            // ADD TO ARRAY
            // ------------------------------------------

            students.push(student);


            // ------------------------------------------
            // DISPLAY TABLE
            // ------------------------------------------

            displayStudents();


            // ------------------------------------------
            // CLEAR STUDENT DETAILS
            // ------------------------------------------

            clearStudentFields();


            // ------------------------------------------
            // SUCCESS MESSAGE
            // ------------------------------------------

            showNotification(
                "Student added successfully."
            );

        }
    );

}



// ======================================================
// DISPLAY STUDENTS
// ======================================================

function displayStudents() {

    if (!studentTableBody) {
        console.error("studentTableBody not found.");
        return;
    }

    // Clear existing table rows
    studentTableBody.innerHTML = "";


    // ==================================================
    // NO STUDENTS
    // ==================================================

    if (students.length === 0) {

        if (emptyMessage) {
            emptyMessage.classList.remove("hidden");
        }

        if (tableContainer) {
            tableContainer.classList.add("hidden");
        }

        if (saveSection) {
            saveSection.classList.add("hidden");
        }

        updateStudentCount();

        return;
    }


    // ==================================================
    // STUDENTS EXIST
    // ==================================================

    if (emptyMessage) {
        emptyMessage.classList.add("hidden");
    }

    if (tableContainer) {
        tableContainer.classList.remove("hidden");
    }

    if (saveSection) {
        saveSection.classList.remove("hidden");
    }


    // ==================================================
    // CREATE TABLE ROWS
    // ==================================================

    students.forEach(function (student) {

        const row = document.createElement("tr");


        row.innerHTML = `

            <td>
                ${escapeHtml(student.testName)}
            </td>

            <td>
                ${student.studentId}
            </td>

            <td>
                ${escapeHtml(student.studentName)}
            </td>

            <td>
                ${student.maximumMarks}
            </td>

            <td>
                ${student.marks[0]}
            </td>

            <td>
                ${student.marks[1]}
            </td>

            <td>
                ${student.marks[2]}
            </td>

            <td>
                ${student.marks[3]}
            </td>

            <td>
                ${student.marks[4]}
            </td>

            <td>
                ${student.marks[5]}
            </td>

            <td>
                ${student.totalMarks}
            </td>

            <td>
                ${student.averageMarks.toFixed(2)}
            </td>

            <td>
                ${student.percentage.toFixed(2)}%
            </td>

            <td>

                <button
                    type="button"
                    class="secondary-btn table-action-btn"
                    onclick="removeStudent(${student.studentId})"
                >
                    Remove
                </button>

            </td>

        `;


        studentTableBody.appendChild(row);

    });


    // Update count
    updateStudentCount();

}


// ======================================================
// REMOVE STUDENT
// ======================================================

function removeStudent(studentId) {


    students =
        students.filter(
            function (student) {

                return (
                    student.studentId !==
                    studentId
                );

            }
        );


    displayStudents();


    showNotification(
        "Student removed successfully."
    );

}


// ======================================================
// UPDATE STUDENT COUNT
// ======================================================

function updateStudentCount() {

    if (studentCount) {

        studentCount.textContent =
            students.length;

    }

}


// ======================================================
// SAVE BUTTON
// ======================================================

if (saveStudentsBtn) {

    saveStudentsBtn.addEventListener(
        "click",
        saveStudentsToDatabase
    );

}


// ======================================================
// SAVE TO DATABASE
// ======================================================

async function saveStudentsToDatabase() {


    // ------------------------------------------
    // CHECK STUDENTS
    // ------------------------------------------

    if (students.length === 0) {

        showNotification(
            "Please add at least one student.",
            "error"
        );

        return;

    }


    // ------------------------------------------
    // READ TEST DETAILS
    // ------------------------------------------

    const testName =
        testNameInput.value.trim();


    const maximumMarks =
        Number(maximumMarksInput.value);


    // ------------------------------------------
    // VALIDATE TEST
    // ------------------------------------------

    if (testName === "") {

        showNotification(
            "Please enter Test Name.",
            "error"
        );

        testNameInput.focus();

        return;

    }


    if (
        !Number.isFinite(maximumMarks) ||
        maximumMarks <= 0
    ) {

        showNotification(
            "Please select valid Maximum Marks.",
            "error"
        );

        maximumMarksInput.focus();

        return;

    }

	
	// ===============================
	// SEARCH STUDENT
	// ===============================

	if (searchStudentBtn) {
	    searchStudentBtn.addEventListener("click", searchStudent);
	}

	async function searchStudent() {

	    const studentId = Number(searchStudentIdInput.value);

	    if (!studentId || studentId <= 0) {
	        showNotification("Please enter a valid Student ID.", "error");
	        return;
	    }

	    try {

	        const response = await fetch(
	            `/students/search/${studentId}`
	        );

	        if (!response.ok) {
	            throw new Error("Failed to search student.");
	        }

	        const records = await response.json();

	        displaySearchResults(records);

	    } catch (error) {

	        console.error(error);

	        showNotification(
	            "Unable to search student.",
	            "error"
	        );
	    }
	}


	// ===============================
	// DISPLAY SEARCH RESULTS
	// ===============================

	async function displaySearchResults(records) {

	    searchResultsBody.innerHTML = "";

	    if (!records || records.length === 0) {

	        searchResults.classList.add("hidden");

	        showNotification(
	            "No marks found for this student.",
	            "error"
	        );

	        return;
	    }

	    /*
	     * We need the test details because the marks API
	     * gives us testId, but not testName or maximumMarks.
	     */

	    for (const record of records) {

	        let testName = "Unknown";
	        let maximumMarks = 0;

	        try {

	            const testResponse = await fetch(
	                `/students/test/${record.testId}`
	            );

	            if (testResponse.ok) {

	                const testData = await testResponse.json();

	                testName = testData.testName;
	                maximumMarks = testData.maximumMarks;
	            }

	        } catch (error) {

	            console.log(
	                "Could not load test details:",
	                error
	            );
	        }

	        const row = document.createElement("tr");

	        row.innerHTML = `
	            <td>${escapeHtml(testName)}</td>

	            <td>${maximumMarks}</td>

	            <td>${record.teluguMarks}</td>
	            <td>${record.hindiMarks}</td>
	            <td>${record.englishMarks}</td>
	            <td>${record.mathsMarks}</td>
	            <td>${record.scienceMarks}</td>
	            <td>${record.socialMarks}</td>

	            <td>${record.totalMarks}</td>

	            <td>${Number(record.averageMarks).toFixed(2)}</td>

	            <td>${Number(record.percentage).toFixed(2)}%</td>

	            <td>
	                <button
	                    type="button"
	                    class="edit-btn"
	                    onclick="editStudentMarks(
	                        ${record.testId},
	                        ${record.studentId},
	                        ${record.teluguMarks},
	                        ${record.hindiMarks},
	                        ${record.englishMarks},
	                        ${record.mathsMarks},
	                        ${record.scienceMarks},
	                        ${record.socialMarks},
	                        ${maximumMarks}
	                    )"
	                >
	                    Edit
	                </button>

	                <button
	                    type="button"
	                    class="delete-btn"
	                    onclick="deleteStudentMarks(
	                        ${record.testId},
	                        ${record.studentId}
	                    )"
	                >
	                    Delete
	                </button>
	            </td>
	        `;

	        searchResultsBody.appendChild(row);
	    }

	    searchResults.classList.remove("hidden");
	}


	// ===============================
	// EDIT STUDENT MARKS
	// ===============================

	window.editStudentMarks=async function (
	    testId,
	    studentId,
	    telugu,
	    hindi,
	    english,
	    maths,
	    science,
	    social,
	    maximumMarks
	) {

	    const newTelugu = prompt(
	        `Enter Telugu marks (0-${maximumMarks}):`,
	        telugu
	    );

	    if (newTelugu === null) return;

	    const newHindi = prompt(
	        `Enter Hindi marks (0-${maximumMarks}):`,
	        hindi
	    );

	    if (newHindi === null) return;

	    const newEnglish = prompt(
	        `Enter English marks (0-${maximumMarks}):`,
	        english
	    );

	    if (newEnglish === null) return;

	    const newMaths = prompt(
	        `Enter Maths marks (0-${maximumMarks}):`,
	        maths
	    );

	    if (newMaths === null) return;

	    const newScience = prompt(
	        `Enter Science marks (0-${maximumMarks}):`,
	        science
	    );

	    if (newScience === null) return;

	    const newSocial = prompt(
	        `Enter Social marks (0-${maximumMarks}):`,
	        social
	    );

	    if (newSocial === null) return;


	    const marks = [
	        Number(newTelugu),
	        Number(newHindi),
	        Number(newEnglish),
	        Number(newMaths),
	        Number(newScience),
	        Number(newSocial)
	    ];


	    // Validate marks

	    for (const mark of marks) {

	        if (
	            !Number.isFinite(mark) ||
	            mark < 0 ||
	            mark > maximumMarks
	        ) {

	            showNotification(
	                `Marks must be between 0 and ${maximumMarks}.`,
	                "error"
	            );

	            return;
	        }
	    }


	    const updatedMarks = {

	        testId: testId,
	        studentId: studentId,

	        teluguMarks: marks[0],
	        hindiMarks: marks[1],
	        englishMarks: marks[2],
	        mathsMarks: marks[3],
	        scienceMarks: marks[4],
	        socialMarks: marks[5]
	    };


	    try {

	        const response = await fetch(
	            `/students/marks/${testId}/${studentId}`,
	            {
	                method: "PUT",

	                headers: {
	                    "Content-Type": "application/json"
	                },

	                body: JSON.stringify(updatedMarks)
	            }
	        );


	        if (!response.ok) {

	            const errorData = await response.json();

	            throw new Error(
	                errorData.error ||
	                "Failed to update marks."
	            );
	        }


	        showNotification(
	            "Student marks updated successfully.",
	            "success"
	        );


	        // Refresh search results

	        searchStudent();

	    } catch (error) {

	        console.error(error);

	        showNotification(
	            error.message,
	            "error"
	        );
	    }
	}


	// ===============================
	// DELETE STUDENT MARKS
	// ===============================

	window.deleteStudentMarks=async function (
	    testId,
	    studentId
	) {

	    const confirmed = confirm(
	        "Are you sure you want to delete the marks for this test?"
	    );

	    if (!confirmed) {
	        return;
	    }


	    try {

	        const response = await fetch(
	            `/students/marks/${testId}/${studentId}`,
	            {
	                method: "DELETE"
	            }
	        );


	        if (!response.ok) {

	            const errorData = await response.text();

	            throw new Error(
	                errorData ||
	                "Failed to delete marks."
	            );
	        }


	        showNotification(
	            "Student marks deleted successfully.",
	            "success"
	        );


	        // Refresh search results

	        searchStudent();

	    } catch (error) {

	        console.error(error);

	        showNotification(
	            error.message,
	            "error"
	        );
	    }
	}


	// ===============================
	// CLEAR SEARCH
	// ===============================

	if (clearSearchBtn) {

	    clearSearchBtn.addEventListener(
	        "click",
	        function () {

	            searchStudentIdInput.value = "";

	            searchResultsBody.innerHTML = "";

	            searchResults.classList.add("hidden");
	        }
	    );
	}

    // ------------------------------------------
    // DISABLE SAVE BUTTON
    // ------------------------------------------

    saveStudentsBtn.disabled = true;


    const originalText =
        saveStudentsBtn.textContent;


    saveStudentsBtn.textContent =
        "Saving...";


    try {


        // ==========================================
        // STEP 1
        // CREATE TEST
        // ==========================================

        const testResponse =
            await fetch(
                "/students/test-db",
                {
                    method: "POST",

                    headers: {
                        "Content-Type":
                            "application/json"
                    },

                    body: JSON.stringify({

                        testName:
                            testName,

                        maximumMarks:
                            maximumMarks

                    })

                }
            );


        if (!testResponse.ok) {

            throw new Error(
                "Test creation failed: " +
                await getErrorMessage(
                    testResponse
                )
            );

        }


        const savedTest =
            await testResponse.json();


        const testId =
            savedTest.testId;


        if (!testId) {

            throw new Error(
                "Test was created but Test ID was not returned."
            );

        }


        // ==========================================
        // STEP 2
        // GET EXISTING STUDENTS
        // ==========================================

        const existingResponse =
            await fetch(
                "/students/db"
            );


        if (!existingResponse.ok) {

            throw new Error(
                "Could not read existing students: " +
                await getErrorMessage(
                    existingResponse
                )
            );

        }


        const existingStudents =
            await existingResponse.json();


        const existingIds =
            new Set(
                existingStudents.map(
                    function (student) {

                        return Number(
                            student.studentId
                        );

                    }
                )
            );


        // ==========================================
        // STEP 3
        // SAVE STUDENTS
        // ==========================================

        for (
            const student of students
        ) {


            // Student already exists

            if (
                existingIds.has(
                    student.studentId
                )
            ) {

                console.log(
                    "Student " +
                    student.studentId +
                    " already exists. Skipping."
                );

                continue;

            }


            // Create new student

            const studentResponse =
                await fetch(
                    "/students/db-test",
                    {
                        method: "POST",

                        headers: {
                            "Content-Type":
                                "application/json"
                        },

                        body: JSON.stringify({

                            studentId:
                                student.studentId,

                            studentName:
                                student.studentName

                        })

                    }
                );


            if (!studentResponse.ok) {

                throw new Error(
                    "Student " +
                    student.studentId +
                    " failed: " +
                    await getErrorMessage(
                        studentResponse
                    )
                );

            }


            existingIds.add(
                student.studentId
            );

        }


        // ==========================================
        // STEP 4
        // SAVE MARKS
        // ==========================================

        for (
            const student of students
        ) {


            const marksResponse =
                await fetch(
                    "/students/marks-db",
                    {
                        method: "POST",

                        headers: {
                            "Content-Type":
                                "application/json"
                        },

                        body: JSON.stringify({

                            testId:
                                testId,

                            studentId:
                                student.studentId,

                            teluguMarks:
                                student.marks[0],

                            hindiMarks:
                                student.marks[1],

                            englishMarks:
                                student.marks[2],

                            mathsMarks:
                                student.marks[3],

                            scienceMarks:
                                student.marks[4],

                            socialMarks:
                                student.marks[5]

                        })

                    }
                );


            if (!marksResponse.ok) {

                throw new Error(
                    "Marks for Student " +
                    student.studentId +
                    " failed: " +
                    await getErrorMessage(
                        marksResponse
                    )
                );

            }

        }


        // ==========================================
        // SUCCESS
        // ==========================================

        showNotification(
            "Test, students and marks saved successfully!"
        );


        console.log(
            "Everything saved successfully.",
            {
                testId:
                    testId,

                testName:
                    testName,

                studentCount:
                    students.length
            }
        );


    } catch (error) {


        console.error(
            "Database save error:",
            error
        );


        showNotification(
            error.message ||
            "Something went wrong while saving.",
            "error"
        );


    } finally {


        saveStudentsBtn.disabled =
            false;


        saveStudentsBtn.textContent =
            originalText;

    }

}


// ======================================================
// READ BACKEND ERROR
// ======================================================

async function getErrorMessage(response) {

    try {


        const data =
            await response.json();


        if (data.error) {

            return data.error;

        }


        if (data.message) {

            return data.message;

        }


        return JSON.stringify(data);


    } catch (error) {


        return await response.text();

    }

}


// ======================================================
// ESCAPE HTML
// ======================================================

function escapeHtml(value) {

    return String(value)

        .replace(
            /&/g,
            "&amp;"
        )

        .replace(
            /</g,
            "&lt;"
        )

        .replace(
            />/g,
            "&gt;"
        )

        .replace(
            /"/g,
            "&quot;"
        )

        .replace(
            /'/g,
            "&#039;"
        );

}

// ======================================================
// EXCEL REPORT BUTTONS
// ======================================================

const viewExcelBtn =
    document.getElementById("viewExcelBtn");

const downloadExcelBtn =
    document.getElementById("downloadExcelBtn");


// ======================================================
// DOWNLOAD EXCEL
// ======================================================

if (downloadExcelBtn) {

    downloadExcelBtn.addEventListener(
        "click",
        function () {

            window.location.href =
                "/excel/download";

        }
    );

}


// ======================================================
// VIEW EXCEL REPORT
// ======================================================

if (viewExcelBtn) {

    viewExcelBtn.addEventListener(
        "click",
        function () {

            window.open(
                "/excel/download",
                "_blank"
            );

        }
    );

}


// ======================================================
// INITIAL PAGE
// ======================================================

displayStudents();