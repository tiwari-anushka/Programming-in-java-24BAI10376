import java.util.*;
import java.io.*;

class Student {
    int id;
    String name;
    int marks;

    Student(int id, String name, int marks) {
        this.id = id;
        this.name = name;
        this.marks = marks;
    }
}

public class StudentManagement {

    static Scanner sc = new Scanner(System.in);
    static ArrayList<Student> students = new ArrayList<>();
    static final String FILE_NAME = "students.txt";

    public static void main(String[] args) {

        loadFromFile(); // 🔥 Load data at start

        while (true) {
            System.out.println("\n===== STUDENT MANAGEMENT SYSTEM =====");
            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Search Student");
            System.out.println("4. Delete Student");
            System.out.println("5. Exit");

            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1: addStudent(); break;
                case 2: viewStudents(); break;
                case 3: searchStudent(); break;
                case 4: deleteStudent(); break;
                case 5:
                    saveToFile(); // 🔥 Save before exit
                    System.out.println("Data saved. Exiting...");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    // ➕ Add Student
    static void addStudent() {
        System.out.print("Enter ID: ");
        int id = sc.nextInt();

        for (Student s : students) {
            if (s.id == id) {
                System.out.println("ID already exists!");
                return;
            }
        }

        sc.nextLine();

        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Marks: ");
        int marks = sc.nextInt();

        students.add(new Student(id, name, marks));
        saveToFile(); // auto-save

        System.out.println("Student added & saved!");
    }

    // 📋 View Students
    static void viewStudents() {
        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }

        for (Student s : students) {
            System.out.println("ID: " + s.id + " | Name: " + s.name + " | Marks: " + s.marks);
        }
    }

    // 🔍 Search
    static void searchStudent() {
        System.out.print("Enter ID: ");
        int id = sc.nextInt();

        for (Student s : students) {
            if (s.id == id) {
                System.out.println("Found: " + s.name + " | Marks: " + s.marks);
                return;
            }
        }

        System.out.println("Not found.");
    }

    // ❌ Delete
    static void deleteStudent() {
        System.out.print("Enter ID: ");
        int id = sc.nextInt();

        Iterator<Student> it = students.iterator();

        while (it.hasNext()) {
            Student s = it.next();
            if (s.id == id) {
                it.remove();
                saveToFile(); // auto-save
                System.out.println("Deleted & saved!");
                return;
            }
        }

        System.out.println("Not found.");
    }

    // 💾 SAVE TO FILE
    static void saveToFile() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME));

            for (Student s : students) {
                bw.write(s.id + "," + s.name + "," + s.marks);
                bw.newLine();
            }

            bw.close();

        } catch (IOException e) {
            System.out.println("Error saving file!");
        }
    }

    // 📂 LOAD FROM FILE
    static void loadFromFile() {
        try {
            File file = new File(FILE_NAME);

            if (!file.exists()) return;

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                int id = Integer.parseInt(data[0]);
                String name = data[1];
                int marks = Integer.parseInt(data[2]);

                students.add(new Student(id, name, marks));
            }

            br.close();

        } catch (Exception e) {
            System.out.println("Error loading file!");
        }
    }
}