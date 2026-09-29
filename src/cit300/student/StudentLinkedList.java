package cit300.student;

public class StudentLinkedList {

    private StudentNode head;

    public StudentLinkedList() {
        this.head = null;
    }

    public void addStudent(Student student) {
        if (student.getMarks() < 0 || student.getMarks() > 100) {
            System.out.println("Marks must be between 0 and 100.");
            return;
        }

        StudentNode current = head;
        while (current != null) {
            if (current.getStudent().getStudentId().equals(student.getStudentId())) {
                System.out.println("Student ID already exists.");
                return;
            }
            current = current.getNext();
        }

        StudentNode newNode = new StudentNode(student);

        if (head == null) {
            head = newNode;
            return;
        }

        current = head;
        while (current.getNext() != null) {
            current = current.getNext();
        }
        current.setNext(newNode);
    }

    public Student searchStudent(String studentId) {
        StudentNode current = head;
        while (current != null) {
            if (current.getStudent().getStudentId().equals(studentId)) {
                return current.getStudent();
            }
            current = current.getNext();
        }
        return null;
    }

    public boolean updateStudent(String studentId, String name, String programme, int marks) {
        if (marks < 0 || marks > 100) {
            System.out.println("Marks must be between 0 and 100.");
            return false;
        }

        Student student = searchStudent(studentId);
        if (student == null) {
            return false;
        }
        student.setName(name);
        student.setProgramme(programme);
        student.setMarks(marks);
        return true;
    }

    public boolean deleteStudent(String studentId) {
        if (head == null) {
            return false;
        }

        if (head.getStudent().getStudentId().equals(studentId)) {
            head = head.getNext();
            return true;
        }

        StudentNode current = head;
        while (current.getNext() != null) {
            if (current.getNext().getStudent().getStudentId().equals(studentId)) {
                current.setNext(current.getNext().getNext());
                return true;
            }
            current = current.getNext();
        }

        return false;
    }

    public void displayStudents() {
        if (head == null) {
            System.out.println("The student list is empty.");
            return;
        }

        StudentNode current = head;
        while (current != null) {
            System.out.println(current.getStudent().toString());
            current = current.getNext();
        }
    }

    public boolean isEmpty() {
        return head == null;
    }
}
