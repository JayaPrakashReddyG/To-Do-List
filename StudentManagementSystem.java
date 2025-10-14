import java.io.*;
import java.util.*;

class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    int id;
    String name;
    int marks;
    String grade;

    Student(int id, String name, int marks) {
        this.id = id;
        this.name = name;
        this.marks = marks;
        this.grade = calculateGrade(marks);
    }

    private String calculateGrade(int marks) {
        if (marks >= 90) return "A+";
        else if (marks >= 80) return "A";
        else if (marks >= 70) return "B";
        else if (marks >= 50) return "C";
        else return "F";
    }

    public void updateMarks(int marks) {
        this.marks = marks;
        this.grade = calculateGrade(marks);
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Marks: " + marks + ", Grade: " + grade;
    }
}

public class StudentManagementSystem {
    static ArrayList<Student> students = new ArrayList<>();
    static final String FILE_NAME = "students.dat";

    public static void main(String[] args) {
        loadFromFile();
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n--- Student Management System ---");
            System.out.println("1. Add Student");
            System.out.println("2. Update Student");
            System.out.println("3. Delete Student");
            System.out.println("4. Search Student");
            System.out.println("5. Display All Students");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1: addStudent(sc); break;
                case 2: updateStudent(sc); break;
                case 3: deleteStudent(sc); break;
                case 4: searchStudent(sc); break;
                case 5: displayAll(); break;
                case 6: saveToFile(); break;
                default: System.out.println("Invalid choice!"); break;
            }
        } while (choice != 6);

        System.out.println("Exiting... Data saved.");
    }

    static void addStudent(Scanner sc) {
        System.out.print("Enter ID: ");
        int id = sc.nextInt();
        sc.nextLine(); 
        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Marks: ");
        int marks = sc.nextInt();

        students.add(new Student(id, name, marks));
        System.out.println("Student added successfully!");
    }

    static void updateStudent(Scanner sc) {
        System.out.print("Enter ID to update: ");
        int id = sc.nextInt();
        for (Student s : students) {
            if (s.id == id) {
                sc.nextLine(); 
                System.out.print("Enter new Name: ");
                s.name = sc.nextLine();
                System.out.print("Enter new Marks: ");
                int newMarks = sc.nextInt();
                s.updateMarks(newMarks);
                System.out.println("Student updated successfully!");
                return;
            }
        }
        System.out.println("Student not found!");
    }

    static void deleteStudent(Scanner sc) {
        System.out.print("Enter ID to delete: ");
        int id = sc.nextInt();
        students.removeIf(s -> s.id == id); 
        System.out.println("Student deleted if ID existed.");
    }

    static void searchStudent(Scanner sc) {
        System.out.print("Enter ID to search: ");
        int id = sc.nextInt();
        for (Student s : students) {
            if (s.id == id) {
                System.out.println(s);
                return;
            }
        }
        System.out.println("Student not found!");
    }

    static void displayAll() {
        if (students.isEmpty()) {
            System.out.println("No records found.");
        } else {
            for (Student s : students) {
                System.out.println(s);
            }
        }
    }

    static void saveToFile() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(students);
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    static void loadFromFile() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            students = (ArrayList<Student>) in.readObject();
        } catch (FileNotFoundException e) {
           
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading file: " + e.getMessage());
        }
    }
}
