package ui;

import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import data.Doctor;
import data.Visit;
import org.vaadin.viritin.MSize;
import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.form.AbstractForm;
import org.vaadin.viritin.layouts.MHorizontalLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;
import services.AccessControl.UserInfo;
import services.ComponentSelection;
import services.DAOS.DoctorDAO;
import services.DAOS.PatientDAO;
import services.DAOS.VisitDAO;

import javax.enterprise.context.Dependent;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;


@ComponentSelection(2)
public class VisitForm extends AbstractForm<Visit> {

    @Inject
    private UserInfo userInfo;

    @Inject @ComponentSelection(2)
    private VisitDAO visitDAO;

    @Inject @ComponentSelection(1)
    DoctorDAO doctorDAO;

    @Inject @ComponentSelection(0)
    PatientDAO patientDAO;

    @PropertyId("date")
    private MTextField date = new MTextField(" date");

    @PropertyId("diagnosis")
    private MTextField diagnosis = new MTextField("diagnosis");

    @PropertyId("info")
    private MTextField info = new MTextField(" informations");

    private Button cancelButton = new Button("cancel");

    private Label label = new Label();

    @Override
    protected Component createContent() {
        setSavedHandler(e -> {
            if (getEntity() == null)
                setEntity(new Visit());
            //getEntity().setDoctor(doctorDAO.findWithUsername((String) UI.getCurrent().getSession().getAttribute("currentDoctor")));
            getEntity().setPatient(patientDAO.findWithId((int) UI.getCurrent().getSession().getAttribute("currentPatient")));
            //getEntity().setDoctor(doctorDAO.findWithUsername("c"));
            //getEntity().setDoctor(new Doctor());
            System.out.println("lalakika");
            //getEntity().setId(12);
            System.out.println(getEntity().toString());
            Visit visit = getEntity();
            visitDAO.persist(visit);
            refresh();
        });

        setDeleteHandler(e -> {
            visitDAO.remove(getEntity());
            refresh();
        });

        cancelButton.addClickListener(e -> {
            setVisible(false);
            refresh();
        });


        MHorizontalLayout buttonLayout = new MHorizontalLayout()
                .withSize(MSize.FULL_SIZE)
                .add(label,0.8f)
                .add(cancelButton,.2f);

        MVerticalLayout layout = new MVerticalLayout(buttonLayout,date,diagnosis,info, getToolbar());
        layout.setMargin(new MarginInfo(false,true,true,true));

        return layout;
    }

    private void refresh(){
        setEntity(null);
        setVisible(false);
        //userInfo.setUser(new Doctor());
    }
}
