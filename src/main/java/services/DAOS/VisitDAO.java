package services.DAOS;

import com.vaadin.ui.UI;
import data.Patient;
import data.Visit;
import services.ComponentSelection;

import javax.enterprise.context.Dependent;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;


@ComponentSelection(2)
public class VisitDAO extends DAO implements Serializable {

    @PersistenceContext(unitName = "myapp")
    private EntityManager em;

    @Transactional
    public void persist(Visit visit){
        if (visit.getId() == null) {
            em.persist(visit);

        } else {
            em.merge(visit);
        }
    }

    @Transactional
    public void remove(Visit visit){
        em.remove(em.contains(visit) ? visit : em.merge(visit));
    }

    @Transactional
    public void update(Visit visit){
        em.merge(visit);
    }

    @Transactional
    public List<Visit> listAll() {
        int patientId =(int) UI.getCurrent().getSession().getAttribute("currentPatient");
        return em.createQuery("SELECT v FROM Visit v where v.patient.id = :patientId", Visit.class)
                .setParameter("patientId", patientId)
                .getResultList();
    }

    @Transactional
    public List<Visit> listAll(Patient patient) {
        Integer patientId = Integer.valueOf(patient.getId());
        return em.createQuery("SELECT v FROM Visit v where v.patient.id = :patientId", Visit.class)
                .setParameter("patientId", patientId)
                .getResultList();
    }
}
