package local.mocaccino.planning.entity;

import org.optaplanner.core.api.domain.lookup.PlanningId;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Auditorium {

    @PlanningId
    @Id
    @GeneratedValue
    private Long id;

    private String label;

    public Auditorium() {
    }

    public Auditorium(String label) {
        this.label = label;
    }

    public Auditorium(Long id, String label) {
        this.id = id;
        this.label = label;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "Auditorium{" +
                "id=" + id +
                ", label='" + label + '\'' +
                '}';
    }
}
