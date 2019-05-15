package data;

import javax.enterprise.context.Dependent;
import javax.enterprise.context.SessionScoped;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
@ComponentSelection(2)
public class Visit extends User implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Integer id;

    @Getter @Setter
    private String date;

    @Getter @Setter
    private String diagnosis;

    @Getter @Setter
    private String info;

    @ManyToOne
    @JoinColumn(name = "patientId")
    @Getter @Setter
    private Patient patient;

    public String getUsername(){
        return patient.getDoctor().getUsername();
    }

    public String getPatientName(){
        return patient.getLastName();
    }

}
