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
public interface ExamSessionManager {
    
    public <T> T findExamSessionsBySubject(Class<T> responseType, String name) throws ClientErrorException ;
    
    public String countREST() throws ClientErrorException ;

    public void edit(Object requestEntity, String id) throws ClientErrorException ;

    public <T> T find(Class<T> responseType, String id) throws ClientErrorException ;

    public <T> T findRange(Class<T> responseType, String from, String to) throws ClientErrorException;

    public void create(Object requestEntity) throws ClientErrorException;

    public <T> T findExamSessionsByStudent(Class<T> responseType, String fullname) throws ClientErrorException;



    public void remove(String id) throws ClientErrorException ;


    public <T> T findAllExamSession(GenericType<T> responseType) throws ClientErrorException ;
    
}
