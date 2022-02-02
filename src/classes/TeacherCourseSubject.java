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
 * @author Aitor Ruiz de Gauna.
 */
/**
 * Entity that represents the relationship between TeacherCourses and subjects.
 */
@XmlRootElement
public class TeacherCourseSubject implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Class that contains the id's of the TeacherCourseSubject entity.
     * @param teacherCourseSubjectId
     * @param totalHours
     * @param subject
     */
    public TeacherCourseSubject(TeacherCourseSubjectId teacherCourseSubjectId, float totalHours, TeacherCourse teacherCourse,
            Subject subject) {
        this.teacherCourseSubjectId=new SimpleObjectProperty<>(teacherCourseSubjectId);
        this.totalHours=new SimpleFloatProperty(totalHours);
        this.teacherCourse=new SimpleObjectProperty<>(teacherCourse);
        this.subject=new SimpleObjectProperty<>(subject);
    }

    public TeacherCourseSubject() {
        this.teacherCourseSubjectId=new SimpleObjectProperty<>();
        this.totalHours=new SimpleFloatProperty();
        this.teacherCourse=new SimpleObjectProperty<>();
        this.subject=new SimpleObjectProperty<>();
    }

    
    private SimpleObjectProperty<TeacherCourseSubjectId> teacherCourseSubjectId;
    /**
     * Total Hours that the TeacherCourseSubject has.
     */
    private SimpleFloatProperty totalHours;
    /**
     * TeacherCourse of the subject.
     */

    private SimpleObjectProperty<TeacherCourse> teacherCourse;   
    /**
     * Subject of the TeacherCourse.
     */
    private SimpleObjectProperty<Subject> subject;
     /**
     * Method that return the id's of the entity.
     * @return teacherCourseSubjectId
     */
    public TeacherCourseSubjectId getTeacherCourseSubjectId() {
        return teacherCourseSubjectId.get();
    }
    /**
     * Method that set the value of the id's.
     * @param teacherCourseSubjectId 
     */
    public void setTeacherCourseSubjectId(TeacherCourseSubjectId teacherCourseSubjectId) {
        this.teacherCourseSubjectId.set(teacherCourseSubjectId);
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
     * Method that return the TeacherCourse.
     * @return teacherCourse
     */
    public TeacherCourse getTeacherCourse() {
        return teacherCourse.get();
    }
     /**
     * Method that set the value of the TeacherCourse.
     * @param teacherCourse 
     */
    public void setTeacherCourse(TeacherCourse teacherCourse) {
        this.teacherCourse.set(teacherCourse);
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
     * Integer representation for TeacherCourseSubject instance.
     * @return 
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.teacherCourseSubjectId);    
        return hash;
    }
    /**
     * Compares two TeacherCourseSubject objects for equality. This method consider a TeacherCourseSubject 
     * equal to another TeacherCourseSubject if their id fields have the same value. 
     * @param obj The other TeacherCourseSubject to compare to
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
     * Obtains a string representation of the TeacherCourseSubject.
     * @return The String representing the TeacherCourseSubject.
     */
    @Override
    public String toString() {
        return "TeacherCourseSubject{" + "teacherCourseSubjectId=" + teacherCourseSubjectId + ", totalHours=" + totalHours + ", teacher=" + teacherCourse + ", subject=" + subject + '}';
    }
    
}