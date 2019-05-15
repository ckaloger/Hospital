package services.DAOS;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;

public abstract class DAO  {
    public void print() {
        System.out.println("inside dao");
    }

    abstract public List listAll();
}
