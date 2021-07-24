package model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class FeedbackDocument implements Serializable {

    private Assignment assignment;
    private String studentId;
    private HashMap<String, String> headingAndData;
    private double grade;

    public FeedbackDocument(Assignment assignment, String studentId) {
        this.assignment = assignment;
        this.studentId = studentId;
        this.headingAndData = new HashMap<String, String>();

        assignment.getAssignmentHeadings().forEach(heading -> {
            headingAndData.put(heading, "");
        });

        this.grade = 0.0;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public double getGrade() {
        return this.grade;
    }

    public void setDataForHeading(String heading, String data) {
        headingAndData.put(heading, data);
    }

    public String getHeadingData(String heading) {
        return this.headingAndData.get(heading);
    }
    public Assignment getAssignment() {
        return assignment;
    }

    public String getStudentId() {
        return studentId;
    }

    public List<String> getHeadings() {
        return assignment.getAssignmentHeadings();
    }

    @Override
    public String toString() {
        return "FeedbackDocument{" +
                "assignment=" + assignment.getDatabaseName() +
                ", studentId='" + studentId + '\'' +
                '}';
    }
}
