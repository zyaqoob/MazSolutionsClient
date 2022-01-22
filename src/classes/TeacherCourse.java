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
 *
 * @author Aitor Ruiz de Gauna
 */
/**
 * Entity that has the info of the courses of the Teacher.
 */
@XmlRootElement
public class TeacherCourse implements Serializable {

    public TeacherCourse(Long idTeacherCourse, Date dateStart, Date dateEnd, ObservableList<TeacherCourseSubject> teacherCourseSubjects,
            Teacher teacher, String name) {
        this.idTeacherCourse = new SimpleLongProperty(idTeacherCourse);
        this.dateStart = new SimpleObjectProperty<>(dateStart);
        this.dateEnd = new SimpleObjectProperty<>(dateEnd);
        this.teacherCourseSubjects = new SimpleListProperty<>(teacherCourseSubjects);
        this.teacher = new SimpleObjectProperty<>(teacher);
        this.name = new SimpleStringProperty(name);
    }

    public TeacherCourse() {
        this.idTeacherCourse = new SimpleLongProperty();
        this.dateStart = new SimpleObjectProperty<>();
        this.dateEnd = new SimpleObjectProperty<>();
        this.teacherCourseSubjects = new SimpleListProperty<>();
        this.teacher = new SimpleObjectProperty<>();
        this.name = new SimpleStringProperty();
    }

    private static final long serialVersionUID = 1L;
    private SimpleLongProperty idTeacherCourse;
    // Date when the TeacherCourse starts.
    private SimpleObjectProperty<Date> dateStart;
    // Date when the TeacherCourse ends.
    private SimpleObjectProperty<Date> dateEnd;
    //Collection of the subject that the teacher has.
    private SimpleListProperty<TeacherCourseSubject> teacherCourseSubjects;
    //Teacher of the TeacherCourse
    private SimpleObjectProperty<Teacher> teacher;

    private SimpleStringProperty name;

    /**
     * Method that returns the class that contains the id's of TeacherCourse.
     *
     * @return idTeacherCourseId;
     */
    public Long getIdTeacherCourse() {
        return idTeacherCourse.get();
    }

    /**
     * Method that set the value of the object of the class TeacherCourseId that
     * contains the id's of TeacherCourse.
     *
     * @param idTeacherCourse
     */
    public void setIdTeacherCourse(Long idTeacherCourse) {
        this.idTeacherCourse.set(idTeacherCourse);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    /**
     * Method that returns the date start of the TeacherCourse.
     *
     * @return dateStart
     */
    public Date getDateStart() {
        return dateStart.get();
    }

    /**
     * Method that set the value of the dateStart of the TeacherCourse.
     *
     * @param dateStart
     */
    public void setDateStart(Date dateStart) {
        this.dateStart.set(dateStart);
    }

    /**
     * Method that return the value of the dateEnd of TeacherCourse.
     *
     * @return
     */
    public Date getDateEnd() {
        return dateEnd.get();
    }

    /**
     * Method that set the value of the dateEnd of TeacherCourse.
     *
     * @param dateEnd
     */
    public void setDateEnd(Date dateEnd) {
        this.dateEnd.set(dateEnd);
    }

    /**
     * Method that return the value of the collection of subjects of
     * TeacherCourse.
     *
     * @return
     */
    @XmlTransient
    public ObservableList<TeacherCourseSubject> getTeacherCourseSubjects() {
        return teacherCourseSubjects.get();
    }

    /**
     * Method that set the value of the collection of subjects of TeacherCourse.
     *
     * @param teacherCourseSubjects
     */
    public void setTeacherCourseSubjects(ObservableList<TeacherCourseSubject> teacherCourseSubjects) {
        this.teacherCourseSubjects.set(teacherCourseSubjects);
    }

    /**
     * Method that return the value of the collection of teachers of
     * TeacherCourse.
     *
     * @return teacher
     */
    public Teacher getTeacher() {
        return teacher.get();
    }

    /**
     * Method that set the value of the collection of subjects of TeacherCourse.
     *
     * @param teacher
     */
    public void setTeacher(Teacher teacher) {
        this.teacher.set(teacher);
    }

    /**
     * Integer representation for TeacherCourse instance.
     *
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
     *
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
     *
     * @return String
     */
    @Override
    public String toString() {
        return "TeacherCourse{" + "idTeacherCourseId=" + idTeacherCourse + ", dateStart=" + dateStart + ", dateEnd=" + dateEnd + ", subjects=" + teacherCourseSubjects + ", teacher=" + teacher + '}';
    }
}