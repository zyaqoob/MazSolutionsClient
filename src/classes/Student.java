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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Entity representing Student of the application. It contains the following
 * fields: year, sessions and courser.
 *
 * @author Miguel Ángel Sánchez
 */

@XmlRootElement
public class Student extends User implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * Year when the Student is registered.
     */
    private Date year;
    /**
     * Examn sessions where the student are being evaluated.
     */
    private Set<ExamSession> sessions;
    /**
     * Course where the student is registered.
     */
    private Course course;

    /**
     *
     * @return This method returns the year of the student.
     */
    public Date getYear() {
        return year;
    }

    /**
     *
     * @param year This method set the year of the student.
     */
    public void setYear(Date year) {
        this.year = year;
    }

    /**
     *
     * @return This method returns a Set with the exam sessions of the student.
     */
    public Set<ExamSession> getSessions() {
        return sessions;
    }

    /**
     *
     * @param sessions This method set the sessions of the student.
     */
    public void setSessions(Set<ExamSession> sessions) {
        this.sessions = sessions;
    }

    /**
     *
     * @return This method returns a course.
     */
    @XmlTransient
    public Course getCourse() {
        return course;
    }

    /**
     *
     * @param course This method set a course.
     */
    public void setCourse(Course course) {
        this.course = course;
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
