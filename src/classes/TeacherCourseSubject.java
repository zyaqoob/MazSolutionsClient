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
 * Entity that represents the relationship between TeacherCourses and subjects.
 */
@XmlRootElement
public class TeacherCourseSubject implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * Class that contains the id's of the TeacherCourseSubject entity.
     */
    private TeacherCourseSubjectId teacherCourseSubjectId;
    /**
     * Total Hours that the TeacherCourseSubject has.
     */
    private float totalHours;
    /**
     * TeacherCourse of the subject.
     */

    private TeacherCourse teacherCourse;   
    /**
     * Subject of the TeacherCourse.
     */
    private Subject subject;
     /**
     * Method that return the id's of the entity.
     * @return teacherCourseSubjectId
     */
    public TeacherCourseSubjectId getTeacherCourseSubjectId() {
        return teacherCourseSubjectId;
    }
    /**
     * Method that set the value of the id's.
     * @param teacherCourseSubjectId 
     */
    public void setTeacherCourseSubjectId(TeacherCourseSubjectId teacherCourseSubjectId) {
        this.teacherCourseSubjectId = teacherCourseSubjectId;
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
     * Method that return the TeacherCourse.
     * @return teacherCourse
     */
    public TeacherCourse getTeacherCourse() {
        return teacherCourse;
    }
     /**
     * Method that set the value of the TeacherCourse.
     * @param teacherCourse 
     */
    public void setTeacherCourse(TeacherCourse teacherCourse) {
        this.teacherCourse = teacherCourse;
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
        final TeacherCourseSubject other = (TeacherCourseSubject) obj;
        if (Float.floatToIntBits(this.totalHours) != Float.floatToIntBits(other.totalHours)) {
            return false;
        }
        if (!Objects.equals(this.teacherCourseSubjectId, other.teacherCourseSubjectId)) {
            return false;
        }
        if (!Objects.equals(this.teacherCourse, other.teacherCourse)) {
            return false;
        }
        if (!Objects.equals(this.subject, other.subject)) {
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
