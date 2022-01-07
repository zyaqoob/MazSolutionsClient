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
 * Entity representing Exam.
 *
 * @author Zeeshan Yaqoob
 */
@XmlRootElement
public class Exam implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * Field that identify ExamSession.
     */
    private Long idExam;

    /**
     * Field that represent statement of exam.
     */
    private String examStatement;

    /**
     * An object of student.
     */
    private Subject subject;

    /**
     * Collection of examSession.
     */
    private Set<ExamSession> sessions;

    /**
     *
     * @return examStatement
     */
    public String getExamStatement() {
        return examStatement;
    }

    /**
     * Field that represent statement of exam.
     *
     * @param examStatement the examStatement to set
     */
    public void setExamStatement(String examStatement) {
        this.examStatement = examStatement;
    }

    /**
     *
     * @return subject.
     */
    public Subject getSubject() {
        return subject;
    }

    /**
     * An object of subject.
     *
     * @param subject the subject to set.
     */
    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    /**
     * Method that return the sessions.
     *
     * @return sessions
     */
    @XmlTransient
    public Set<ExamSession> getSessions() {
        return sessions;
    }

    /**
     * Collection of examSession.
     *
     * @param sessions the collection of ExamSession to set.
     */
    public void setSessions(Set<ExamSession> sessions) {
        this.sessions = sessions;
    }

    /**
     *
     * @return idExam.
     */
    public Long getIdExam() {
        return idExam;
    }

    /**
     * Field that identify ExamSession.
     *
     * @param idExam the id of exam to set
     */
    public void setIdExam(Long idExam) {
        this.idExam = idExam;
    }

    /**
     * Integer representation of Exam instance
     *
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 19 * hash + Objects.hashCode(this.idExam);
        return hash;
    }

    /**
     * Compares two objects of Exam.
     *
     * @param object the other Exam object to compare.
     * @return true if they are same.
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
        final Exam other = (Exam) obj;
        if (!Objects.equals(this.idExam, other.idExam)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Exam{" + "idExam=" + idExam + ", examStatement=" + examStatement + ", subject=" + subject + ", sessions=" + sessions + '}';
    }

}
