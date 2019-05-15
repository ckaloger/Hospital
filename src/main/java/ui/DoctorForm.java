package ui;

import com.vaadin.cdi.CDIView;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import com.vaadin.ui.Component;
import data.Doctor;
import data.Patient;
import org.vaadin.viritin.MSize;
import org.vaadin.viritin.fields.MPasswordField;
import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.form.AbstractForm;
import org.vaadin.viritin.layouts.MHorizontalLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;
import services.ComponentSelection;
import services.DAOS.DoctorDAO;
import services.DAOS.PatientDAO;

import javax.enterprise.context.Dependent;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import java.util.Iterator;
import java.util.List;

//@CDIView("doctorForm")

@ComponentSelection(1)
public class DoctorForm extends AbstractForm<Doctor>  {

    @Inject @ComponentSelection(1)
    private DoctorDAO doctorDAO;

    @Inject @ComponentSelection(0)
    PatientDAO patientDAO;

    @PropertyId("username")
    private MTextField username = new MTextField("enter username");

    @PropertyId("password")
    private MPasswordField password = new MPasswordField("enter password");

    @PropertyId("firstName")
    private MTextField firstName = new MTextField("enter firstName");

    @PropertyId("lastName")
    private MTextField lastName = new MTextField("enter lastName");

    @PropertyId("phoneNumber")
    private MTextField phoneNumber = new MTextField("enter phone number");

    @PropertyId("specialty")
    private MTextField specialty = new MTextField("enter specialty");

    @PropertyId("info")
    private MTextField info = new MTextField("enter informations");

    private Button cancelButton = new Button("cancel");

    private Label label = new Label();

    @Override
    protected Component createContent() {
        setSavedHandler(e -> {
            doctorDAO.persist(getEntity());
            refresh();
        });

        setDeleteHandler(e -> {
            removePatients();
            doctorDAO.remove(getEntity());
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

        MVerticalLayout layout = new MVerticalLayout(buttonLayout,username,password,firstName,lastName,phoneNumber,specialty,info, getToolbar());
        layout.setMargin(new MarginInfo(false,true,true,true));

        return layout;
    }

    private void refresh(){
        setEntity(null);
        setVisible(false);
    }

    private void removePatients(){
        List<Patient> patientList = patientDAO.listAll(getEntity());
        Iterator<Patient> iterator = patientList.iterator();
        while(iterator.hasNext()) {
            Patient patient = iterator.next();
            patientDAO.remove(patient);
        }
    }


}