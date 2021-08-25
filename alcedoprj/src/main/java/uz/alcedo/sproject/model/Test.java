package uz.alcedo.sproject.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tests")
public class Test extends BaseEntity {
	
	private static final long serialVersionUID = 202107302233L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(length = 100)
	private String nomi;
	
	@Column(length = 50)
	private String fani;
	
	@Column(length = 100)
	private String razdel;
	
	@Column(length = 100)
	private String muallifi;
	
	@Column(nullable = false)
	private boolean activated = false;
	
	@ManyToMany
	@JoinTable(
	        name = "test_savol",
	        joinColumns = {@JoinColumn(name = "test_id", referencedColumnName = "id")},
	        inverseJoinColumns = {@JoinColumn(name = "savol_id", referencedColumnName = "id")})
	private List<Savol> savols = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomi() {
		return nomi;
	}

	public void setNomi(String nomi) {
		this.nomi = nomi;
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

	public String getMuallifi() {
		return muallifi;
	}

	public void setMuallifi(String muallifi) {
		this.muallifi = muallifi;
	}

	public List<Savol> getSavols() {
		return savols;
	}

	public void setSavols(List<Savol> savols) {
		this.savols = savols;
	}
	
	public boolean isActivated() {
		return activated;
	}

	public void setActivated(boolean activated) {
		this.activated = activated;
	}

}
