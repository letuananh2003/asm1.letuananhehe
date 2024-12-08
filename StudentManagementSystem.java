import java.util.*;

class Student {
    private String id;
    private String name;
    private double marks;

    public Student(String id, String name, double marks) {
        this.id = id;
        this.name = name;
        this.marks = marks;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getMarks() {
        return marks;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMarks(double marks) {
        this.marks = marks;
    }

    public String getRank() {
        if (marks < 5.0) return "Fail";
        else if (marks < 6.5) return "Medium";
        else if (marks < 7.5) return "Good";
        else if (marks < 9.0) return "Very Good";
        else return "Excellent";
    }

    @Override
    public String toString() {
        return String.format("ID: %s, Name: %s, Marks: %.2f, Rank: %s",
                id, name, marks, getRank());
    }
}

public class StudentManagementSystem {
    private static List<Student> students = new ArrayList<>();
    private static Stack<Student> deletedStudents = new Stack<>();
    private static Stack<Student> studentStack = new Stack<>(); // Ngăn xếp lưu trữ sinh viên

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n--- Student Management System ---");
            System.out.println("1. Add Student");
            System.out.println("2. Edit Student");
            System.out.println("3. Delete Student");
            System.out.println("4. Sort Students by Marks");
            System.out.println("5. Search Student by ID");
            System.out.println("6. Display All Students");
            System.out.println("7. Undo Last Delete");
            System.out.println("8. Push Student to Stack");
            System.out.println("9. Pop Student from Stack");
            System.out.println("10. Peek Top Student in Stack");
            System.out.println("11. Check if Stack is Empty");
            System.out.println("12. Display All Students in Stack");
            System.out.println("13. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addStudent(scanner);
                    break;
                case 2:
                    editStudent(scanner);
                    break;
                case 3:
                    deleteStudent(scanner);
                    break;
                case 4:
                    sortStudents(scanner);
                    break;
                case 5:
                    searchStudent(scanner);
                    break;
                case 6:
                    displayStudents();
                    break;
                case 7:
                    undoLastDelete();
                    break;
                case 8:
                    pushStudentToStack(scanner);
                    break;
                case 9:
                    popStudentFromStack();
                    break;
                case 10:
                    peekStudentInStack();
                    break;
                case 11:
                    isStackEmpty();
                    break;
                case 12:
                    displayStack();
                    break;
                case 13:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice! Try again.");
            }
        } while (choice != 13);

        scanner.close();
    }

    private static void addStudent(Scanner scanner) {
        System.out.print("Enter Student ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter Student Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Marks: ");
        double marks = scanner.nextDouble();

        students.add(new Student(id, name, marks));
        System.out.println("Student added successfully!");
    }

    private static void editStudent(Scanner scanner) {
        System.out.print("Enter Student ID to edit: ");
        String id = scanner.nextLine();

        for (Student student : students) {
            if (student.getId().equals(id)) {
                System.out.print("Enter new name: ");
                String name = scanner.nextLine();
                System.out.print("Enter new marks: ");
                double marks = scanner.nextDouble();

                student.setName(name);
                student.setMarks(marks);
                System.out.println("Student updated successfully!");
                return;
            }
        }

        System.out.println("Student not found!");
    }

    private static void deleteStudent(Scanner scanner) {
        System.out.print("Enter Student ID to delete: ");
        String id = scanner.nextLine();

        Iterator<Student> iterator = students.iterator();
        while (iterator.hasNext()) {
            Student student = iterator.next();
            if (student.getId().equals(id)) {
                deletedStudents.push(student); // Save to stack
                iterator.remove();
                System.out.println("Student deleted successfully and saved to stack!");
                return;
            }
        }

        System.out.println("Student not found!");
    }

    private static void undoLastDelete() {
        if (!deletedStudents.isEmpty()) {
            Student restoredStudent = deletedStudents.pop();
            students.add(restoredStudent);
            System.out.println("Last deleted student restored:");
            System.out.println(restoredStudent);
        } else {
            System.out.println("No deleted students to restore!");
        }
    }

    private static void pushStudentToStack(Scanner scanner) {
        System.out.print("Enter Student ID to push to stack: ");
        String id = scanner.nextLine();

        for (Student student : students) {
            if (student.getId().equals(id)) {
                studentStack.push(student);
                System.out.println("Student pushed to stack successfully!");
                return;
            }
        }
        System.out.println("Student not found!");
    }

    private static void popStudentFromStack() {
        if (!studentStack.isEmpty()) {
            Student poppedStudent = studentStack.pop();
            System.out.println("Student popped from stack:");
            System.out.println(poppedStudent);
        } else {
            System.out.println("Stack is empty! No student to pop.");
        }
    }

    private static void peekStudentInStack() {
        if (!studentStack.isEmpty()) {
            Student topStudent = studentStack.peek();
            System.out.println("Student at the top of the stack:");
            System.out.println(topStudent);
        } else {
            System.out.println("Stack is empty!");
        }
    }

    private static void isStackEmpty() {
        if (studentStack.isEmpty()) {
            System.out.println("Stack is empty.");
        } else {
            System.out.println("Stack is not empty.");
        }
    }

    private static void displayStack() {
        if (studentStack.isEmpty()) {
            System.out.println("Stack is empty! No students to display.");
        } else {
            System.out.println("\n--- Students in Stack ---");
            for (Student student : studentStack) {
                System.out.println(student);
            }
        }
    }

    private static void sortStudents(Scanner scanner) {
        System.out.println("Choose sorting order: 1. Ascending, 2. Descending");
        int orderChoice = scanner.nextInt();

        if (orderChoice == 1) {
            students.sort(Comparator.comparingDouble(Student::getMarks));
            System.out.println("Sorted in Ascending order!");
        } else if (orderChoice == 2) {
            students.sort((s1, s2) -> Double.compare(s2.getMarks(), s1.getMarks()));
            System.out.println("Sorted in Descending order!");
        } else {
            System.out.println("Invalid choice!");
        }
    }

    private static void searchStudent(Scanner scanner) {
        System.out.print("Enter Student ID to search: ");
        String id = scanner.nextLine();

        for (Student student : students) {
            if (student.getId().equals(id)) {
                System.out.println(student);
                return;
            }
        }

        System.out.println("Student not found!");
    }

    private static void displayStudents() {
        if (students.isEmpty()) {
            System.out.println("No students to display!");
            return;
        }

        System.out.println("\n--- Student List ---");
        for (Student student : students) {
            System.out.println(student);
        }
    }
}
