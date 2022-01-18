/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Objects;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author 2dam
 */
@XmlRootElement
public class LastSignIn implements Serializable {

    private static final long serialVersionUID = 1L;
    private SimpleLongProperty id;

    public LastSignIn(Long id,Calendar lastSignIn, User user) {
        this.id=new SimpleLongProperty(id);
        this.lastSignIn=new SimpleObjectProperty<>(lastSignIn);
        this.user=new SimpleObjectProperty<>(user);
    }

    public LastSignIn() {
        this.id=new SimpleLongProperty();
        this.lastSignIn=new SimpleObjectProperty<>();
        this.user=new SimpleObjectProperty<>();
    }
    
    
    
    private SimpleObjectProperty<Calendar> lastSignIn;
    
    private SimpleObjectProperty<User> user;
    
    
    public Calendar getLastSignIn() {
        return lastSignIn.get();
    }

    public void setLastSignIn(Calendar lastSignIn) {
        this.lastSignIn.set(lastSignIn);
    }
    
    @XmlTransient
    public User getUser() {
        return user.get();
    }

    public void setUser(User user) {
        this.user.set(user);
    }
    
    

    public Long getId() {
        return id.get();
    }

    public void setId(Long id) {
        this.id.set(id);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

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
        final LastSignIn other = (LastSignIn) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "LastSignIn{" + "id=" + id + ", lastSignIn=" + lastSignIn + ", user=" + user + '}';
    }

    
    
}
