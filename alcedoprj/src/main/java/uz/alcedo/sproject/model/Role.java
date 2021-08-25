package uz.alcedo.sproject.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "roles")
public class Role implements Serializable {
	
	private static final long serialVersionUID = 1L;
    
    @Id
    @Column(length = 50)
    private String name;

    public Role() {}
       
 	public Role(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Role)) {
			return false;
		}
		return Objects.equals(name, ((Role) obj).getName());
	}
	
	@Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
	
	@Override
    public String toString() {
        return "Role {" +
            "name='" + name + '\'' +
            "}";
    }
    
}
