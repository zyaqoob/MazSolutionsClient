/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.io.Serializable;
import java.util.Objects;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleObjectProperty;
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
     * @param courseSubjectId
     * @param totalHours
     * @param course
     * @param subject
     */
    public CourseSubject(CourseSubjectId courseSubjectId, float totalHours,Course course,Subject subject) {
        this.courseSubjectId=new SimpleObjectProperty<>(courseSubjectId);
        this.totalHours=new SimpleFloatProperty(totalHours);
        this.course=new SimpleObjectProperty<>(course);
        this.subject= new SimpleObjectProperty<>(subject);
    }

    public CourseSubject() {
        this.courseSubjectId=new SimpleObjectProperty<>();
        this.totalHours=new SimpleFloatProperty();
        this.course=new SimpleObjectProperty<>();
        this.subject= new SimpleObjectProperty<>();
    }

    
    private SimpleObjectProperty<CourseSubjectId> courseSubjectId;
    /**
     * Total Hours that the courseSubject has.
     */
    private SimpleFloatProperty totalHours;
    /**
     * Course of the subject.
     */
    private SimpleObjectProperty<Course> course;
    /**
     * Subject of the course.
     */
    private SimpleObjectProperty<Subject> subject;
    /**
     * Method that return the id's of the entity.
     * @return courseSubjectId
     */
    public CourseSubjectId getCourseSubjectId() {
        return courseSubjectId.get();
    }
    /**
     * Method that set the value of the id's.
     * @param courseSubjectId 
     */
    public void setCourseSubjectId(CourseSubjectId courseSubjectId) {
        this.courseSubjectId.set(courseSubjectId);
    }
    /**
     * Method that return the totalHours.
     * @return totalHours
     */
    public float getTotalHours() {
        return totalHours.get();
    }
    /**
     * Method that set the value of the totalHours
     * @param totalHours 
     */
    public void setTotalHours(float totalHours) {
        this.totalHours.set(totalHours);
    }
    /**
     * Method that return the course.
     * @return course
     */
    public Course getCourse() {
        return course.get();
    }
    /**
     * Method that set the value of the course.
     * @param course 
     */
    public void setCourse(Course course) {
        this.course.set(course);
    }
    /**
     * Method that return the subject.
     * @return subject
     */
    public Subject getSubject() {
        return subject.get();
    }
    /**
     * Method that set the value of the subject.
     * @param subject 
     */
    public void setSubject(Subject subject) {
        this.subject.set(subject);
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