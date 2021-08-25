package uz.alcedo.sproject.service.dto;

import java.time.Instant;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import uz.alcedo.sproject.model.Role;
import uz.alcedo.sproject.model.User;

public class UserDTO {
	
	private Long id;
	private String login;
    private String ismi;
    private String familiya;
    private String otaIsmi;
    private String email; 
    private String telNum;
    private Date tugSana;
    private boolean activated;
    
	private String createdBy;
    private Instant createdDate;
    private String lastModifiedBy;
    private Instant lastModifiedDate;
    private Set<String> roles;
    
    public UserDTO() {
    }
       
	public UserDTO(User user) {
		this.id = user.getId();
		this.login = user.getLogin();
		this.ismi = user.getIsmi();
		this.familiya = user.getFamiliya();
		this.otaIsmi = user.getOtaIsmi();
		this.email = user.getEmail();
		this.telNum = user.getTelNum();
		this.tugSana = user.getTugSana();
		this.activated = user.isActivated();
		this.createdBy = user.getCreatedBy();
		this.createdDate = user.getCreatedDate();
		this.lastModifiedBy = user.getLastModifiedBy();
		this.lastModifiedDate = user.getLastModifiedDate();
		this.roles = user.getRoles().stream().map(Role::getName).collect(Collectors.toSet());
	}

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


	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Instant getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Instant createdDate) {
		this.createdDate = createdDate;
	}
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}
	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}
	public Instant getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(Instant lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	public Set<String> getRoles() {
		return roles;
	}
	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}
   
}
