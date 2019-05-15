package services.DAOS;

import data.Admin;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

public class AdminDAO extends DAO {

    @PersistenceContext(unitName = "myapp")
    private EntityManager em;

    @Transactional
    public void persist(Admin admin){
        em.persist(admin);
    }

    @Transactional
    public Admin find(String username){
        try {
            return em
                    .createQuery("select a from Admin a where a.username = :username", Admin.class)
                    .setParameter("username", username)
                    .getSingleResult();
        }
        catch (NoResultException e) {
            return null;
        }
    }

    public Admin findWithUsernameAndPassword(String username,String password){
        try {
            return em
                    .createQuery("select a from Admin a where a.username = :username and a.password = :password", Admin.class)
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .getSingleResult();
        }
        catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List listAll() {
        return null;
    }
}
