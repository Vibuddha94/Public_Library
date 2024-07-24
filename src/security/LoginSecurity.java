package security;

//------------THIS IS TO KEEP A RECORD OF THE PERSON WHO LOGGED IN THROUGHOUT THE ENTIRE SYSTEM'S FUNCTIONS UNTIL THEY LOG OUT----------------

import java.util.ArrayList;

import dto.UserDto;
import service.ServiceFactory;
import service.ServiceFactory.ServiceType;
import service.custom.UserService;

public class LoginSecurity {
    private static LoginSecurity loginSecurity;
    private  String name;
    private  Boolean isAdmin;
    private  String password;

    UserService loginService;
    
    public LoginSecurity() {
        loginService = (UserService) ServiceFactory.getInstance().getService(ServiceType.USER);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static LoginSecurity getInstance() throws Exception{
        if (loginSecurity == null) {
            loginSecurity = new LoginSecurity();
        }
        return loginSecurity;
    }


    //------------CHECK THE VALIDY OF THE ENTERED USER ID----------------
    public boolean validateUserId(String id) throws Exception{
        ArrayList<UserDto> arrayList = loginService.getAll();
        for (UserDto userDto : arrayList) {
            if (id.equals(userDto.getUserId())) {
                setName(userDto.getFirstName() + " " + userDto.getLastName());
                setIsAdmin(userDto.getIsAdmin());
                setPassword(userDto.getPassword());
                return true;
            }
        }
        return false;
    }
    
}
