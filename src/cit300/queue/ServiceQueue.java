package cit300.queue;

/**
 * Queue of service requests.
 * FIFO means First In, First Out: the first request added is the first one processed.
 */
public class ServiceQueue {

    /**
     * One box in the queue. Each box stores a request and a link to the next box.
     */
    private class Node {
        ServiceRequest request;
        Node next;

        Node(ServiceRequest request) {
            this.request = request;
            this.next = null;
        }
    }

    // front = next request to process; rear = last request added
    private Node front;
    private Node rear;

    public ServiceQueue() {
        this.front = null;
        this.rear = null;
    }

    /**
     * Adds a request at the rear (end) of the queue.
     */
    public void add(ServiceRequest request) {
        Node newNode = new Node(request);

        if (isEmpty()) {
            front = newNode;
            rear = newNode;
            return;
        }

        rear.next = newNode;
        rear = newNode;
    }

    /**
     * Removes and returns the request at the front of the queue.
     * If the queue is empty, prints a message and returns null (does not crash).
     */
    public ServiceRequest processNext() {
        if (isEmpty()) {
            System.out.println("The service queue is empty. No request to process.");
            return null;
        }

        ServiceRequest request = front.request;
        front = front.next;

        // If the last request was removed, the rear must also become empty.
        if (front == null) {
            rear = null;
        }

        return request;
    }

    /**
     * Prints requests from front (next to process) to rear (most recently added).
     */
    public void display() {
        if (isEmpty()) {
            System.out.println("The service queue is empty.");
            return;
        }

        System.out.println("Service requests (front to rear):");
        Node current = front;
        while (current != null) {
            System.out.println(current.request.toString());
            current = current.next;
        }
    }

    /**
     * Returns true if the queue has no requests.
     */
    public boolean isEmpty() {
        return front == null;
    }
}
