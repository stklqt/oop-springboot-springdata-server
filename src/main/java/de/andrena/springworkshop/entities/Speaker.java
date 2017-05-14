package de.andrena.springworkshop.entities;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class Speaker {


	@Id
	@GeneratedValue
	private Integer id;
	private String firstName;
	private String lastName;
	private String company;
	@Lob
	@Column(length = 20971520)
	private String biography;

	public Speaker(String firstName, String lastName, String company, String biography) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.company = company;
		this.biography = biography;
	}

    public Speaker() {

    }

    @Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", id)
				.append("firstName", firstName)
				.append("lastName", lastName)
				.append("company", company)
				.append("biography", biography)
				.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

		if (o == null || getClass() != o.getClass()) return false;

		Speaker speaker = (Speaker) o;

		return new EqualsBuilder()
				.append(id, speaker.id)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
				.append(id)
				.toHashCode();
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }
}
