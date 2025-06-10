import java.util.*;
import java.util.regex.Pattern;

/**
 * Student Management System
 * A comprehensive system for managing student records with full CRUD operations
 * Features: Add, View, Update, Delete students with data validation
 * 
 * @author Your Name
 * @version 1.0
 */

// Student class representing individual student entity
class Student {
    private int id;
    private String name;
    private String email;
    private int age;
    private String course;
    private double gpa;

    // Constructor with validation
    public Student(int id, String name, String email, int age, String course, double gpa) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.course = course;
        this.gpa = gpa;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    
    public String getCourse() { return course; }
    public void setCourse(String course) { this.course = course; }
    
    public double getGpa() { return gpa; }
    public void setGpa(double gpa) { this.gpa = gpa; }

    @Override
    public String toString() {
        return String.format("ID: %d | Name: %s | Email: %s | Age: %d | Course: %s | GPA: %.2f",
                id, name, email, age, course, gpa);
    }
}

// Data validation utility class
class DataValidator {
    private static final String EMAIL_PATTERN = 
        "^[a-zA-Z0-9_+&-]+(?:\\.[a-zA-Z0-9_+&-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    
    public static boolean isValidEmail(String email) {
        return Pattern.compile(EMAIL_PATTERN).matcher(email).matches();
    }
    
    public static boolean isValidAge(int age) {
        return age >= 16 && age <= 100;
    }
    
    public static boolean isValidGPA(double gpa) {
        return gpa >= 0.0 && gpa <= 4.0;
    }
    
    public static boolean isValidName(String name) {
        return name != null && name.trim().length() >= 2 && name.matches("[a-zA-Z\\s]+");
    }
}

// Main Student Management System class
public class StudentManagementSystem {
    private static List<Student> students = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static int nextId = 1;

    public static void main(String[] args) {
        System.out.println("=== STUDENT MANAGEMENT SYSTEM ===");
        System.out.println("Welcome to the comprehensive student management portal!");
        
        // Initialize with sample data
        initializeSampleData();
        
        while (true) {
            try {
                displayMenu();
                int choice = getValidChoice();
                processChoice(choice);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                System.out.println("Please try again.");
            }
        }
    }

    // Initialize sample data for testing
    private static void initializeSampleData() {
        students.add(new Student(nextId++, "John Doe", "john@email.com", 20, "Computer Science", 3.5));
        students.add(new Student(nextId++, "Jane Smith", "jane@email.com", 19, "Mathematics", 3.8));
    }

