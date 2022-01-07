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
 * Entity representing Teacher that extends from user.
 *
 * @author Zeeshan Yaqoob
 */
@XmlRootElement
public class Teacher extends User implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * Field that represent salary of the teacher.
     */
    private float salary;

    /**
     * A collection of TeacherCourses.
     */
    private Set<TeacherCourse> teacherCourses;

    /**
     *
     * @return salary.
     */
    public float getSalary() {
        return salary;
    }

    /**
     * Field that represent salary of the teacher.
     *
     * @param salary the salary to set.
     */
    public void setSalary(float salary) {
        this.salary = salary;
    }

    /**
     *
     * @return teacherCourses.
     */
    @XmlTransient
    public Set<TeacherCourse> getTeacherCourses() {
        return teacherCourses;
    }

    /**
     * A collection of TeacherCourse.
     *
     * @param teacherCourses the teacherCourses to set.
     */
    public void setTeacherCourses(Set<TeacherCourse> teacherCourses) {
        this.teacherCourses = teacherCourses;
    }

    /**
     * Integer representation of Teacher instance.
     *
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.getIdUser());
        hash = 53 * hash + Float.floatToIntBits(this.salary);
        hash = 53 * hash + Objects.hashCode(this.teacherCourses);
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
        return "Teacher{" + "idTeacher=" + getIdUser() + ", salary=" + salary + ", teacherCourses=" + teacherCourses + '}';
    }

}
