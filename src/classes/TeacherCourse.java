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
 *
 * @author Aitor Ruiz de Gauna
 */
/**
 *Entity that has the info of the courses of the Teacher.
 */
@XmlRootElement
public class TeacherCourse implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idTeacherCourse;
    // Date when the TeacherCourse starts.
    private Date dateStart;
    // Date when the TeacherCourse ends.
    private Date dateEnd;
    //Collection of the subject that the teacher has.
    private Set<TeacherCourseSubject> teacherCourseSubjects;
    //Teacher of the TeacherCourse
    private Teacher teacher;
    /**
     * Method that returns the class that contains the id's of TeacherCourse.
     * @return idTeacherCourseId;
     */
    public Long getIdTeacherCourse() {
        return idTeacherCourse;
    }
    /**
     * Method that set the value of the object of the class TeacherCourseId that contains the id's of TeacherCourse.
     * @param idTeacherCourse 
     */
    public void setIdTeacherCourseId(Long idTeacherCourse) {
        this.idTeacherCourse = idTeacherCourse;
    }
    /**
     * Method that returns the date start of the TeacherCourse.
     * @return dateStart
     */
    public Date getDateStart() {
        return dateStart;
    }
    /**
     * Method that set the value of the dateStart of the TeacherCourse.
     * @param dateStart 
     */
    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }
    /**
     * Method that return the value of the dateEnd of TeacherCourse.
     * @return 
     */
    public Date getDateEnd() {
        return dateEnd;
    }
    /**
     * Method that set the value of the dateEnd of TeacherCourse.
     * @param dateEnd 
     */
    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }
    /**
     * Method that return the value of the collection of subjects of TeacherCourse.
     * @return 
     */
    @XmlTransient
    public Set<TeacherCourseSubject> getTeacherCourseSubjects() {    
        return teacherCourseSubjects;
    }
    /**
     * Method that set the value of the collection of subjects of TeacherCourse.
     * @param teacherCourseSubjects 
     */
    public void setTeacherCourseSubjects(Set<TeacherCourseSubject> teacherCourseSubjects) {    
        this.teacherCourseSubjects = teacherCourseSubjects;
    }

    /**
     * Method that return the value of the collection of teachers of TeacherCourse.
     * @return teacher
     */
    public Teacher getTeacher() {
        return teacher;
    }
    /**
     * Method that set the value of the collection of subjects of TeacherCourse.
     * @param teacher 
     */
    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
    /**
     * Integer representation for TeacherCourse instance.
     * @return 
     */
    @Override   
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.idTeacherCourse);  
        return hash;
    }

    /**
     * Method that compares if two objects of TeacherCourse are equals.
     * @param obj
     * @return boolean
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
        final TeacherCourse other = (TeacherCourse) obj;
        if (!Objects.equals(this.idTeacherCourse, other.idTeacherCourse)) {
            return false;
        }
        return true;
    }
    /**
     * Method that return a String of the parameters of TeacherCourse.
     * @return String
     */
    @Override
    public String toString() {
        return "TeacherCourse{" + "idTeacherCourseId=" + idTeacherCourse + ", dateStart=" + dateStart + ", dateEnd=" + dateEnd + ", subjects=" + teacherCourseSubjects+  ", teacher=" + teacher + '}';
    }
}