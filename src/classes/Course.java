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
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Entity representing Course of the application. It contains the following fields:
 * name, dateStart, dateEnd, subjects, students.
 * @author Miguel Angel Sanchez
 */

@XmlRootElement
public class Course implements Serializable {
    
    public Course(Long idCourse, String name, Date dateStart, Date dateEnd, ObservableList<CourseSubject> courseSubjects,
            ObservableList<Student> students) {
        this.idCourse=new SimpleLongProperty(idCourse);
        this.name=new SimpleStringProperty(name);
        this.dateStart=new SimpleObjectProperty<>(dateStart);
        this.dateEnd=new SimpleObjectProperty<>(dateEnd);
        this.courseSubjects=new SimpleListProperty<>(courseSubjects);
        this.students=new SimpleListProperty<>(students);
    }

    public Course() {
         this.idCourse=new SimpleLongProperty();
        this.name=new SimpleStringProperty();
        this.dateStart=new SimpleObjectProperty<>();
        this.dateEnd=new SimpleObjectProperty<>();
        this.courseSubjects=new SimpleListProperty<>();
        this.students=new SimpleListProperty<>();
    }
    
    private static final long serialVersionUID = 1L;
    private SimpleLongProperty idCourse;
    /**
     * Name field for the course.
     */
    private SimpleStringProperty name;
    /**
     * The date of the course start.
     */
    private SimpleObjectProperty<Date> dateStart;
    /**
     * The date of the course end.
     */
    private SimpleObjectProperty<Date> dateEnd;
    /**
     * The subjects of the course.
     */
    private SimpleListProperty<CourseSubject> courseSubjects;
    /**
     * The students of the course.
     */
    private SimpleListProperty<Student> students;
    /**
     * This method returns the course id.
     * @return 
     */
    public Long getIdCourse() {
        return idCourse.get();
    }
    /**
     * This method set the course id.
     * @param idCourse 
     */
    public void setIdCourse(Long idCourse) {
        this.idCourse.set(idCourse);
    }
    /**
     * This method returns the course name.
     * @return 
     */
    public String getName() {
        return name.get();
    }
    /**
     * This method set the course name.
     * @param name 
     */
    public void setName(String name) {
        this.name.set(name);
    }
    /**
     * This method return the date start of the course.
     * @return 
     */
    public Date getDateStart() {
        return dateStart.get();
    }
    /**
     * This method set the date start of the course.
     * @param dateStart 
     */
    public void setDateStart(Date dateStart) {
        this.dateStart.set(dateStart);
    }
    /**
     * This method returns the date end of the course.
     * @return 
     */
    public Date getDateEnd() {
        return dateEnd.get();
    }
    /**
     * This method set the date end of the course.
     * @param dateEnd 
     */
    public void setDateEnd(Date dateEnd) {
        this.dateEnd.set(dateEnd);
    }
    /**
     * This method return a set of the subjects from the course.
     * @return 
     */
    @XmlTransient
    public ObservableList<CourseSubject> getCourseSubjects() {
        return courseSubjects.get();
    }
    /**
     * This method set a set of subjects in the course.
     * @param courseSubjects
     */
    public void setCourseSubject(ObservableList<CourseSubject> courseSubjects) {    
        this.courseSubjects.set(courseSubjects);
    }
    /**
     * This method get a set of students from the course.
     * @return 
     */
    @XmlTransient
    public ObservableList<Student> getStudents() {
        return students.get();
    }
    /**
     * This method set a set of students in the course.
     * @param students 
     */
    public void setStudents(ObservableList<Student> students) {
        this.students.set(students);
    }
    /**
     * Integer representation for Course instance.
     * @return 
     */
    @Override    
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + Objects.hashCode(this.idCourse);
        hash = 83 * hash + Objects.hashCode(this.name);
        hash = 83 * hash + Objects.hashCode(this.dateStart);
        hash = 83 * hash + Objects.hashCode(this.dateEnd);
        hash = 83 * hash + Objects.hashCode(this.courseSubjects);
        hash = 83 * hash + Objects.hashCode(this.students);
        return hash;
    }

    /**
     * Compares two Courses objects for equality. This method consider a Course
     * equal to another Course if their id fields have the same value. 
     * @param obj The other Course to compare to
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
        final Course other = (Course) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.idCourse, other.idCourse)) {
            return false;
        }
        if (!Objects.equals(this.dateStart, other.dateStart)) {
            return false;
        }
        if (!Objects.equals(this.dateEnd, other.dateEnd)) {
            return false;
        }
        if (!Objects.equals(this.courseSubjects, other.courseSubjects)) {
            return false;
        }
        if (!Objects.equals(this.students, other.students)) {
            return false;
        }
        return true;
    }
    /**
     * Obtains a string representation of the Course.
     * @return The String representing the Course.
     */
    @Override
    public String toString() {
        return "Course{" + "id=" + idCourse + ", name=" + name + ", dateStart=" + dateStart + ", dateEnd=" + dateEnd + ", subjects=" + courseSubjects + ", students=" + students + '}';
    }

}