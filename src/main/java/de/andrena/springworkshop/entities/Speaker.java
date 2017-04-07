package de.andrena.springworkshop.entities;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class Speaker {

    @EmbeddedId
    private SpeakerKey name;

    private String title;

    public SpeakerKey getName() {
        return name;
    }

    public void setName(SpeakerKey name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
