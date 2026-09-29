package cit300.tree;

import cit300.student.Student;

/**
 * Binary Search Tree of Student records. Student ID is the key.
 */
public class BST {

    private TreeNode root;

    public BST() {
        root = null;
    }

    /**
     * Insert a student by Student ID. Smaller IDs go left, larger IDs go right.
     * Does not insert if the same Student ID is already in the tree.
     */
    public void insert(Student student) {
        if (student == null) {
            return;
        }
        root = insertRecursive(root, student);
    }

    private TreeNode insertRecursive(TreeNode node, Student student) {
        if (node == null) {
            return new TreeNode(student);
        }

        String newId = student.getStudentId();
        String nodeId = node.getStudent().getStudentId();
        int compare = newId.compareTo(nodeId);

        if (compare < 0) {
            node.setLeft(insertRecursive(node.getLeft(), student));
        } else if (compare > 0) {
            node.setRight(insertRecursive(node.getRight(), student));
        }
        // If compare == 0, same ID already exists — do nothing (no duplicate)

        return node;
    }

    /**
     * Find a student by Student ID, or return null if not found.
     */
    public Student search(String studentId) {
        if (studentId == null) {
            return null;
        }
        TreeNode found = searchRecursive(root, studentId);
        if (found == null) {
            return null;
        }
        return found.getStudent();
    }

    private TreeNode searchRecursive(TreeNode node, String studentId) {
        if (node == null) {
            return null;
        }

        String nodeId = node.getStudent().getStudentId();
        int compare = studentId.compareTo(nodeId);

        if (compare == 0) {
            return node;
        } else if (compare < 0) {
            return searchRecursive(node.getLeft(), studentId);
        } else {
            return searchRecursive(node.getRight(), studentId);
        }
    }

    /**
     * Print all students in ascending Student ID order (in-order traversal).
     */
    public void inOrderTraversal() {
        if (root == null) {
            System.out.println("BST is empty. No students to display.");
            return;
        }
        System.out.println("In-order traversal (ascending Student ID):");
        inOrderRecursive(root);
        System.out.println();
    }

    private void inOrderRecursive(TreeNode node) {
        if (node == null) {
            return;
        }
        inOrderRecursive(node.getLeft());
        System.out.println(node.getStudent());
        inOrderRecursive(node.getRight());
    }

    /**
     * True if the tree has no nodes.
     */
    public boolean isEmpty() {
        return root == null;
    }
}
