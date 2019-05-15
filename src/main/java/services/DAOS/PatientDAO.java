package services.DAOS;

import com.vaadin.ui.UI;
import data.Doctor;
import data.Patient;
import services.ComponentSelection;

import javax.enterprise.context.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;


@ComponentSelection(0)
public class PatientDAO extends DAO implements Serializable {

    @PersistenceContext(unitName = "myapp")
    private EntityManager em;

    @Transactional
    public void persist(Patient patient){
        if (patient.getId() == null) {
            em.persist(patient);

        } else {
            em.merge(patient);
        }
    }

    @Transactional
    public void remove(Patient patient){
        em.remove(em.contains(patient) ? patient : em.merge(patient));
    }

    @Transactional
    public void update(Patient patient){
        em.merge(patient);
    }

    //@Transactional
    //public List<Patient> listAll() {
        //return em.createQuery("SELECT p FROM Patient p", Patient.class).getResultList();
    //}


    @Transactional
    public List<Patient> listAll() {
        String doctorUsername =(String) UI.getCurrent().getSession().getAttribute("currentDoctor");
        return em.createQuery("SELECT p FROM Patient p where p.doctor.username = :doctorUsername", Patient.class)
                .setParameter("doctorUsername", doctorUsername)
                .getResultList();
    }

    @Transactional
    public List<Patient> listAll(Doctor doctor) {
        String doctorUsername = doctor.getUsername();
        return em.createQuery("SELECT p FROM Patient p where p.doctor.username = :doctorUsername", Patient.class)
                .setParameter("doctorUsername", doctorUsername)
                .getResultList();
    }

    @Transactional
    public Patient findWithId(int id){
        try {
            return em
                    .createQuery("select p from Patient p where p.id = :id", Patient.class)
                    .setParameter("id", id)
                    .getSingleResult();
        }
        catch (NoResultException e) {
            return null;
        }
    }

    public Patient findWithUsernameAndPassword(String username,String password){
        try {
            return em
                    .createQuery("select p from Patient p where p.username = :username and p.password = :password", Patient.class)
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .getSingleResult();
        }
        catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public void print() {
        System.out.println("inside patient dao");
    }

}