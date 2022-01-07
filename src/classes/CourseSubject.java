/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.io.Serializable;
import java.util.Objects;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author Aitor Ruiz de Gauna,Miguel Sanchez,Zeeshan Yaqoob.
 */
/**
 * Entity that represents the relation between courses and subjects.
 */

@XmlRootElement
public class CourseSubject implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * Class that contains the id's of the CourseSubject entity.
     */
    private CourseSubjectId courseSubjectId;
    /**
     * Total Hours that the courseSubject has.
     */
    private float totalHours;
    /**
     * Course of the subject.
     */
    private Course course;
    /**
     * Subject of the course.
     */
    private Subject subject;
    /**
     * Method that return the id's of the entity.
     * @return courseSubjectId
     */
    public CourseSubjectId getCourseSubjectId() {
        return courseSubjectId;
    }
    /**
     * Method that set the value of the id's.
     * @param courseSubjectId 
     */
    public void setCourseSubjectId(CourseSubjectId courseSubjectId) {
        this.courseSubjectId = courseSubjectId;
    }
    /**
     * Method that return the totalHours.
     * @return totalHours
     */
    public float getTotalHours() {
        return totalHours;
    }
    /**
     * Method that set the value of the totalHours
     * @param totalHours 
     */
    public void setTotalHours(float totalHours) {
        this.totalHours = totalHours;
    }
    /**
     * Method that return the course.
     * @return course
     */
    public Course getCourse() {
        return course;
    }
    /**
     * Method that set the value of the course.
     * @param course 
     */
    public void setCourse(Course course) {
        this.course = course;
    }
    /**
     * Method that return the subject.
     * @return subject
     */
    public Subject getSubject() {
        return subject;
    }
    /**
     * Method that set the value of the subject.
     * @param subject 
     */
    public void setSubject(Subject subject) {
        this.subject = subject;
    }
    /**
     * Integer representation for CourseSubject instance.
     * @return 
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.courseSubjectId);
        return hash;
    }
     /**
     * Compares two CourseSubject objects for equality. This method consider a CourseSubject 
     * equal to another CourseSubject if their id fields have the same value. 
     * @param obj The other CourseSubject to compare to
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
        final CourseSubject other = (CourseSubject) obj;
        if (Float.floatToIntBits(this.totalHours) != Float.floatToIntBits(other.totalHours)) {
            return false;
        }
        if (!Objects.equals(this.courseSubjectId, other.courseSubjectId)) {
            return false;
        }
        if (!Objects.equals(this.course, other.course)) {
            return false;
        }
        if (!Objects.equals(this.subject, other.subject)) {
            return false;
        }
        return true;
    }
    /**
     * Obtains a string representation of the CourseSubject.
     * @return The String representing the CourseSubject.
     */
    @Override
    public String toString() {
        return "CourseSubject{" + "courseSubjectId=" + courseSubjectId + ", totalHours=" + totalHours + ", course=" + course + ", subject=" + subject + '}';
    }
    
}
