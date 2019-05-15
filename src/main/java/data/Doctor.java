package data;

import javax.enterprise.context.Dependent;
import javax.enterprise.context.SessionScoped;
import javax.persistence.*;
import java.io.Serializable;

import com.vaadin.cdi.UIScoped;
import com.vaadin.cdi.ViewScoped;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import services.ComponentSelection;

@Entity
@EqualsAndHashCode
@ToString
@SessionScoped
//@Dependent
@ComponentSelection(1)
public class Doctor extends User implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Integer id;

    @Getter @Setter
    private String username;

    @Getter @Setter
    private String password;

    @Getter @Setter
    private String firstName;

    @Getter @Setter
    private String lastName;

    @Getter @Setter
    private Integer phoneNumber;

    @Getter @Setter
    private String specialty;

    @Getter @Setter
    private String info;

}
