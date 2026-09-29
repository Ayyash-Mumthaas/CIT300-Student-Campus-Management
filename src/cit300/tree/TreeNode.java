package cit300.tree;

import cit300.student.Student;

/**
 * One node in a binary search tree. Each node holds one Student
 * and links to a left child and a right child.
 */
public class TreeNode {

    private Student student;
    private TreeNode left;
    private TreeNode right;

    public TreeNode(Student student) {
        this.student = student;
        this.left = null;
        this.right = null;
    }

    public Student getStudent() {
        return student;
    }

    public TreeNode getLeft() {
        return left;
    }

    public void setLeft(TreeNode left) {
        this.left = left;
    }

    public TreeNode getRight() {
        return right;
    }

    public void setRight(TreeNode right) {
        this.right = right;
    }
}
