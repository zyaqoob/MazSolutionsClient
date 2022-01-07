/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Aitor Ruiz de Gauna
 */
/**
 * Entity that contains the info of a subject.
 */
@XmlRootElement
public class Subject implements Serializable {

    private static final long serialVersionUID = 1L;

    //Subject identifier.
    private Long idSubject;
    //Name of the subject.
    private String name;
    //Password to register in the subject
    private String password;
    //TeacherCourse where the subject appears
    private Set<TeacherCourseSubject> teacherCourseSubjects;
    //Collection of exams that the subject has had
    private Set<Exam>exams;
    //Collection of courses where the subject is teached
    private Set<CourseSubject> courseSubjects;
    /**
    * Method that return the identifier of the subject.
    * @return idSubject
    */
    public Long getIdSubject() {
        return idSubject;
    }
    /**
    * Method that set the value of the identifier of the subject.
    * @param idSubject
    */
    public void setIdSubject(Long idSubject) {
        this.idSubject = idSubject;
    }
    /**
    * Method that return the name of the subject.
    * @return name
    */
    public String getName() {
        return name;
    }
    /**
    * Method that set the value of the name of the subject.
    * @param name
    */
    public void setName(String name) {
        this.name = name;
    }
    /**
    * Method that return the password of the subject.
    * @return password
    */
    public String getPassword() {
        return password;
    }
    /**
    * Method that set the value of the password of the subject.
    * @param password
    */
    public void setPassword(String password) {
        this.password = password;
    }
    /**
    * Method that return the TeacherCourse of the subject.
    * @return teacherCourse
    */
    @XmlTransient
    public Set<TeacherCourseSubject> getTeacherCourseSubjects() {    
        return teacherCourseSubjects;
    }
    /**
     * Method that set the value of the TeacherCourse of the subject.
     * @param teacherCourseSubjects
     */
    public void setTeacherCourseSubjects(Set<TeacherCourseSubject> teacherCourseSubjects) {    
        this.teacherCourseSubjects = teacherCourseSubjects;
    }

    /**
     * Method that return the Exams of the subject.
     * @return exams
     */
    @XmlTransient
    public Set<Exam> getExams() {
        return exams;
    }
    /**
     * Method that set the value of the exams of the subject.
     * @param exams 
     */
    public void setExams(Set<Exam> exams) {
        this.exams = exams;
    }
    /**
     * Method that return the courses where the subject is teached.
     * @return courses
     */
    @XmlTransient
    public Set<CourseSubject> getCourseSubjects() {    
        return courseSubjects;
    }
    /**
     * Method that set the value of the courses where the subject is teached.
     * @param courseSubjects 
     */
    public void setCourseSubjects(Set<CourseSubject> courseSubjects) {
        this.courseSubjects = courseSubjects;
    }  
    /**
     * Integer representation for Subject instance.
     * @return 
     */
    @Override    
    public int hashCode() {
        int hash = 3;
        hash = 31 * hash + Objects.hashCode(this.idSubject);
        return hash;
    }  

    /**
     * 
     * Method that compares if two objects of Subjects are equals.
     * @param object
     * @return boolean
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Subject)) {
            return false;
        }
        Subject other = (Subject) object;
        if ((this.idSubject == null && other.idSubject != null) || (this.idSubject != null && !this.idSubject.equals(other.idSubject))) {
            return false;
        }
        return true;
    }  
    /**
     * Method that return a String of the parameters of Subject.
     * @return String
     */
    @Override
    public String toString() {
        return "Subject{" + "idSubject=" + idSubject + ", name=" + name + ", password=" + password + ", teacherCourses=" + teacherCourseSubjects + ", exams=" + exams + ", courses=" + courseSubjects + '}';
    }
    
}
