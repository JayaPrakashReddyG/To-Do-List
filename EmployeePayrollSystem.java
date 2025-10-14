import java.io.*;
import java.util.*;
abstract class Employee implements Serializable {
    private static final long serialVersionUID = 1L;
    int id;
    String name;

    Employee(int id, String name) {
        this.id = id;
        this.name = name;
    }

    abstract double calculateSalary();

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name;
    }
}
class PermanentEmployee extends Employee {
    double basicSalary;
    double hra; 
    double bonus;

    PermanentEmployee(int id, String name, double basicSalary, double hra, double bonus) {
        super(id, name);
        this.basicSalary = basicSalary;
        this.hra = hra;
        this.bonus = bonus;
    }

    @Override
    double calculateSalary() {
        return basicSalary + hra + bonus;
    }

    @Override
    public String toString() {
        return super.toString() + ", Type: Permanent, Salary: " + calculateSalary();
    }
}
class ContractEmployee extends Employee {
    double hourlyRate;
    int hoursWorked;

    ContractEmployee(int id, String name, double hourlyRate, int hoursWorked) {
        super(id, name);
        this.hourlyRate = hourlyRate;
        this.hoursWorked = hoursWorked;
    }

    @Override
    double calculateSalary() {
        return hourlyRate * hoursWorked;
    }

    @Override
    public String toString() {
        return super.toString() + ", Type: Contract, Salary: " + calculateSalary();
    }
}


public class EmployeePayrollSystem {
    static ArrayList<Employee> employees = new ArrayList<>();
    static final String FILE_NAME = "employees.dat";

    public static void main(String[] args) {
        loadFromFile();
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n--- Employee Payroll System ---");
            System.out.println("1. Add Permanent Employee");
            System.out.println("2. Add Contract Employee");
            System.out.println("3. Display All Employees");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1 -> addPermanentEmployee(sc);
                case 2 -> addContractEmployee(sc);
                case 3 -> displayAll();
                case 4 -> saveToFile();
                default -> System.out.println("Invalid choice!");
            }
        } while (choice != 4);

        System.out.println("Exiting... Data saved.");
    }

    static void addPermanentEmployee(Scanner sc) {
        System.out.print("Enter ID: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Basic Salary: ");
        double basic = sc.nextDouble();
        System.out.print("Enter HRA: ");
        double hra = sc.nextDouble();
        System.out.print("Enter Bonus: ");
        double bonus = sc.nextDouble();

        employees.add(new PermanentEmployee(id, name, basic, hra, bonus));
        System.out.println("Permanent Employee added!");
    }

    static void addContractEmployee(Scanner sc) {
        System.out.print("Enter ID: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Hourly Rate: ");
        double rate = sc.nextDouble();
        System.out.print("Enter Hours Worked: ");
        int hours = sc.nextInt();

        employees.add(new ContractEmployee(id, name, rate, hours));
        System.out.println("Contract Employee added!");
    }

    static void displayAll() {
        if (employees.isEmpty()) {
            System.out.println("No employees found.");
        } else {
            for (Employee e : employees) {
                System.out.println(e);
            }
        }
    }

    static void saveToFile() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(employees);
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    static void loadFromFile() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            employees = (ArrayList<Employee>) in.readObject();
        } catch (FileNotFoundException e) {
       
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading file: " + e.getMessage());
        }
    }
}
