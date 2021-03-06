/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author Miguel
 */
public interface CourseManager {
    
    public String countREST() throws ClientErrorException;
    public void edit(Object requestEntity, String id) throws ClientErrorException;
    public <T> T findCourseByStudent(Class<T> responseType, String id) throws ClientErrorException;
    public <T> T findRange(Class<T> responseType, String from, String to) throws ClientErrorException;
    public <T> T find(GenericType<T> responseType, String id) throws ClientErrorException;
    public void create(Object requestEntity) throws ClientErrorException;
    public <T> T findAllCourses(GenericType<T> responseType) throws ClientErrorException;
    public void remove(String id) throws ClientErrorException;
    public <T> T findCoursesBySubject(Class<T> responseType, String id) throws ClientErrorException;
    public <T> T findCourseByName(GenericType<T> responseType, String name) throws ClientErrorException;
    public void close();
    
}
