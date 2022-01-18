/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.io.Serializable;
import java.util.Date;
import java.util.Calendar;
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
 * Entity representing generic user of the application. It contains the following fields:
 * userId, login, email, password, telephone, lastPasswordChange, status, birthDate, fullName.
 * @author Miguel Ángel Sánchez
 */


@XmlRootElement
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    public User(Long idUser, String login, String email,String password,String telephone, Calendar lastPasswordChange, 
            UserStatus status,UserPrivilege privilege,Date birthDate,
            String fullName,
            ObservableList<LastSignIn> lastSignIn) {
        this.idUser=new SimpleLongProperty(idUser);
        this.login=new SimpleStringProperty(login);
        this.email=new SimpleStringProperty(email);
        this.password=new SimpleStringProperty(password);
        this.telephone=new SimpleStringProperty(telephone);
        this.lastPasswordChange=new SimpleObjectProperty<>(lastPasswordChange);
        this.status=new SimpleObjectProperty<>(status);
        this.privilege=new SimpleObjectProperty<>(privilege);
        this.birthDate=new SimpleObjectProperty<>(birthDate);
        this.fullName=new SimpleStringProperty(fullName);
        this.lastSignIn=new SimpleListProperty<>(lastSignIn);
    }

    public User() {
        this.idUser=new SimpleLongProperty();
        this.login=new SimpleStringProperty();
        this.email=new SimpleStringProperty();
        this.password=new SimpleStringProperty();
        this.telephone=new SimpleStringProperty();
        this.lastPasswordChange=new SimpleObjectProperty<>();
        this.status=new SimpleObjectProperty<>();
        this.privilege=new SimpleObjectProperty<>();
        this.birthDate=new SimpleObjectProperty<>();
        this.fullName=new SimpleStringProperty();
        this.lastSignIn=new SimpleListProperty<>();
    }
    
    /**
     * Identification field for the user.
     */
    
    private SimpleLongProperty idUser;
    
    /**
     * Identification field for the user.
     */
    private SimpleStringProperty login;
    /**
     * Email field for the user.
     */
    private SimpleStringProperty email;
    /**
     * Password field for the user.
     */
    private SimpleStringProperty password;
    /**
     * Telephone field for the user.
     */
    private SimpleStringProperty telephone;
    /**
     * Last password change date field for the user.
     */
    private SimpleObjectProperty<Calendar> lastPasswordChange;
    /**
     * Status field for the user(ENABLED, DISALBED).
     */
    private SimpleObjectProperty<UserStatus> status;
    /**
     * Privileges field for the user(USER, ADMIN, STUDENT, TEACHER).
     */
    private SimpleObjectProperty<UserPrivilege> privilege;
    /**
     * Birth date for the user.
     */
    private SimpleObjectProperty<Date> birthDate;
    /**
     * Full name for the user.
     */
    private SimpleStringProperty fullName;

    private SimpleListProperty<LastSignIn> lastSignIn;

    @XmlTransient
    public ObservableList<LastSignIn> getLastSignIn() {
        return lastSignIn.get();
    }

    public void setLastSignIn(ObservableList<LastSignIn> lastSignIn) {
        this.lastSignIn.set(lastSignIn);
    }
    
    /**
     * 
     * @return Return the id.
     */
    public Long getIdUser() {
        return idUser.get();
    }
    /**
     * 
     * @param idUser Method to set the id
     */
    public void setUserId(Long idUser) {
        this.idUser.set(idUser);
    }
    /**
     * 
     * @return Return the login.
     */
    public String getLogin() {
        return login.get();
    }
    /**
     * 
     * @param login Method to set the login.
     */
    public void setLogin(String login) {
        this.login.set(login);
    }
    /**
     * 
     * @return Method wich returns the email.
     */
    public String getEmail() {
        return email.get();
    }
    /**
     * 
     * @param email Method to set the email.
     */
    public void setEmail(String email) {
        this.email.set(email);
    }
    /**
     * 
     * @return Method wich returns the password.
     */
    public String getPassword() {
        return password.get();
    }
    /**
     * 
     * @param password Method to set the password.
     */
    public void setPassword(String password) {
        this.password.set(password);
    }
    /**
     * 
     * @return Method wich returns the telephone.
     */
    public String getTelephone() {
        return telephone.get();
    }
    /**
     * 
     * @param telephone Method to set the telephone.
     */
    public void setTelephone(String telephone) {
        this.telephone.set(telephone);
    }
    /**
     * 
     * @return Method wich return the last password change date.
     */
    public Calendar getLastPasswordChange() {    
        return lastPasswordChange.get();
    }

    /**
     * 
     * @param lastPasswordChange Method to set the last password change date.
     */
    public void setLastPasswordChange(Calendar lastPasswordChange) {
        this.lastPasswordChange.set(lastPasswordChange);
    }
    /**
     * 
     * @return Method wich returns the user status. 
     */
    public UserStatus getStatus() {
        return status.get();
    }
    /**
     * 
     * @param status Method to set the user status.
     */
    public void setStatus(UserStatus status) {
        this.status.set(status);
    }
    /**
     * 
     * @return Method wich returns the bird date of the user.
     */
    public Date getBirthDate() {
        return birthDate.get();
    }
    /**
     * 
     * @param birthDate Method to set the users birth date.
     */
    public void setBirthDate(Date birthDate) {
        this.birthDate.set(birthDate);
    }
    /**
     * 
     * @return Method wich returns the users full name.
     */
    public String getFullName() {
        return fullName.get();
    }
    /**
     * 
     * @param fullName Method to set the users full name.
     */
    public void setFullName(String fullName) {
        this.fullName.set(fullName);
    }
    /**
     * 
     * @return Method wich returns the user privilege.
     */
    public UserPrivilege getPrivilege() {
        return privilege.get();
    }
    /**
     * 
     * @param privilege Method to set the user privilege.
     */
    public void setPrivilege(UserPrivilege privilege) {
        this.privilege.set(privilege);
    }
    
    
    
    
    /**
     * Method that returns an integer representation of user instance.
     * @return 
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.idUser);
        hash = 67 * hash + Objects.hashCode(this.login);
        hash = 67 * hash + Objects.hashCode(this.email);
        hash = 67 * hash + Objects.hashCode(this.password);
        hash = 67 * hash + Objects.hashCode(this.telephone);
        hash = 67 * hash + Objects.hashCode(this.lastPasswordChange);
        hash = 67 * hash + Objects.hashCode(this.status);
        hash = 67 * hash + Objects.hashCode(this.birthDate);
        hash = 67 * hash + Objects.hashCode(this.fullName);
        return hash;
    }

    
    /**
     * 
     * @param object Receives an object.
     * @return Returns true or false depending if that object is a User.
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.idUser == null && other.idUser != null) || (this.idUser != null && !this.idUser.equals(other.idUser))) {
            return false;
        }
        return true;
    }

    
    /**
     * This method returns a String with the diferent attributes of this class.
     * @return The string representing the User.
     */
    @Override
    public String toString() {
        return "User{" + "userId=" + idUser + ", login=" + login + ", email=" + email + ", password=" + password + ", telephone=" + telephone + ", lastPasswordChange=" + lastPasswordChange + ", status=" + status + ", birthDate=" + birthDate + ", fullName=" + fullName + '}';
    }
    
}
