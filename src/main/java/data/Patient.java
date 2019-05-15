package data;

import javax.enterprise.context.Dependent;
import javax.enterprise.context.SessionScoped;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import services.ComponentSelection;

@Entity
@EqualsAndHashCode
@ToString
@SessionScoped
@ComponentSelection(0)
public class Patient extends User implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Integer id;

    @Getter @Setter
    private String firstName;

    @Getter @Setter
    private String lastName;

    @Getter @Setter
    private int phoneNumber;

    @Getter @Setter
    private int AMKA;

    @Getter @Setter
    private String address;

    @Getter @Setter
    private String info;

    @ManyToOne
    @JoinColumn(name = "doctorId")
    @Getter @Setter
    private Doctor doctor;

}
