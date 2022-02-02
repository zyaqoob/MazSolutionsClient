/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import classes.Exam;
import classes.ExamSession;
import classes.Student;
import java.util.Objects;

/**
 *
 * @author z332h
 */
public class ExamSessionReportAuxClass {
    
    /**
     * Field that identify ExamSession.
     */
   
    
    private Long idExamSession;
    /**
     * Field that represent marks obtained.
     */
    private int mark;

    /**
     * An object of Exam.
     */
    
    private Exam exam;

    /**
     * An object of Student.
     */
   
    private Student student;

    /**
     * Data and time start of the examSession.
     */
    
    private String dateTimeStart;

    /**
     * Data and time end of the examSession.
     */
 
    private String dateTimeEnd;

    /**
     *
     * @return mark.
     */
    public int getMark() {
        return mark;
    }

    /**
     * Field that represent marks obtained
     *
     * @param mark the mark to set
     */
    public void setMark(int mark) {
        this.mark = mark;
    }

    /**
     *
     * @return exam
     */
    public Exam getExam() {
        return exam;
    }

    /**
     * An object of Exam.
     *
     * @param exam the exam to set
     */
    public void setExam(Exam exam) {
        this.exam = exam;
    }

    /**
     *
     * @return student
     */
    
    public Student getStudent() {
        return student;
    }

    /**
     * An object of student.
     *
     * @param student the student to set.
     */
    public void setStudent(Student student) {
        this.student = student;
    }

    /**
     *
     * @return dateTimeStart.
     */
    public String getDateTimeStart() {
        return dateTimeStart;
    }

    /**
     * Data and time start of the examSession.
     *
     * @param dateTimeStart the dateTimeStart to set
     */
    public void setDateTimeStart(String dateTimeStart) {
        this.dateTimeStart = dateTimeStart;
    }

    /**
     *
     * @return dateTimeEnd.
     */
    public String getDateTimeEnd() {
        return dateTimeEnd;
    }

    /**
     * Data and time end of the examSession.
     *
     * @param dateTimeEnd the dateTimeEnd to set
     */
    public void setDateTimeEnd(String dateTimeEnd) {
        this.dateTimeEnd = dateTimeEnd;
    }

    /**
     *
     * @return examSessionId
     */
    public Long getIdExamSession() {
        return idExamSession;
    }

    /**
     * an object of ExamSessionId.
     *
     * @param idExamSession the examSessionId to set.
     */
    public void setIdExamSession(Long idExamSession) {
        this.idExamSession = idExamSession;
    }

    

   
}
