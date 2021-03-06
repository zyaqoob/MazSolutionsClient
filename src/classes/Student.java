/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.Set;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Entity representing Student of the application. It contains the following
 * fields: year, sessions and courser.
 *
 * @author Aitor Ruiz de Gauna
 */
@XmlRootElement
public class Student extends User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Year when the Student is registered.
     *
     * @param year
     * @param sessions
     * @param course
     */
    public Student(Date year, ObservableList<ExamSession> sessions, Course course) {
        this.year = new SimpleObjectProperty<>(year);
        this.sessions = new SimpleListProperty<>(sessions);
        this.course = new SimpleObjectProperty<>(course);
    }

    public Student() {
        this.year = new SimpleObjectProperty<>();
        this.sessions = new SimpleListProperty<>();
        this.course = new SimpleObjectProperty<>();
    }

    private SimpleObjectProperty<Date> year;
    /**
     * Examn sessions where the student are being evaluated.
     */
    private SimpleListProperty<ExamSession> sessions;
    /**
     * Course where the student is registered.
     */
    private SimpleObjectProperty<Course> course;

    /**
     *
     * @return This method returns the year of the student.
     */
    public Date getYear() {
        return year.get();
    }

    /**
     *
     * @param year This method set the year of the student.
     */
    public void setYear(Date year) {
        this.year.set(year);
    }

    /**
     *
     * @return This method returns a Set with the exam sessions of the student.
     */
    @XmlTransient
    public ObservableList<ExamSession> getSessions() {
        return sessions.get();
    }

    /**
     *
     * @param sessions This method set the sessions of the student.
     */
    public void setSessions(ObservableList<ExamSession> sessions) {
        this.sessions.set(sessions);
    }

    /**
     *
     * @return This method returns a course.
     */
    public Course getCourse() {
        return course.get();
    }

    /**
     *
     * @param course This method set a course.
     */
    public void setCourse(Course course) {
        this.course.set(course);
    }

    /**
     *
     * @return Integer representation for Student instance.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.getIdUser());
        return hash;
    }

    /**
     * Compares two Student objects for equality. This method consider a Student
     * equal to another Student if their id fields have the same value.
     *
     * @param obj The other Student to compare to
     * @return Returns true or false depending if the fields are equals.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Student other = (Student) obj;
        if (!Objects.equals(this.year, other.year)) {
            return false;
        }
        if (!Objects.equals(this.sessions, other.sessions)) {
            return false;
        }
        if (!Objects.equals(this.course, other.course)) {
            return false;
        }
        if (!Objects.equals(this.getIdUser(), other.getIdUser())) {
            return false;
        }
        return true;
    }

    /**
     * Obtains a string representation of the Student.
     *
     * @return The String representing the Student.
     */
    @Override
    public String toString() {
        return "Student{" + "year=" + year + ", sessions=" + sessions + ", course=" + course + '}';
    }

}