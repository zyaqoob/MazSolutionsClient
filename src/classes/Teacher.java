/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Entity representing Teacher that extends from user.
 *
 * @author Zeeshan Yaqoob
 */
@XmlRootElement
public class Teacher extends User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 
     * @param salary
     * @param teacherCourses 
     */
    public Teacher(Float salary,TeacherCourse teacherCourses) {
        this.salary=new SimpleFloatProperty(salary);
        this.teacherCourse=new SimpleObjectProperty<>(teacherCourses);
    }

    public Teacher() {
        this.salary=new SimpleFloatProperty();
        this.teacherCourse=new SimpleObjectProperty<>();
    }
    
      /**
     * Field that represent salary of the teacher.
     * @param salary
     */
    private SimpleFloatProperty salary;

    /**
     * A collection of TeacherCourses.
     */
    private SimpleObjectProperty<TeacherCourse> teacherCourse;

    /**
     *
     * @return salary.
     */
    public Float getSalary() {    
        return salary.get();
    }

    /**
     * Field that represent salary of the teacher.
     *
     * @param salary the salary to set.
     */
    public void setSalary(Float salary) {    
        this.salary.set(salary);
    }

    /**
     *
     * @return teacherCourses.
     */
     public TeacherCourse getTeacherCourse() {
        return teacherCourse.get();
    }

    /**
     * A collection of TeacherCourse.
     *
     * @param teacherCourse the teacherCourses to set.
     */
    public void setTeacherCourse(TeacherCourse teacherCourse) {
        this.teacherCourse.set(teacherCourse);
    }
    /**
     * Integer representation of Teacher instance.
     *
     * @return
     */
    @Override   
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.salary);
        hash = 37 * hash + Objects.hashCode(this.teacherCourse);
        return hash;
    }

    /**
     * Compares two Teacher objects. This method consider a Teacher equal to
     * another Teacher if their id fields have the same value.
     *
     * @param obj the other Teacher object to compare.
     * @return true in case they are same.
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
        final Teacher other = (Teacher) obj;
        if (!Objects.equals(this.getIdUser(), other.getIdUser())) {
            return false;
        }
        return true;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "Teacher{" + "idTeacher=" + getIdUser() + ", salary=" + salary + ", teacherCourses=" + teacherCourse + '}';
    }

}
