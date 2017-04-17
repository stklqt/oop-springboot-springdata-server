package de.andrena.springworkshop.entities;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Lob;

@Entity
public class Speaker {


    @EmbeddedId
    private SpeakerKey name;
    private String company;
    @Lob
    @Column(length = 20971520)
    private String biography;

    public Speaker(SpeakerKey name, String company, String biography) {
        this.name = name;
        this.company = company;
        this.biography = biography;
    }

    public Speaker() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Speaker speaker = (Speaker) o;

        return new EqualsBuilder()
                .append(name, speaker.name)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(name)
                .toHashCode();
    }

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

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }
}
