package cit300.hash;

import cit300.student.Student;

/**
 * Hash table for Student records keyed by Student ID.
 * Collisions are handled with separate chaining (linked list in each bucket).
 */
public class HashTable {

    private static final int TABLE_SIZE = 10;

    // Each array slot is the head of a chain of nodes
    private HashNode[] buckets;
    private int count;

    /**
     * One node in a bucket's linked chain.
     */
    private static class HashNode {
        Student student;
        HashNode next;

        HashNode(Student student) {
            this.student = student;
            this.next = null;
        }
    }

    public HashTable() {
        buckets = new HashNode[TABLE_SIZE];
        count = 0;
    }

    /**
     * Simple hash: add character codes of the Student ID, then mod table size.
     */
    private int getIndex(String studentId) {
        int sum = 0;
        for (int i = 0; i < studentId.length(); i++) {
            sum = sum + studentId.charAt(i);
        }
        return sum % TABLE_SIZE;
    }

    /**
     * Insert a student into the correct bucket. No duplicate Student IDs.
     */
    public void insert(Student student) {
        if (student == null) {
            return;
        }

        String id = student.getStudentId();
        int index = getIndex(id);

        // Check if this ID is already in the chain
        HashNode current = buckets[index];
        while (current != null) {
            if (current.student.getStudentId().equals(id)) {
                return; // duplicate — do not insert
            }
            current = current.next;
        }

        // Add new node at the front of the chain
        HashNode newNode = new HashNode(student);
        newNode.next = buckets[index];
        buckets[index] = newNode;
        count++;
    }

    /**
     * Search one bucket for the Student ID.
     */
    public Student search(String studentId) {
        if (studentId == null) {
            return null;
        }

        int index = getIndex(studentId);
        HashNode current = buckets[index];

        while (current != null) {
            if (current.student.getStudentId().equals(studentId)) {
                return current.student;
            }
            current = current.next;
        }
        return null;
    }

    /**
     * True if no students are stored.
     */
    public boolean isEmpty() {
        return count == 0;
    }

    /**
     * Show each bucket index and the Student IDs in that bucket (for demos).
     */
    public void display() {
        System.out.println("Hash table (separate chaining), size " + TABLE_SIZE + ":");
        for (int i = 0; i < TABLE_SIZE; i++) {
            System.out.print("Bucket " + i + ": ");
            HashNode current = buckets[i];
            if (current == null) {
                System.out.println("(empty)");
            } else {
                while (current != null) {
                    System.out.print(current.student.getStudentId());
                    if (current.next != null) {
                        System.out.print(" -> ");
                    }
                    current = current.next;
                }
                System.out.println();
            }
        }
        System.out.println();
    }
}
