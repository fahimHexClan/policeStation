# Police Station Management System

## Project Overview

The **Police Station Management System** is a comprehensive Java-based application designed to manage various operations within a police station. Built using **JavaFX** for the graphical user interface (GUI) and **MySQL** for database management, the system facilitates managing citizen records, complaints, crimes, employees, and more.

This system supports the following features:
- **Admin & Employee Login/Signup**
- **Dashboard** to track police station statistics (crime, complaints, fines, etc.)
- **Complaint Management** including suspect details and email notifications
- **Traffic Management** for vehicles and fines
- **Crime Management** for adding and managing crimes
- **Employee Management** with image upload functionality
- **Police Report Generation** for citizens based on criminal activities

---

## Features

### 1. **Login and Signup Pages**
- **Login Page**: Admin and employees can log in to access the system.
- **Signup Page**: Admin and employees can create their accounts to use the system.

### 2. **Dashboard**
- Displays real-time statistics:
    - **Crimes**, **Complaints**, **Police Reports**, **Fines**, **Employees**, **Citizens**
- Visual representation of data through **Bar Charts** for easy understanding.

### 3. **Citizen Management**
- Manage **Citizen Details** (CRUD operations).
- Ability to upload and display **Citizen Images**.

### 4. **Complaint Management**
- Manage **Complaint Details**, **Incident**, and **Suspect** information.
- Automatically sends an email to the suspect with the message:  
  `"Please come to the police station within a week."`
- All data is saved to the database and supports **CRUD** operations.

### 5. **Traffic Management**
- Manage **Driver**, **Vehicle**, and **Fine Details**.
- Add and track **Fine Transactions**.

### 6. **Crime Management**
- Add and manage **Crime Details**.

### 7. **Employee Management**
- Manage **Employee Details** with **CRUD functionality**.
- Image upload for employee profiles.

### 8. **Police Report Generation**
- **Generate Police Reports** for citizens instantly.
- System identifies if the citizen has committed a crime and issues a report within **one minute**.

---

## Technologies Used

- **JavaFX** for the GUI.
- **MySQL** for the database.
- **JFoenix** for Material Design components.
- **Lombok** to reduce boilerplate code.
- **JasperReports** for generating reports.
- **JavaMail API** for email notifications to suspects.

---

## Setup Instructions

### Prerequisites

1. **JDK 11 or higher** installed on your machine.
2. **Maven** installed for dependency management.
3. **MySQL** database up and running.

### Steps to Run

1. **Clone the repository**:
   ```bash
   git clone <https://github.com/fahimHexClan/policeStation.git>




### Acknowledgments
### Special thanks to
1.**JavaFX, Maven, MySQL, JFoenix, Lombok, JasperReports,
and JavaMail API for the technologies and libraries that made this project possible.**