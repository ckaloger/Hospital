package ui;

import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import com.vaadin.ui.Component;
import data.Patient;
import data.Visit;
import org.vaadin.viritin.MSize;
import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.form.AbstractForm;
import org.vaadin.viritin.layouts.MHorizontalLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;
import services.AccessControl.UserInfo;
import services.CallBack;
import services.ComponentSelection;
import services.DAOS.DoctorDAO;
import services.DAOS.PatientDAO;
import services.DAOS.VisitDAO;

import javax.inject.Inject;
import java.util.Iterator;
import java.util.List;

@ComponentSelection(0)
public class PatientForm extends AbstractForm<Patient>  {

    @Inject @ComponentSelection(0)
    private PatientDAO patientDAO;

    @Inject @ComponentSelection(1)
    DoctorDAO doctorDAO;

    @Inject @ComponentSelection(2)
    private VisitDAO visitDAO;

    @Inject
    private UserInfo userInfo;

    @PropertyId("firstName")
    private MTextField firstName = new MTextField("enter firstName");

    @PropertyId("lastName")
    private MTextField lastName = new MTextField("enter lastName");

    @PropertyId("phoneNumber")
    private MTextField phoneNumber = new MTextField("enter phone number");

    @PropertyId("AMKA")
    private MTextField amka = new MTextField("enter AMKA");

    @PropertyId("address")
    private MTextField address = new MTextField("enter address");

    @PropertyId("info")
    private MTextField info = new MTextField("enter informations");

    private Button cancelButton = new Button("cancel");

    private Button historyButton = new Button("view history");

    private Label label = new Label();

    private CallBack callback ;

    @Override
    protected Component createContent() {
        setSavedHandler(e -> {
            getEntity().setDoctor(doctorDAO.findWithUsername((String) UI.getCurrent().getSession().getAttribute("currentDoctor")));
            patientDAO.persist(getEntity());
            refresh();
        });

        setDeleteHandler(e -> {
            removeVisits();
            patientDAO.remove(getEntity());
            refresh();
        });

        cancelButton.addClickListener(e -> {
            setVisible(false);
            refresh();
        });

        historyButton.addClickListener(e -> {
            userInfo.setUser(new Visit());
            callback.updateScreen();
        });

        MHorizontalLayout buttonLayout = new MHorizontalLayout()
                .withSize(MSize.FULL_SIZE)
                .add(historyButton,0.8f)
                .add(cancelButton,.2f);

        MVerticalLayout layout = new MVerticalLayout(buttonLayout,firstName,lastName,phoneNumber,amka,address,info, getToolbar());
        layout.setMargin(new MarginInfo(false,true,true,true));

        return layout;
    }

    private void refresh(){
        setEntity(null);
        setVisible(false);
    }

    public void register(CallBack callBack){
        this.callback = callBack;
    }


    private void removeVisits(){
        List<Visit> visitList = visitDAO.listAll(getEntity());
        Iterator<Visit> iterator = visitList.iterator();
        while(iterator.hasNext()) {
            visitDAO.remove(iterator.next());
        }
    }
}

