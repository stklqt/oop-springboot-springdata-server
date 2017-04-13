package de.andrena.springworkshop.entities;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class Speaker {

    @EmbeddedId
    private SpeakerKey name;
    private String company;

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("name", name)
                .append("company", company)
                .toString();
    }

    public SpeakerKey getName() {
        return name;
    }

    public void setName(SpeakerKey name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Speaker speaker = (Speaker) o;

        return new EqualsBuilder()
                .append(name, speaker.name)
                .append(company, speaker.company)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(name)
                .append(company)
                .toHashCode();
    }
}
