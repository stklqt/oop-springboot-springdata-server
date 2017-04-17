package de.andrena.springworkshop.entities;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.hateoas.ResourceSupport;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class SpeakerKey extends ResourceSupport implements Serializable {
    private String firstName;
    private String surname;

    public SpeakerKey(String firstName, String surname) {
        this.firstName = firstName;
        this.surname = surname;
    }

    public SpeakerKey() {
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        SpeakerKey that = (SpeakerKey) o;

        return new EqualsBuilder()
                .append(firstName, that.firstName)
                .append(surname, that.surname)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(firstName)
                .append(surname)
                .toHashCode();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("firstName", firstName)
                .append("surname", surname)
                .toString();
    }

}
