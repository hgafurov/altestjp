package uz.alcedo.sproject.web.rest.vm;

import uz.alcedo.sproject.service.dto.UserDTO;

public class ManagedUserVM extends UserDTO {
	
	public static final int PASSWORD_MIN_LENGTH = 4;
    public static final int PASSWORD_MAX_LENGTH = 60;
    
    private String password;
    
    public ManagedUserVM() {
    }
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
