package services.DAOS;

import data.Doctor;
import services.ComponentSelection;

import javax.enterprise.context.Dependent;
import javax.enterprise.context.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;


@ComponentSelection(1)
public class DoctorDAO extends DAO implements Serializable {

    @PersistenceContext(unitName = "myapp")
    private EntityManager em;

    @Transactional
    public void persist(Doctor doctor){
        if (doctor.getId() == null) {
            em.persist(doctor);

        } else {
            em.merge(doctor);
        }
    }

    @Transactional
    public void remove(Doctor doctor){
        em.remove(em.contains(doctor) ? doctor : em.merge(doctor));
    }

    @Transactional
    public void update(Doctor doctor){
        em.merge(doctor);
    }

    @Transactional
    public List<Doctor> listAll() {
        return em.createQuery("SELECT d FROM Doctor d", Doctor.class).getResultList();
    }

    @Transactional
    public Doctor find(String username){
        try {
            return em
                    .createQuery("select d from Doctor d where d.username = :username", Doctor.class)
                    .setParameter("username", username)
                    .getSingleResult();
        }
        catch (NoResultException e) {
            return null;
        }
    }

    @Transactional
    public Doctor findWithUsernameAndPassword(String username,String password){
        try {
            return em
                    .createQuery("select d from Doctor d where d.username = :username and d.password = :password", Doctor.class)
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .getSingleResult();
        }
        catch (NoResultException e) {
            return null;
        }
    }

    @Transactional
    public Doctor findWithUsername(String username){
        try {
            return em
                    .createQuery("select d from Doctor d where d.username = :username ", Doctor.class)
                    .setParameter("username", username)
                    .getSingleResult();
        }
        catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public void print() {
        System.out.println("inside doctor dao");
    }

}