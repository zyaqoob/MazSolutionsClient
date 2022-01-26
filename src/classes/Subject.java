/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
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

    public Subject(Long idSubject, String name, int totalHours, ObservableList<TeacherCourseSubject> teacherCourseSubjects,
            ObservableList<Exam> exams, ObservableList<CourseSubject> courseSubjects) {
        this.idSubject = new SimpleLongProperty(idSubject);
        this.name = new SimpleStringProperty(name);
        this.totalHours = new SimpleIntegerProperty(totalHours);
        this.teacherCourseSubjects = new SimpleListProperty<>(teacherCourseSubjects);
        this.exams = new SimpleListProperty<>(exams);
        this.courseSubjects = new SimpleListProperty<>(courseSubjects);
    }

    public Subject() {
        this.idSubject = new SimpleLongProperty();
        this.name = new SimpleStringProperty();
        this.totalHours = new SimpleIntegerProperty();
        this.teacherCourseSubjects = new SimpleListProperty<>();
        this.exams = new SimpleListProperty<>();
        this.courseSubjects = new SimpleListProperty<>();
    }

    //Subject identifier.
    private SimpleLongProperty idSubject;
    //Name of the subject.
    private SimpleStringProperty name;
    //Password to register in the subject
    private SimpleIntegerProperty totalHours;
    //TeacherCourse where the subject appears
    private SimpleListProperty<TeacherCourseSubject> teacherCourseSubjects;
    //Collection of exams that the subject has had
    private SimpleListProperty<Exam> exams;
    //Collection of courses where the subject is teached
    private SimpleListProperty<CourseSubject> courseSubjects;

    /**
     * Method that return the identifier of the subject.
     *
     * @return idSubject
     */
    public Long getIdSubject() {
        return idSubject.get();
    }

    /**
     * Method that set the value of the identifier of the subject.
     *
     * @param idSubject
     */
    public void setIdSubject(Long idSubject) {
        this.idSubject.set(idSubject);
    }

    /**
     * Method that return the name of the subject.
     *
     * @return name
     */
    public String getName() {
        return name.get();
    }

    /**
     * Method that set the value of the name of the subject.
     *
     * @param name
     */
    public void setName(String name) {
        this.name.set(name);
    }

    @XmlTransient
    public ObservableList<TeacherCourseSubject> getTeacherCourseSubjects() {
        return teacherCourseSubjects.get();
    }

    /**
     * Method that set the value of the TeacherCourse of the subject.
     *
     * @param teacherCourseSubjects
     */
    public void setTeacherCourseSubjects(ObservableList<TeacherCourseSubject> teacherCourseSubjects) {
        this.teacherCourseSubjects.set(teacherCourseSubjects);
    }

    /**
     * Method that return the Exams of the subject.
     *
     * @return exams
     */
    @XmlTransient
    public ObservableList<Exam> getExams() {
        return exams.get();
    }

    /**
     * Method that set the value of the exams of the subject.
     *
     * @param exams
     */
    public void setExams(ObservableList<Exam> exams) {
        this.exams.set(exams);
    }

    /**
     * Method that return the courses where the subject is teached.
     *
     * @return courses
     */
    @XmlTransient
    public ObservableList<CourseSubject> getCourseSubjects() {
        return courseSubjects.get();
    }

    /**
     * Method that set the value of the courses where the subject is teached.
     *
     * @param courseSubjects
     */
    public void setCourseSubjects(ObservableList<CourseSubject> courseSubjects) {
        this.courseSubjects.set(courseSubjects);
    }

    /**
     * Integer representation for Subject instance.
     *
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
     *
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
     *
     * @return String
     */
    @Override
    public String toString() {
        return "Subject{" + "idSubject=" + idSubject + ", name=" + name + ", totalHours=" + totalHours + ", teacherCourseSubjects=" + teacherCourseSubjects + ", exams=" + exams + ", courseSubjects=" + courseSubjects + '}';
    }

}