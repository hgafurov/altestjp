package uz.alcedo.sproject.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "users")
public class User extends BaseEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(length = 50, unique = true, nullable = false)
    private String login;

    @JsonIgnore
    @Column(name = "password_hash", length = 60, nullable = false)
    private String password;
 
    @Column(name = "ismi", length = 50)
    private String ismi;

    @Column(name = "familiya", length = 50)
    private String familiya;

    @Column(name = "ota_ismi", length = 50)
    private String otaIsmi;

    @Column(name = "email", length = 254, unique = true)
    private String email;
    
    @Column(name = "tel_num", length = 100)
    private String telNum;

    @Column(name = "tug_sana")
    private Date tugSana;
    
    @Column(name = "activated", nullable = false)
    private boolean activated = false;    

    @ManyToMany
    @JoinTable(
        name = "users_roles",
        joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "role_name", referencedColumnName = "name")})
    private Set<Role> roles = new HashSet<>();
    
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

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
    
}
