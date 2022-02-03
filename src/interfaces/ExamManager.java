/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author z332h
 */
public interface ExamManager {
    public <T> T findExamsByStudent(Class<T> responseType, String name) throws ClientErrorException ;

    public String countREST() throws ClientErrorException ;

    public void edit(Object requestEntity, String id) throws ClientErrorException ;
    public <T> T find(Class<T> responseType, String id) throws ClientErrorException;

    public <T> T findRange(Class<T> responseType, String from, String to) throws ClientErrorException ;

    public <T> T findAllExam(GenericType<T> responseType) throws ClientErrorException ;

    public void create(Object requestEntity) throws ClientErrorException ;


    public void remove(String id) throws ClientErrorException ;

    public <T> T findExamsBySubject(Class<T> responseType, String name) throws ClientErrorException ;

    public <T> T findExamByExamSession(Class<T> responseType, String id) throws ClientErrorException ;
}
