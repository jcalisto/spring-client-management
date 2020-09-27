package com.multicert.task.MClientsApp.models;


import javax.validation.constraints.*;
import javax.persistence.*;

@Entity
@Table(name = "clients", schema = "public")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "NIF is mandatory")
    @Pattern(regexp="[\\d]{9}")
    private String nif;

    @Pattern(regexp="[\\d]{9}")
	private String phone;
    
	private String address;

	public Client(){
		
	}

	public Client(String name, String nif, String phone, String address) {
        this.name = name;
        this.nif = nif;
		this.address = address;
		this.phone = phone;
    }

    public Client(Long id, String name, String nif) {
        this.id = id;
        this.name = name;
        this.nif = nif;
		this.address = "";
		this.phone = "";
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

	public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

	
	public String getNIF() {
        return nif;
    }

    public void setNIF(String nif) {
        this.nif = nif;
    }
    
    

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nif == null) ? 0 : nif.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Client other = (Client) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nif == null) {
			if (other.nif != null)
				return false;
		} else if (!nif.equals(other.nif))
			return false;
		return true;
	}

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Client{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
		sb.append(", phone=").append(phone);
        sb.append(", address=").append(address);
        sb.append('}');
        return sb.toString();
    }
}