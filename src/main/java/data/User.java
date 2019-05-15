package data;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.enterprise.context.Dependent;
import javax.enterprise.context.SessionScoped;
import javax.persistence.Entity;
import java.io.Serializable;

//@Entity
@EqualsAndHashCode
@ToString
@SessionScoped
public class User implements Serializable {

    private boolean userIsAdmin;

    public boolean isAdmin(){
        return userIsAdmin;
    }

    public void setUserAsAdmin( boolean value){
        userIsAdmin = value;
    }

}
