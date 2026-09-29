package cit300.app;

import java.util.Scanner;

import cit300.graph.Graph;
import cit300.hash.HashTable;
import cit300.queue.ServiceQueue;
import cit300.queue.ServiceRequest;
import cit300.stack.ActionStack;
import cit300.student.Student;
import cit300.student.StudentLinkedList;
import cit300.tree.BST;

/**
 * Menu-driven console application. Wires together the team's data structures.
 * Student records live in the linked list. BST and hash table are search indexes
 * built from the same Student objects.
 */
public class Main {

    private static final int MAX_STUDENTS = 100;

    private final Scanner scanner;
    private final StudentLinkedList studentList;
    private final ActionStack actionStack;
    private final ServiceQueue serviceQueue;
    private BST studentTree;
    private HashTable studentHash;
    private final Graph campusGraph;

    // Same Student objects as in the linked list. Used only to rebuild BST/hash after delete.
    private final Student[] liveStudents;
    private int liveCount;

    public Main() {
        scanner = new Scanner(System.in);
        studentList = new StudentLinkedList();
        actionStack = new ActionStack();
        serviceQueue = new ServiceQueue();
        studentTree = new BST();
        studentHash = new HashTable();
        campusGraph = new Graph();
        liveStudents = new Student[MAX_STUDENTS];
        liveCount = 0;
    }

    public static void main(String[] args) {
        Main app = new Main();
        app.run();
    }

    private void run() {
        boolean running = true;
        while (running) {
            printMainMenu();
            int choice = readMenuChoice();
            switch (choice) {
                case 1:
                    studentMenu();
                    break;
                case 2:
                    stackMenu();
                    break;
                case 3:
                    queueMenu();
                    break;
                case 4:
                    bstMenu();
                    break;
                case 5:
                    hashMenu();
                    break;
                case 6:
                    graphMenu();
                    break;
                case 0:
                    running = false;
                    System.out.println("Goodbye.");
                    break;
                default:
                    System.out.println("Invalid menu choice. Enter a number from the menu.");
                    break;
            }
        }
    }

    private void printMainMenu() {
        System.out.println();
        System.out.println("==================================================");
        System.out.println(" University Student Record and Campus Routes");
        System.out.println("==================================================");
        System.out.println("1. Student records (Linked List)");
        System.out.println("2. Recent actions (Stack)");
        System.out.println("3. Service requests (Queue)");
        System.out.println("4. Student search tree (BST)");
        System.out.println("5. Student ID hash table");
        System.out.println("6. Campus map (Graph)");
        System.out.println("0. Exit");
        System.out.print("Enter choice: ");
    }

