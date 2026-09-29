package cit300.student;

/**
 * One student record for the campus system.
 * This is the shared Student class for the whole team.
 */
public class Student {

    private String studentId;
    private String name;
    private String programme;
    private int marks;

    public Student(String studentId, String name, String programme, int marks) {
        this.studentId = studentId;
        this.name = name;
        this.programme = programme;
        this.marks = marks;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public String getProgramme() {
        return programme;
    }

    public int getMarks() {
        return marks;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProgramme(String programme) {
        this.programme = programme;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }

    @Override
    public String toString() {
        return "Student ID: " + studentId
                + ", Name: " + name
                + ", Programme: " + programme
                + ", Marks: " + marks;
    }
}
