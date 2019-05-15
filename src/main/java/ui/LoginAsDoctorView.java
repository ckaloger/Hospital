package ui;

import com.vaadin.cdi.CDIView;
import com.vaadin.cdi.access.AccessControl;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import data.Doctor;
import org.vaadin.viritin.fields.MPasswordField;
import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.form.AbstractForm;
import org.vaadin.viritin.layouts.MVerticalLayout;
import services.ComponentSelection;
import services.DAOS.DoctorDAO;
import services.AccessControl.UserInfo;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;


@CDIView("loginAsDoctor")
public class LoginAsDoctorView extends AbstractForm<Doctor> implements View {

    @Inject
    private UserInfo userInfo;

    @Inject @ComponentSelection(1)
    DoctorDAO doctorDAO;

    @PropertyId("username")
    private MTextField username = new MTextField("enter username").withAutocompleteOff();

    @PropertyId("password")
    private MPasswordField password = new MPasswordField("enter password");

    Button b =new Button("log in");

    @PostConstruct
    private void OnPostConstruct(){

        setEntity(new Doctor());

        b.addClickListener(e -> {
            Doctor doctor;
            if ((doctor = doctorDAO.findWithUsernameAndPassword(getEntity().getUsername(),getEntity().getPassword())) != null) {
                UI.getCurrent().getSession().setAttribute("currentDoctor",username.getValue());
                userInfo.setUser(doctor);
                UI.getCurrent().getNavigator().navigateTo("main");
            }
            else {
                setEntity(new Doctor());
            }

        });
    }

    @Override
    protected Component createContent() {
        return new MVerticalLayout( username, password,b, getToolbar());
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        setEntity(new Doctor());
    }
}