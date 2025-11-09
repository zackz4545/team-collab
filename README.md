#  SmartAssistant — Group Project
**Course:** IT 106 DL1  
**Date:** November 9, 2025  
**Language:** Java  
**GUI Framework:** Swing  

---

##  Overview
The **SmartAssistant** project is a collaborative Java Swing application designed to assist campus users (students, faculty, and staff) with essential academic and utility functions.

Each group member contributed a distinct interactive module, all unified under the **SmartAssistant** umbrella. The system is entirely GUI-based, using **JOptionPane** dialogs for input/output consistency and simplicity.

---

##  Team Members and Modules
| Member | Module | Description |
|---------|---------|-------------|
| **Christopher Wells** |  Student Module | Manage student records and calculate GPAs |
| **Elizabeth Nsiah** |  Course & Faculty Module | Add, search, and display courses |
| **Amaan Zafar** |  Library Module | Manage books, issue/return records |
| **Zackaria Zagade** |  Utility Tools Module | Includes a calculator and event scheduler |

---

##  Project Summary
Each module runs independently, using Swing dialogs for all user interaction. Together, they form a comprehensive system for managing student, course, library, and campus activities.

---

##  Module Details

---

###  Student Module — *by Christopher Wells*
**File:** `GPACalculator.java`

#### Overview
A graphical interface for managing student information and computing GPA.  
Supports adding, searching, and displaying student data stored locally in a text file.

#### Features
- Add Student (ID, Name, Major) with duplicate ID prevention  
- Search Student by ID  
- Display All Students  
- GPA Calculator supporting letter and numeric grades  

#### Technical Highlights
- **File Storage:** `students.txt`  
- **Data Format:** `ID|Name|Major`  
- **Validation:** Empty fields, duplicates, and invalid inputs  
- **JDK Version:** 17+  

---

###  Course & Faculty Module — *by Elizabeth Nsiah*
**File:** `FacultySystem.java`

#### Overview
A GUI-based course management tool allowing the addition and lookup of academic courses and instructors.

#### Features
- Add Course (Code, Name, Instructor)  
- Search Course by Code or Instructor  
- Display All Courses  
- Prevents duplicate course codes  

#### Technical Highlights
- **Data Structure:** `ArrayList<Course>`  
- **GUI:** JOptionPane menus  
- **Validation:** Empty fields and duplicates  
- **JDK Version:** 17+  

---

###  Library Module — *by Amaan Zafar*
**File:** `LibraryModule.java`

#### Overview
A digital library system that allows users to add, search, issue, and return books through a clean Swing interface.

#### Features
- Add Book (Title, Author)  
- Search Books by Title or Author  
- Issue/Return Books by ID or Title  
- Display Available or All Books  

#### Technical Highlights
- **Data Structure:** `ArrayList<Book>`  
- **Auto-ID Assignment:** Each new book gets a unique ID  
- **Validation:** Empty fields, invalid IDs, and re-issue protection  
- **JDK Version:** 17+  

---

###  Utility Tools Module — *by Zackaria Zagade*
**File:** `Utility.java`

#### Overview
A multifunctional tool featuring a simple calculator and a campus event scheduler.

#### Features
- Basic Calculator supporting `+`, `-`, `*`, `/`  
- Event Scheduler: Add events (Date, Time, Title)  
- View All Events sorted by date  
- Auto-saves data to `events.csv`  

#### Technical Highlights
- **File Storage:** `events.csv`  
- **Data Format:** `date,time,title`  
- **Validation:** Invalid dates, empty fields, and divide-by-zero prevention  
- **JDK Version:** 17+  

---

##  Common Technical Aspects
- Built entirely in **Java Swing (JOptionPane)**  
- Modular design: each member’s class can run independently  
- Emphasis on **user-friendly dialog interfaces**  
- Strong **input validation** and **error handling**  
- Compatible with **JDK 17 or newer**  

---

##  How to Run
1. Open the project in **IntelliJ IDEA** or **any Java IDE**.  
2. Ensure all `.java` files are in the same package or directory.  
3. Run any module’s `main()` method individually:  
   - `GPACalculator.java` (Student Module)  
   - `FacultySystem.java` (Course & Faculty Module)  
   - `LibraryModule.java` (Library Module)  
   - `Utility.java` (Utility Tools Module)  

Each module opens its own **GUI window** using Swing dialogs.  

---

## 🏁 Project Summary
This collaborative project demonstrates teamwork, modular design, and GUI-based programming using Java Swing.  
