/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Aitor Ruiz de Gauna,Miguel Sanchez,Zeeshan Yaqoob.
 */
/**
 * Class that contains the id's of the CourseSubject.
 */
public class TeacherCourseSubjectId implements Serializable {
    /**
     * The id of the teacherCourse
     */
    private Long idTeacherCourse;
    /**
     * The id of the subject
     */
    private Long idSubject;
    /**
     * Method that return the idTeacherCourse.
     * @return idTeacherCourse
     */
    public Long getIdTeacherCourse() {
        return idTeacherCourse;
    }
    /**
     * Method that set the value of the idTeacherCourse
     * @param idTeacherCourse 
     */
    public void setIdTeacherCourse(Long idTeacherCourse) {
        this.idTeacherCourse = idTeacherCourse;
    }
    /**
     * Method that return the idSubject.
     * @return idSubject
     */
    public Long getIdSubject() {
        return idSubject;
    }
    /**
     * Method that set the value of the idSubject
     * @param idSubject 
     */
    public void setIdSubject(Long idSubject) {
        this.idSubject = idSubject;
    }
     /**
     * Integer representation for TeacherCourseSubjectId instance.
     * @return 
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.idTeacherCourse);
        hash = 59 * hash + Objects.hashCode(this.idSubject);
        return hash;
    }
     /**
     * Compares two TeacherCourseSubject objects for equality. This method consider a TeacherCourseSubjectId 
     * equal to another TeacherCourseSubjectId if their id fields have the same value. 
     * @param obj The other TeacherCourseSubjectId to compare to
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
        final TeacherCourseSubjectId other = (TeacherCourseSubjectId) obj;
        if (!Objects.equals(this.idTeacherCourse, other.idTeacherCourse)) {
            return false;
        }
        if (!Objects.equals(this.idSubject, other.idSubject)) {
            return false;
        }
        return true;
    }
    /**
     * Obtains a string representation of the CourseSubjectId.
     * @return The String representing the CourseSubjectId.
     */
    @Override
    public String toString() {
        return "TeacherCourseSubjectId{" + "idTeacher=" + idTeacherCourse + ", idSubject=" + idSubject + '}';
    }

}
