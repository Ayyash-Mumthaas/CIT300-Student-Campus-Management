package cit300.stack;

/**
 * Stack of recent student-management actions.
 * LIFO means Last In, First Out: the last action pushed is the first one popped.
 */
public class ActionStack {

    /**
     * One box in the stack. Each box stores an action and a link to the box below it.
     */
    private class Node {
        String action;
        Node next;

        Node(String action) {
            this.action = action;
            this.next = null;
        }
    }

    // top is the most recent action (the "top of the pile")
    private Node top;

    public ActionStack() {
        this.top = null;
    }

    /**
     * Adds a new action to the top of the stack.
     */
    public void push(String action) {
        Node newNode = new Node(action);
        newNode.next = top;
        top = newNode;
    }

    /**
     * Removes and returns the most recent action.
     * If the stack is empty, prints a message and returns null (does not crash).
     */
    public String pop() {
        if (isEmpty()) {
            System.out.println("The action stack is empty. Nothing to undo.");
            return null;
        }

        String action = top.action;
        top = top.next;
        return action;
    }

    /**
     * Prints actions from newest (top) to oldest.
     */
    public void display() {
        if (isEmpty()) {
            System.out.println("The action stack is empty.");
            return;
        }

        System.out.println("Recent actions (newest first):");
        Node current = top;
        while (current != null) {
            System.out.println(current.action);
            current = current.next;
        }
    }

    /**
     * Returns true if the stack has no actions.
     */
    public boolean isEmpty() {
        return top == null;
    }
}
