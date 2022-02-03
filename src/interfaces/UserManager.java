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
 * @author Zeeshan Yaqoob
 */
public interface UserManager {
    public String countREST() throws ClientErrorException ;

    public void edit_XML(Object requestEntity, String id) throws ClientErrorException;


    public <T> T find_XML(GenericType<T> responseType, String id) throws ClientErrorException;

    public <T> T find_JSON(Class<T> responseType, String id) throws ClientErrorException ;

    public <T> T findRange_XML(Class<T> responseType, String from, String to) throws ClientErrorException ;

    public <T> T findRange_JSON(Class<T> responseType, String from, String to) throws ClientErrorException;

    public void create_XML(Object requestEntity) throws ClientErrorException;

    public void create_JSON(Object requestEntity) throws ClientErrorException ;

    public <T> T findUserByEmail_XML(GenericType<T> responseType, String email) throws ClientErrorException;

    public <T> T login_XML(GenericType<T> responseType, String login, String password) throws ClientErrorException ;

    public <T> T findAll_XML(Class<T> responseType) throws ClientErrorException ;

    public void remove(String id) throws ClientErrorException ;

    public <T> T findUserByPassword_XML(GenericType<T> responseType, String login, String password, String newPassword) throws ClientErrorException ;
}
