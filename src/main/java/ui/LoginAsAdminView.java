package ui;

import com.vaadin.cdi.CDIView;
import com.vaadin.cdi.access.AccessControl;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.UI;
import data.Admin;
import org.vaadin.viritin.fields.MPasswordField;
import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.form.AbstractForm;
import org.vaadin.viritin.layouts.MVerticalLayout;
import services.AccessControl.UserInfo;
import services.DAOS.AdminDAO;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

@CDIView("loginAsAdmin")
public class LoginAsAdminView extends AbstractForm<Admin> implements View {

    @Inject
    private AccessControl accessControl;

    @Inject
    private UserInfo userInfo;

    @Inject
    AdminDAO adminDAO;

    @PropertyId("username")
    private MTextField username = new MTextField("enter username").withAutocompleteOff();

    @PropertyId("password")
    private MPasswordField password = new MPasswordField("enter password");

    Button b =new Button("log in");

    @PostConstruct
    private void OnPostConstruct(){

        System.out.println("onPostConstruct");

        setEntity(new Admin());

        b.addClickListener(e -> {
            Admin admin;
            if ((admin = adminDAO.findWithUsernameAndPassword(getEntity().getUsername(),getEntity().getPassword())) != null) {
                userInfo.setUser(getEntity());
                UI.getCurrent().getNavigator().navigateTo("main");
            }
            else {
                setEntity(new Admin());
            }

        });
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        setEntity(new Admin());
    }

    @Override
    protected Component createContent() {
        return new MVerticalLayout( username, password,b, getToolbar());
    }
}
