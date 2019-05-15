package ui;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Button;
import data.Admin;
import data.User;

import javax.annotation.PostConstruct;
import javax.inject.Inject;



import com.vaadin.ui.*;

import org.vaadin.viritin.form.AbstractForm;
import org.vaadin.viritin.layouts.MVerticalLayout;
import services.AccessControl.UserInfo;


@CDIView("login")
public class LoginView extends AbstractForm<User> implements View {

    @Inject
    private UserInfo userInfo;

    Button loginAsAdmin = new Button("login as admin");


    Button loginAsDoctor = new Button("login as Doctor");


    @PostConstruct
    private void onPostConstruct() {

        loginAsAdmin.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                UI.getCurrent().getNavigator().navigateTo("loginAsAdmin");
            }
        });

        loginAsDoctor.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                UI.getCurrent().getNavigator().navigateTo("loginAsDoctor");
            }
        });
    }

    @Override
    protected Component createContent() {
        return new MVerticalLayout(loginAsAdmin,loginAsDoctor);
    }
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }



}