    private void studentMenu() {
        boolean back = false;
        while (!back) {
            System.out.println();
            System.out.println("--- Student Records ---");
            System.out.println("1. Add student");
            System.out.println("2. Update student");
            System.out.println("3. Delete student");
            System.out.println("4. Search student");
            System.out.println("5. Display all students");
            System.out.println("0. Back to main menu");
            System.out.print("Enter choice: ");
            int choice = readMenuChoice();
            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    updateStudent();
                    break;
                case 3:
                    deleteStudent();
                    break;
                case 4:
                    searchStudent();
                    break;
                case 5:
                    System.out.println();
                    System.out.println("Students in the linked list:");
                    studentList.displayStudents();
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid menu choice. Enter a number from the menu.");
                    break;
            }
        }
    }

    private void addStudent() {
        if (liveCount >= MAX_STUDENTS) {
            System.out.println("Cannot add more students. The list is full.");
            return;
        }

        String studentId = readRequiredText("Student ID");
        if (studentId == null) {
            return;
        }
        if (studentList.searchStudent(studentId) != null) {
            System.out.println("Student ID already exists. Duplicate IDs are not allowed.");
            return;
        }

        String name = readRequiredText("Name");
        if (name == null) {
            return;
        }
        String programme = readRequiredText("Programme");
        if (programme == null) {
            return;
        }

        Integer marks = readMarks();
        if (marks == null) {
            return;
        }

        Student student = new Student(studentId, name, programme, marks.intValue());
        studentList.addStudent(student);

        if (studentList.searchStudent(studentId) == null) {
            System.out.println("Student was not added.");
            return;
        }

        studentTree.insert(student);
        studentHash.insert(student);
        liveStudents[liveCount] = student;
        liveCount++;
        actionStack.push("Added " + studentId);
        System.out.println("Student added: " + student);
    }

    private void updateStudent() {
        String studentId = readRequiredText("Student ID to update");
        if (studentId == null) {
            return;
        }

        Student existing = studentList.searchStudent(studentId);
        if (existing == null) {
            System.out.println("No student found with ID " + studentId + ".");
            return;
        }

        String name = readRequiredText("New name");
        if (name == null) {
            return;
        }
        String programme = readRequiredText("New programme");
        if (programme == null) {
            return;
        }
        Integer marks = readMarks();
        if (marks == null) {
            return;
        }

        boolean updated = studentList.updateStudent(studentId, name, programme, marks.intValue());
        if (!updated) {
            System.out.println("Update failed.");
            return;
        }

        // Same Student object is stored in the BST and hash table, so their data updates too.
        actionStack.push("Updated " + studentId);
        System.out.println("Student updated: " + existing);
    }

    private void deleteStudent() {
        String studentId = readRequiredText("Student ID to delete");
        if (studentId == null) {
            return;
        }

        if (studentList.searchStudent(studentId) == null) {
            System.out.println("No student found with ID " + studentId + ".");
            return;
        }

        boolean deleted = studentList.deleteStudent(studentId);
        if (!deleted) {
            System.out.println("Delete failed.");
            return;
        }

        removeLiveStudent(studentId);
        rebuildSearchIndexes();
        actionStack.push("Deleted " + studentId);
        System.out.println("Student deleted: " + studentId);
    }

    private void searchStudent() {
        String studentId = readRequiredText("Student ID to search");
        if (studentId == null) {
            return;
        }

        Student fromList = studentList.searchStudent(studentId);
        Student fromTree = studentTree.search(studentId);
        Student fromHash = studentHash.search(studentId);

        System.out.println();
        System.out.println("Linked list search: " + formatStudentResult(fromList));
        System.out.println("BST search:         " + formatStudentResult(fromTree));
        System.out.println("Hash table search:  " + formatStudentResult(fromHash));
    }

    private String formatStudentResult(Student student) {
        if (student == null) {
            return "not found";
        }
        return student.toString();
    }

    private void removeLiveStudent(String studentId) {
        int foundIndex = -1;
        for (int i = 0; i < liveCount; i++) {
            if (liveStudents[i].getStudentId().equals(studentId)) {
                foundIndex = i;
                break;
            }
        }
        if (foundIndex == -1) {
            return;
        }
        for (int i = foundIndex; i < liveCount - 1; i++) {
            liveStudents[i] = liveStudents[i + 1];
        }
        liveStudents[liveCount - 1] = null;
        liveCount--;
    }

    /**
     * BST and HashTable have no delete methods. After a list delete, rebuild them
     * from the remaining Student objects so all three stay consistent.
     */
    private void rebuildSearchIndexes() {
        studentTree = new BST();
        studentHash = new HashTable();
        for (int i = 0; i < liveCount; i++) {
            studentTree.insert(liveStudents[i]);
            studentHash.insert(liveStudents[i]);
        }
    }

    private void stackMenu() {
        boolean back = false;
        while (!back) {
            System.out.println();
            System.out.println("--- Recent Actions (Stack, LIFO) ---");
            System.out.println("1. Display recent actions");
            System.out.println("2. Pop most recent action record");
            System.out.println("0. Back to main menu");
            System.out.print("Enter choice: ");
            int choice = readMenuChoice();
            switch (choice) {
                case 1:
                    actionStack.display();
                    break;
                case 2:
                    String action = actionStack.pop();
                    if (action != null) {
                        System.out.println("Popped action: " + action);
                        System.out.println("Note: this only removes the history line. Student data is not undone.");
                    }
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid menu choice. Enter a number from the menu.");
                    break;
            }
        }
    }

    private void queueMenu() {
        boolean back = false;
        while (!back) {
            System.out.println();
            System.out.println("--- Service Requests (Queue, FIFO) ---");
            System.out.println("1. Add service request");
            System.out.println("2. Process next request");
            System.out.println("3. Display waiting requests");
            System.out.println("0. Back to main menu");
            System.out.print("Enter choice: ");
            int choice = readMenuChoice();
            switch (choice) {
                case 1:
                    addServiceRequest();
                    break;
                case 2:
                    ServiceRequest next = serviceQueue.processNext();
                    if (next != null) {
                        System.out.println("Processed: " + next);
                    }
                    break;
                case 3:
                    serviceQueue.display();
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid menu choice. Enter a number from the menu.");
                    break;
            }
        }
    }

    private void addServiceRequest() {
        String studentId = readRequiredText("Student ID");
        if (studentId == null) {
            return;
        }
        String description = readRequiredText("Request description");
        if (description == null) {
            return;
        }
        serviceQueue.add(new ServiceRequest(studentId, description));
        System.out.println("Request added: " + studentId + " - " + description);
    }

    private void bstMenu() {
        boolean back = false;
        while (!back) {
            System.out.println();
            System.out.println("--- Binary Search Tree (by Student ID) ---");
            System.out.println("1. Display students in ID order");
            System.out.println("2. Search by Student ID");
            System.out.println("0. Back to main menu");
            System.out.print("Enter choice: ");
            int choice = readMenuChoice();
            switch (choice) {
                case 1:
                    studentTree.inOrderTraversal();
                    break;
                case 2:
                    String studentId = readRequiredText("Student ID");
                    if (studentId == null) {
                        break;
                    }
                    Student found = studentTree.search(studentId);
                    if (found == null) {
                        System.out.println("BST: student not found.");
                    } else {
                        System.out.println("BST: " + found);
                    }
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid menu choice. Enter a number from the menu.");
                    break;
            }
        }
    }

    private void hashMenu() {
        boolean back = false;
        while (!back) {
            System.out.println();
            System.out.println("--- Hash Table (Student ID) ---");
            System.out.println("1. Display buckets");
            System.out.println("2. Search by Student ID");
            System.out.println("0. Back to main menu");
            System.out.print("Enter choice: ");
            int choice = readMenuChoice();
            switch (choice) {
                case 1:
                    studentHash.display();
                    break;
                case 2:
                    String studentId = readRequiredText("Student ID");
                    if (studentId == null) {
                        break;
                    }
                    Student found = studentHash.search(studentId);
                    if (found == null) {
                        System.out.println("Hash table: student not found.");
                    } else {
                        System.out.println("Hash table: " + found);
                    }
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid menu choice. Enter a number from the menu.");
                    break;
            }
        }
    }

    private void graphMenu() {
        boolean back = false;
        while (!back) {
            System.out.println();
            System.out.println("--- Campus Map (Graph) ---");
            System.out.println("1. Add location");
            System.out.println("2. Remove location");
            System.out.println("3. Add connection");
            System.out.println("4. Remove connection");
            System.out.println("5. Display campus network");
            System.out.println("6. BFS traversal");
            System.out.println("0. Back to main menu");
            System.out.print("Enter choice: ");
            int choice = readMenuChoice();
            switch (choice) {
                case 1:
                    String addName = readRequiredText("Location name");
                    if (addName != null) {
                        campusGraph.addLocation(addName);
                    }
                    break;
                case 2:
                    String removeName = readRequiredText("Location name to remove");
                    if (removeName != null) {
                        campusGraph.removeLocation(removeName);
                    }
                    break;
                case 3:
                    String fromAdd = readRequiredText("First location");
                    if (fromAdd == null) {
                        break;
                    }
                    String toAdd = readRequiredText("Second location");
                    if (toAdd != null) {
                        campusGraph.addConnection(fromAdd, toAdd);
                    }
                    break;
                case 4:
                    String fromRemove = readRequiredText("First location");
                    if (fromRemove == null) {
                        break;
                    }
                    String toRemove = readRequiredText("Second location");
                    if (toRemove != null) {
                        campusGraph.removeConnection(fromRemove, toRemove);
                    }
                    break;
                case 5:
                    campusGraph.displayConnections();
                    break;
                case 6:
                    String start = readRequiredText("Start location for BFS");
                    if (start != null) {
                        campusGraph.bfsTraversal(start);
                    }
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid menu choice. Enter a number from the menu.");
                    break;
            }
        }
    }

    /**
     * Reads a menu number. Letters or empty input are rejected without crashing.
     */
    private int readMenuChoice() {
        String line = scanner.nextLine();
        if (line == null) {
            return -1;
        }
        line = line.trim();
        if (line.isEmpty()) {
            System.out.println("Input cannot be empty.");
            return -1;
        }
        try {
            return Integer.parseInt(line);
        } catch (NumberFormatException ex) {
            System.out.println("Please enter a whole number.");
            return -1;
        }
    }

    /**
     * Reads a non-empty line. Returns null if the user entered nothing.
     */
    private String readRequiredText(String label) {
        System.out.print(label + ": ");
        String line = scanner.nextLine();
        if (line == null) {
            System.out.println(label + " cannot be empty.");
            return null;
        }
        line = line.trim();
        if (line.isEmpty()) {
            System.out.println(label + " cannot be empty.");
            return null;
        }
        return line;
    }

    /**
     * Reads marks as a number from 0 to 100. Returns null if invalid.
     */
    private Integer readMarks() {
        System.out.print("Marks (0-100): ");
        String line = scanner.nextLine();
        if (line == null) {
            System.out.println("Marks cannot be empty.");
            return null;
        }
        line = line.trim();
        if (line.isEmpty()) {
            System.out.println("Marks cannot be empty.");
            return null;
        }
        int marks;
        try {
            marks = Integer.parseInt(line);
        } catch (NumberFormatException ex) {
            System.out.println("Marks must be a whole number.");
            return null;
        }
        if (marks < 0 || marks > 100) {
            System.out.println("Marks must be between 0 and 100.");
            return null;
        }
        return Integer.valueOf(marks);
    }
}
