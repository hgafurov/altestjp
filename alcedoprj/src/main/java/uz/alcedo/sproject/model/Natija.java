package uz.alcedo.sproject.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "natijalar")
public class Natija extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private User user;
	
	@ManyToOne
	private Test test;
	
	private int savolCount = 0;
	
	private int tjCount = 0;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Test getTest() {
		return test;
	}

	public void setTest(Test test) {
		this.test = test;
	}

	public int getSavolCount() {
		return savolCount;
	}

	public void setSavolCount(int savolCount) {
		this.savolCount = savolCount;
	}

	public int getTjCount() {
		return tjCount;
	}

	public void setTjCount(int tjCount) {
		this.tjCount = tjCount;
	}
	
}
