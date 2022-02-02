/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Objects;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Entity representing ExamSession.
 *
 * @author Aitor Ruiz de Gauna
 */
@XmlRootElement
public class ExamSession implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Field that identify ExamSession.
     * @param idExamSession
     * @param mark
     * @param exam
     * @param student
     * @param dateTimeStart
     * @param dateTimeEnd
     */
    public ExamSession(Long idExamSession,int mark, Exam exam, Student student, 
            Calendar dateTimeStart,Calendar dateTimeEnd) {
        this.idExamSession=new SimpleLongProperty(idExamSession);
        this.mark=new SimpleIntegerProperty(mark);
        this.exam=new SimpleObjectProperty<>(exam);
        this.student=new SimpleObjectProperty<>(student);
        this.dateTimeStart=new SimpleObjectProperty<>(dateTimeStart);
        this.dateTimeEnd=new SimpleObjectProperty<>(dateTimeEnd);
    }

    public ExamSession() {
        this.idExamSession=new SimpleLongProperty();
        this.mark=new SimpleIntegerProperty();
        this.exam=new SimpleObjectProperty<>();
        this.student=new SimpleObjectProperty<>();
        this.dateTimeStart=new SimpleObjectProperty<>();
        this.dateTimeEnd=new SimpleObjectProperty<>();
    }
    
    
    private SimpleLongProperty idExamSession;
    /**
     * Field that represent marks obtained.
     */
    private SimpleIntegerProperty mark;

    /**
     * An object of Exam.
     */
    private SimpleObjectProperty<Exam> exam;

    /**
     * An object of Student.
     */
    private SimpleObjectProperty<Student> student;

    /**
     * Data and time start of the examSession.
     */
    private SimpleObjectProperty<Calendar> dateTimeStart;

    /**
     * Data and time end of the examSession.
     */
    private SimpleObjectProperty<Calendar> dateTimeEnd;

    /**
     *
     * @return mark.
     */
    public int getMark() {
        return mark.get();
    }

    /**
     * Field that represent marks obtained
     *
     * @param mark the mark to set
     */
    public void setMark(int mark) {
        this.mark.set(mark);
    }

    /**
     *
     * @return exam
     */
    public Exam getExam() {
        return exam.get();
    }

    /**
     * An object of Exam.
     *
     * @param exam the exam to set
     */
    public void setExam(Exam exam) {
        this.exam.set(exam);
    }

    /**
     *
     * @return student
     */
    
    public Student getStudent() {
        return student.get();
    }

    /**
     * An object of student.
     *
     * @param student the student to set.
     */
    public void setStudent(Student student) {
        this.student.set(student);
    }

    /**
     *
     * @return dateTimeStart.
     */
    public Calendar getDateTimeStart() {
        return dateTimeStart.get();
    }

    /**
     * Data and time start of the examSession.
     *
     * @param dateTimeStart the dateTimeStart to set
     */
    public void setDateTimeStart(Calendar dateTimeStart) {
        this.dateTimeStart.set(dateTimeStart);
    }

    /**
     *
     * @return dateTimeEnd.
     */
    public Calendar getDateTimeEnd() {
        return dateTimeEnd.get();
    }

    /**
     * Data and time end of the examSession.
     *
     * @param dateTimeEnd the dateTimeEnd to set
     */
    public void setDateTimeEnd(Calendar dateTimeEnd) {
        this.dateTimeEnd.set(dateTimeEnd);
    }

    /**
     *
     * @return examSessionId
     */
    public Long getIdExamSession() {
        return idExamSession.get();
    }

    /**
     * an object of ExamSessionId.
     *
     * @param idExamSession the examSessionId to set.
     */
    public void setIdExamSession(Long idExamSession) {
        this.idExamSession.set(idExamSession);
    }

    /**
     * Interger representation of examSession instance.
     *
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.idExamSession);
        hash = 79 * hash + Objects.hashCode(this.exam);
        hash = 79 * hash + Objects.hashCode(this.student);
        hash = 79 * hash + Objects.hashCode(this.dateTimeStart);
        hash = 79 * hash + Objects.hashCode(this.dateTimeEnd);
        return hash;
    }

    /**
     * compares two objects of ExamSessions.
     *
     * @param obj the other object of ExamSession to compare.
     * @return true incase they are same.
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
        final ExamSession other = (ExamSession) obj;
        if (!Objects.equals(this.idExamSession, other.idExamSession)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ExamSession{" + "examSessionId=" + idExamSession + ", mark=" + mark + ", exam=" + exam + ", student=" + student + ", dateTimeStart=" + dateTimeStart + ", dateTimeEnd=" + dateTimeEnd + '}';
    }

}