package uz.alcedo.sproject.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity()
@Table(name = "savols")
public class Savol extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 202118070008L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(length = 65536)
	private String savol;
	
	@Column(length = 32768)
	private String tj;
	
	@Column(length = 32768)
	private String j1;
	
	@Column(length = 32768)
	private String j2;
	
	@Column(length = 32768)
	private String j3;
	
	@Column(length = 32768)
	private String j4;
	
	@Column(length = 100)
	private String fani;
	
	@Column(length = 200)
	private String razdel;
	
	@Column(nullable = false)
	private boolean activated = false;
	
	@Column(length = 200)
	private String muallifi;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSavol() {
		return savol;
	}

	public void setSavol(String savol) {
		this.savol = savol;
	}

	public String getTj() {
		return tj;
	}

	public void setTj(String tj) {
		this.tj = tj;
	}

	public String getJ1() {
		return j1;
	}

	public void setJ1(String j1) {
		this.j1 = j1;
	}

	public String getJ2() {
		return j2;
	}

	public void setJ2(String j2) {
		this.j2 = j2;
	}

	public String getJ3() {
		return j3;
	}

	public void setJ3(String j3) {
		this.j3 = j3;
	}

	public String getJ4() {
		return j4;
	}

	public void setJ4(String j4) {
		this.j4 = j4;
	}

	public String getFani() {
		return fani;
	}

	public void setFani(String fani) {
		this.fani = fani;
	}

	public String getRazdel() {
		return razdel;
	}

	public void setRazdel(String razdel) {
		this.razdel = razdel;
	}

	public boolean isActivated() {
		return activated;
	}

	public void setActivated(boolean activated) {
		this.activated = activated;
	}

	public String getMuallifi() {
		return muallifi;
	}

	public void setMuallifi(String muallifi) {
		this.muallifi = muallifi;
	}

}
