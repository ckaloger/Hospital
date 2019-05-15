package services.AccessControl;

import javax.enterprise.inject.Alternative;
import javax.inject.Inject;

import com.vaadin.cdi.UIScoped;
import com.vaadin.cdi.access.AccessControl;
import data.Admin;
import data.Doctor;

@Alternative
public class CustomAccessControl extends AccessControl {

    private boolean isRefreshScreenTHread = false;

    @Inject
    private UserInfo userInfo;

    @Override
    public boolean isUserSignedIn() {
        return userInfo.getUser() != null;
    }

    @Override
    public boolean isUserInRole(String role) {
        if (isUserSignedIn()) {
            for (String userRole : userInfo.getRoles()) {
                if (role.equals(userRole)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getPrincipalName() {
        if (isUserSignedIn()) {
            return ((userInfo.getUser() instanceof  Doctor) ? ((Doctor)userInfo.getUser()).getLastName():
                    ((Admin)userInfo.getUser()).getUsername());
        }
        return null;
    }


}