    // Display main menu
    private static void displayMenu() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("STUDENT MANAGEMENT SYSTEM - MAIN MENU");
        System.out.println("=".repeat(50));
        System.out.println("1. Add New Student");
        System.out.println("2. View All Students");
        System.out.println("3. Search Student by ID");
        System.out.println("4. Update Student Information");
        System.out.println("5. Delete Student");
        System.out.println("6. Display Statistics");
        System.out.println("7. Exit System");
        System.out.println("=".repeat(50));
        System.out.print("Enter your choice (1-7): ");
    }

    // Get valid menu choice with error handling
    private static int getValidChoice() {
        try {
            int choice = Integer.parseInt(scanner.nextLine());
            if (choice < 1 || choice > 7) {
                throw new IllegalArgumentException("Choice must be between 1 and 7");
            }
            return choice;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid input. Please enter a number.");
        }
    }

    // Process user choice
    private static void processChoice(int choice) {
        switch (choice) {
            case 1: addStudent(); break;
            case 2: viewAllStudents(); break;
            case 3: searchStudent(); break;
            case 4: updateStudent(); break;
            case 5: deleteStudent(); break;
            case 6: displayStatistics(); break;
            case 7: exitSystem(); break;
        }
    }

    // Add new student with complete validation
    private static void addStudent() {
        System.out.println("\n--- ADD NEW STUDENT ---");
        try {
            System.out.print("Enter student name: ");
            String name = scanner.nextLine().trim();
            if (!DataValidator.isValidName(name)) {
                throw new IllegalArgumentException("Invalid name. Use only letters and spaces, min 2 characters.");
            }

            System.out.print("Enter email: ");
            String email = scanner.nextLine().trim();
            if (!DataValidator.isValidEmail(email)) {
                throw new IllegalArgumentException("Invalid email format.");
            }

            System.out.print("Enter age: ");
            int age = Integer.parseInt(scanner.nextLine());
            if (!DataValidator.isValidAge(age)) {
                throw new IllegalArgumentException("Age must be between 16 and 100.");
            }

            System.out.print("Enter course: ");
            String course = scanner.nextLine().trim();
            if (course.isEmpty()) {
                throw new IllegalArgumentException("Course cannot be empty.");
            }

            System.out.print("Enter GPA (0.0-4.0): ");
            double gpa = Double.parseDouble(scanner.nextLine());
            if (!DataValidator.isValidGPA(gpa)) {
                throw new IllegalArgumentException("GPA must be between 0.0 and 4.0.");
            }

            Student newStudent = new Student(nextId++, name, email, age, course, gpa);
            students.add(newStudent);
            System.out.println("✓ Student added successfully! ID: " + newStudent.getId());
            
        } catch (NumberFormatException e) {
            System.out.println("✗ Error: Please enter valid numbers for age and GPA.");
        } catch (Exception e) {
            System.out.println("✗ Error: " + e.getMessage());
        }
    }

    // View all students
    private static void viewAllStudents() {
        System.out.println("\n--- ALL STUDENTS ---");
        if (students.isEmpty()) {
            System.out.println("No students found in the system.");
            return;
        }
        
        System.out.println("Total Students: " + students.size());
        System.out.println("-".repeat(80));
        for (Student student : students) {
            System.out.println(student);
        }
        System.out.println("-".repeat(80));
    }

    // Search student by ID
    private static void searchStudent() {
        System.out.println("\n--- SEARCH STUDENT ---");
        try {
            System.out.print("Enter student ID: ");
            int id = Integer.parseInt(scanner.nextLine());
            
            Student student = findStudentById(id);
            if (student != null) {
                System.out.println("Student Found:");
                System.out.println("-".repeat(50));
                System.out.println(student);
                System.out.println("-".repeat(50));
            } else {
                System.out.println("✗ Student with ID " + id + " not found.");
            }
        } catch (NumberFormatException e) {
            System.out.println("✗ Error: Please enter a valid ID number.");
        }
    }

    // Update student information
    private static void updateStudent() {
        System.out.println("\n--- UPDATE STUDENT ---");
        try {
            System.out.print("Enter student ID to update: ");
            int id = Integer.parseInt(scanner.nextLine());
            
            Student student = findStudentById(id);
            if (student == null) {
                System.out.println("✗ Student with ID " + id + " not found.");
                return;
            }

            System.out.println("Current Information: " + student);
            System.out.println("Enter new information (press Enter to keep current value):");

            System.out.print("New name [" + student.getName() + "]: ");
            String name = scanner.nextLine().trim();
            if (!name.isEmpty() && DataValidator.isValidName(name)) {
                student.setName(name);
            }

            System.out.print("New email [" + student.getEmail() + "]: ");
            String email = scanner.nextLine().trim();
            if (!email.isEmpty() && DataValidator.isValidEmail(email)) {
                student.setEmail(email);
            }

            System.out.print("New course [" + student.getCourse() + "]: ");
            String course = scanner.nextLine().trim();
            if (!course.isEmpty()) {
                student.setCourse(course);
            }

            System.out.println("✓ Student updated successfully!");
            System.out.println("Updated Information: " + student);
            
        } catch (NumberFormatException e) {
            System.out.println("✗ Error: Please enter a valid ID number.");
        }
    }

    // Delete student
    private static void deleteStudent() {
        System.out.println("\n--- DELETE STUDENT ---");
        try {
            System.out.print("Enter student ID to delete: ");
            int id = Integer.parseInt(scanner.nextLine());
            
            Student student = findStudentById(id);
            if (student == null) {
                System.out.println("✗ Student with ID " + id + " not found.");
                return;
            }

            System.out.println("Student to delete: " + student);
            System.out.print("Are you sure? (yes/no): ");
            String confirmation = scanner.nextLine().trim().toLowerCase();
            
            if (confirmation.equals("yes") || confirmation.equals("y")) {
                students.remove(student);
                System.out.println("✓ Student deleted successfully!");
            } else {
                System.out.println("Delete operation cancelled.");
            }
            
        } catch (NumberFormatException e) {
            System.out.println("✗ Error: Please enter a valid ID number.");
        }
    }

    // Display system statistics
    private static void displayStatistics() {
        System.out.println("\n--- SYSTEM STATISTICS ---");
        if (students.isEmpty()) {
            System.out.println("No data available.");
            return;
        }

        int totalStudents = students.size();
        double totalGPA = students.stream().mapToDouble(Student::getGpa).sum();
        double averageGPA = totalGPA / totalStudents;
        
        Map<String, Integer> courseCount = new HashMap<>();
        for (Student student : students) {
            courseCount.put(student.getCourse(), 
                courseCount.getOrDefault(student.getCourse(), 0) + 1);
        }

        System.out.println("Total Students: " + totalStudents);
        System.out.printf("Average GPA: %.2f%n", averageGPA);
        System.out.println("Course Distribution:");
        courseCount.forEach((course, count) -> 
            System.out.println("  " + course + ": " + count + " students"));
    }

    // Helper method to find student by ID
    private static Student findStudentById(int id) {
        return students.stream()
                .filter(student -> student.getId() == id)
                .findFirst()
                .orElse(null);
    }

    // Exit system gracefully
    private static void exitSystem() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("Thank you for using Student Management System!");
        System.out.println("System shutting down safely...");
        System.out.println("=".repeat(50));
        scanner.close();
        System.exit(0);
    }
}