package services.AccessControl;

import com.vaadin.cdi.UIScoped;
import data.Admin;
import data.Doctor;
import data.User;
import data.Visit;

import javax.enterprise.context.Dependent;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

//@SessionScoped
@SessionScoped
public class UserInfo implements Serializable {

    User user;

    private List<String> roles = new LinkedList<String>();

    public  void setUser(User user) {
        this.user = user;
        roles.clear();
        if (user instanceof Doctor) {
            roles.add("doctor");
        }
        else if (user instanceof Admin) {
            roles.add("admin");
        }
        else  {
            roles.add("visit");
        }
//        else
//            System.out.println("to poulo");
    }

    public User getUser(){
        return user;
    }

    public List<String> getRoles() {
        return roles;
    }
}
