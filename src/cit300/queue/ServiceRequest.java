package cit300.queue;

/**
 * One student service request waiting in the campus service queue.
 */
public class ServiceRequest {

    private String studentId;
    private String description;

    public ServiceRequest(String studentId, String description) {
        this.studentId = studentId;
        this.description = description;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Example: S001 - Transcript
     */
    @Override
    public String toString() {
        return studentId + " - " + description;
    }
}
