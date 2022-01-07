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
public class CourseSubjectId implements Serializable{
    /**
     * The id of the course
     */
    private Long idCourse;
    /**
     * The id of the subject
     */
    private Long idSubject;
    /**
     * Method that return the idCourse.
     * @return idCourse
     */
    public Long getIdCourse() {
        return idCourse;
    }
    /**
     * Method that set the value of the idCourse
     * @param idCourse 
     */
    public void setIdCourse(Long idCourse) {
        this.idCourse = idCourse;
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
     * Integer representation for CourseSubjectId instance.
     * @return 
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.idCourse);
        hash = 59 * hash + Objects.hashCode(this.idSubject);
        return hash;
    }
     /**
     * Compares two CourseSubjectId objects for equality. This method consider a CourseSubjectId 
     * equal to another CourseSubjectId if their id fields have the same value. 
     * @param obj The other CourseSubjectId to compare to
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
        final CourseSubjectId other = (CourseSubjectId) obj;
        if (!Objects.equals(this.idCourse, other.idCourse)) {
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
        return "CourseSubjectId{" + "idCourse=" + idCourse + ", idSubject=" + idSubject + '}';
    }
    
    
}
