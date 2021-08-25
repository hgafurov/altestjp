package uz.alcedo.sproject.web.rest.vm;

import java.util.Date;
import java.util.Set;

public class UpdateUserVM {
	private Long id;
	private String login;
	private String password;
    private String ismi;
    private String familiya;
    private String otaIsmi;
    private String email; 
    private String telNum;
    private Date tugSana;
    private boolean activated;
    private Set<String> roles;
    
    public UpdateUserVM() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getIsmi() {
		return ismi;
	}

	public void setIsmi(String ismi) {
		this.ismi = ismi;
	}

	public String getFamiliya() {
		return familiya;
	}

	public void setFamiliya(String familiya) {
		this.familiya = familiya;
	}

	public String getOtaIsmi() {
		return otaIsmi;
	}

	public void setOtaIsmi(String otaIsmi) {
		this.otaIsmi = otaIsmi;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelNum() {
		return telNum;
	}

	public void setTelNum(String telNum) {
		this.telNum = telNum;
	}

	public Date getTugSana() {
		return tugSana;
	}

	public void setTugSana(Date tugSana) {
		this.tugSana = tugSana;
	}

	public boolean isActivated() {
		return activated;
	}

	public void setActivated(boolean activated) {
		this.activated = activated;
	}

	public Set<String> getRoles() {
		return roles;
	}

	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}

}
