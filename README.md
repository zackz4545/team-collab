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

## How to Run
1. Ensure you have **Java** installed on your computer.  
2. Place `SmartAssistant.java` in a project directory (the `students.txt` file will be created automatically).  
3. Open a terminal or command prompt and compile:
   ```bash
   javac SmartAssistant.java
