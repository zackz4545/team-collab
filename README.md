# 🎓 SmartAssistant — Student Module
**Author:** Christopher Wells  
**Course:** (IT 106 DL1)  
**Date:** (11/8/2025)  
**Language:** Java  
**GUI Framework:** Swing  

---

## Overview
This section of the **SmartAssistant** project represents my personal contribution:  
the **Student Module**. It provides an easy-to-use graphical interface for managing
student information and performing GPA calculations.

I designed and implemented this module independently as part of the group project deliverables.
The application was written in **Java Swing** and focuses on user interaction,
data persistence, and basic academic record functionality.

---

## Features Implemented
### 1. Add Student
- Allows users to enter a **Student ID**, **Full Name**, and **Major**.  
- Saves the record into a local text file (`students.txt`).  
- Automatically checks for **duplicate IDs** before saving.  

### 2. Search Student
- Lets users search for a specific student by **ID**.  
- Displays their stored details or shows a **“Student not found”** message.  

### 3. Display Students
- Loads all students into a **JTable** for easy viewing.  
- Includes a **Refresh** button to reload the table data.  

### 4. GPA Calculator
- Accepts multiple course entries with **Grade** and **Credit Hours**.  
- Supports both **letter grades (A, B+, C, etc.)** and **numeric GPA values (0.0–4.0)**.  
- Calculates overall GPA and displays the result instantly.  

---

## Technical Details
- **Main Class:** `SmartAssistant.java`  
- **File Storage:** `students.txt` (plain text file)  
- **Data Format:** `ID|Name|Major`  
- **Error Handling:** Validations for empty fields, duplicates, and invalid input  
- **Recommended JDK:** Version 17 or newer  

---

# 📘 Smart Campus Assistant – Course & Faculty Module
**Author:** Elizabeth Nsiah  
**Role:** Course/Faculty Module Creator  

---

## 🧩 Overview
This module is part of the **Smart Campus Assistant** group project.  
It manages **courses and faculty information** through a simple, user-friendly **Java Swing GUI**.

Users can:
- **Add new courses**
- **Search courses** by code or instructor
- **Display all available courses**

All actions are handled through `JOptionPane` dialogs for a consistent GUI experience shared by all Smart Campus Assistant modules.

---

## 🎯 Key Responsibilities
- Developed the **Course/Faculty management system** for the Smart Campus Assistant project.  
- Designed **JOptionPane-based GUI menus** consistent with other modules (GPA Calculator, Library Module, Utility Tools).  
- Implemented **add, search, and display** functionality for course data.  
- Added **validation checks** to prevent duplicate course codes and empty fields.  
- Built **in-memory storage** using `ArrayList<Course>` for managing course information.  
- Ensured **clarity, simplicity, and user-friendly interaction** in all dialogs.

---

## 🧠 Features Implemented

### 1️⃣ Add New Course
- Prompts user for **Course Code**, **Course Name**, and **Instructor Name**.  
- Ensures all fields are filled.  
- Prevents duplicate course codes.  
- Displays a success confirmation on completion.

### 2️⃣ Search Course (By Code or Instructor)
- Allows searching by **course code** or **instructor name** (case-insensitive).  
- Displays all matching results in a clear formatted dialog.  
- Notifies user if no matches are found.

### 3️⃣ Display All Courses
- Lists all courses currently stored in memory.  
- Displays information in a structured format:  
  `Code | Name | Instructor`.  
- Alerts user if no courses exist.

### 4️⃣ Exit Option
- Closes the module gracefully with a message confirming exit.

---

## 🖥️ Technologies Used
- **Language:** Java  
- **Framework:** Swing (`JOptionPane`)  
- **Data Structure:** `ArrayList<Course>`  
- **IDE:** IntelliJ IDEA  

---

## 🧩 Class Breakdown

### 🏫 Class: `FacultySystem`
- Serves as the **main controller** for the module.  
- Handles all menu logic, user interaction, and GUI prompts.

### 📘 Inner Class: `Course`
Represents an individual course with attributes:
- `code` → Course code  
- `name` → Course title  
- `instructor` → Instructor name  

Provides getter methods for each property.

---

## 🚀 How to Run
1. Open **IntelliJ IDEA** (or any Java IDE).  
2. Create a file named `FacultySystem.java`.  
3. Copy and paste the full source code.  
4. Run the program.  
5. Follow on-screen menu dialogs to add, search, or view courses.

---

## 🧾 Example Interaction

**Main Menu**

## How to Run
1. Ensure you have **Java** installed on your computer.  
2. Place `SmartAssistant.java` in a project directory (the `students.txt` file will be created automatically).  
3. Open a terminal or command prompt and compile:
   ```bash
   javac SmartAssistant.java
