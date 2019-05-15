package ui;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Push;
import com.vaadin.cdi.CDIView;
import com.vaadin.cdi.ViewScoped;
import com.vaadin.cdi.access.AccessControl;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.navigator.View;
import data.*;
import org.vaadin.viritin.MSize;
import org.vaadin.viritin.form.AbstractForm;
import org.vaadin.viritin.layouts.MHorizontalLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;
import services.AccessControl.UserInfo;
import services.CallBack;
import services.ComponentSelectionAnnotationLiteral;
import services.DAOS.DAO;
import services.DAOS.VisitDAO;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;


@CDIView("main")
public class MainComponent extends MVerticalLayout implements View , CallBack {

    @Inject
    private UserInfo userInfo;

    private Grid grid = new Grid();
    private BeanItemContainer container ;
    @Inject @Any
    transient private Instance<DAO> daos;
    @Inject @Any
    private Instance<AbstractForm<? extends User>> forms;
    @Inject @Any
    private Instance<User> users;
    private AbstractForm form;
    @Inject
    transient private  AccessControl accessControl;
    private Button addNewEntityButton = new  Button("new");
    private Button logOut = new Button("log out");
    private Button back = new Button("back");
    private MHorizontalLayout buttonsLayout = new MHorizontalLayout().withSize(MSize.FULL_SIZE);
    private MHorizontalLayout gridAndFormLayout = new MHorizontalLayout().withSize(MSize.FULL_SIZE);


    @PostConstruct
    private void onPostConstruct() {
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        System.out.println("inside enter");
        initComponent();

    }

    private void initGrid(){
        grid = new Grid();
        grid.setSizeFull();

        if (accessControl.isUserInRole("admin") == true){
            container = new BeanItemContainer(Doctor.class);
            grid.setContainerDataSource(container);
            grid.setColumns("firstName", "lastName", "phoneNumber","specialty","info");
        }
        else if (accessControl.isUserInRole("doctor") == true) {
            container = new BeanItemContainer(Patient.class);
            grid.setContainerDataSource(container);
            grid.setColumns("firstName", "lastName", "phoneNumber","AMKA","address","info");
            if (!(getDAO().listAll().isEmpty()))
            initFiltering();

        }
        else {
            container = new BeanItemContainer(Visit.class);
            grid.setContainerDataSource(container);
            grid.setColumns("date", "diagnosis", "info","username");
        }


        grid.addItemClickListener(e -> {
            Visit visit;
            if (e.isDoubleClick()){
                getForm().setVisible(true);
                if (e.getItemId() instanceof Patient)
                    UI.getCurrent().getSession().setAttribute("currentPatient", ((Patient) e.getItemId()).getId());

                getForm().setEntity(e.getItemId());

            }
        });
    }

    private void initForm(){
        getForm().setSizeFull();
        getForm().getSaveButton().addClickListener(e -> updateList());
        getForm().getDeleteButton().addClickListener(e -> updateList());
        if (getForm() instanceof  PatientForm)
            ((PatientForm) getForm()).register(this);
        getForm().setEntity(null);
        getForm().setVisible(false);
    }

    //11111111111111111111111111
    private void initButtons(){
        addNewEntityButton.addClickListener(e -> {
                getForm().setEntity(getUser());
                getForm().setVisible(true);
        });

        logOut.addClickListener(e -> {
            UI.getCurrent().getNavigator().navigateTo("login");
        });

        if (accessControl.isUserInRole("visit") == true){
            back.addClickListener(e -> {
                userInfo.setUser(new Doctor());
                updateScreen();
            });
            buttonsLayout
                    .add(addNewEntityButton, .8f)
                    .add(back,0.1f)
                    .add(logOut, .1f);
        }
        else {
            buttonsLayout
                    .add(addNewEntityButton, .9f)
                    .add(logOut, .1f);
        }

    }

    private void updateList() {
        container.removeAllItems();
        container.addAll(getDAO().listAll());
    }

    private AbstractForm getForm(){
        return  (form == null) ? (form = forms.select(ComponentSelectionAnnotationLiteral.network(accessControl.isUserInRole("admin") == true ? 1 :
                accessControl.isUserInRole("doctor") == true ? 0 : 2)).get()) : form;
    }

    private DAO getDAO(){
        return daos.select(ComponentSelectionAnnotationLiteral.network(accessControl.isUserInRole("admin") == true ? 1 :
                accessControl.isUserInRole("doctor") == true ? 0 : 2)).get();
    }

    private User getUser(){

        if (accessControl.isUserInRole("admin") == true)
            return new Doctor();
        else if (accessControl.isUserInRole("doctor") == true)
            return new Patient();
        else
            return new Visit();
    }

    private void initComponent(){

        removeAllComponentsFromCurrent();
        setSizeFull();
        initGrid();
        initForm();
        initButtons();

        gridAndFormLayout
                .add(grid, .6f)
                .add(getForm(), .4f);

        add(buttonsLayout,0.1f)
                .add(gridAndFormLayout,0.9f);

        setHeightUndefined();
        updateList();

    }

    @Override
    public void updateScreen() {
        resetForm();
        initComponent();
    }

    private void removeAllComponentsFromCurrent(){
        gridAndFormLayout.removeAllComponents();
        buttonsLayout.removeAllComponents();
        this.removeAllComponents();
    }

    private void initFiltering(){

        Grid.HeaderRow filterRow = grid.appendHeaderRow();

        // Set up a filter for all columns
        for (Object pid: grid.getContainerDataSource()
                .getContainerPropertyIds())  {
            if ((pid.toString().equals("admin")) || (pid.toString().equals( "id")) || (pid.toString().equals("firstName"))
                    || (pid.toString().equals( "info")) || (pid.toString().equals( "address"))|| (pid.toString().equals( "doctor")))
                continue;
            System.out.println(pid.toString());
            Grid.HeaderCell cell = filterRow.getCell(pid);

            // Have an input field to use for filter
            TextField filterField = new TextField();
            filterField.setColumns(3);

            // Update filter When the filter input is changed
            filterField.addTextChangeListener(change -> {
                // Can't modify filters so need to replace
                container.removeContainerFilters(pid);

                // (Re)create the filter if necessary
                if (! change.getText().isEmpty())
                    container.addContainerFilter(
                            new SimpleStringFilter(pid,
                                    change.getText(), true, false));
            });
            cell.setComponent(filterField);
        }
    }

    private void resetForm(){
        form = null;
    }
}
