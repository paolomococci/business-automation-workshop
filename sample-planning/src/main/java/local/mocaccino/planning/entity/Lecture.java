package local.mocaccino.planning.entity;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.lookup.PlanningId;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity(name = "LECTURE")
@PlanningEntity
public class Lecture {

    @PlanningId
    @Id
    @GeneratedValue
    private Long id;

    private String topic;
    private String lecturer;
    private String audience;

    @ManyToOne
    @PlanningVariable(valueRangeProviderRefs = "timeslotRange")
    private Timeslot timeslot;

    @ManyToOne
    @PlanningVariable(valueRangeProviderRefs = "auditoriumRange")
    private Auditorium auditorium;

    public Lecture() {
    }

    public Lecture(
            String topic,
            String lecturer,
            String audience
    ) {
        this.topic = topic;
        this.lecturer = lecturer;
        this.audience = audience;
    }

    public Lecture(
            Long id,
            String topic,
            String lecturer,
            String audience,
            Timeslot timeslot,
            Auditorium auditorium
    ) {
        this.id = id;
        this.topic = topic;
        this.lecturer = lecturer;
        this.audience = audience;
        this.timeslot = timeslot;
        this.auditorium = auditorium;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getLecturer() {
        return lecturer;
    }

    public void setLecturer(String lecturer) {
        this.lecturer = lecturer;
    }

    public String getAudience() {
        return audience;
    }

    public void setAudience(String audience) {
        this.audience = audience;
    }

    public Timeslot getTimeslot() {
        return timeslot;
    }

    public void setTimeslot(Timeslot timeslot) {
        this.timeslot = timeslot;
    }

    public Auditorium getAuditorium() {
        return auditorium;
    }

    public void setAuditorium(Auditorium auditorium) {
        this.auditorium = auditorium;
    }

    @Override
    public String toString() {
        return "Lecture{" +
                "id=" + id +
                ", topic='" + topic + '\'' +
                ", lecturer='" + lecturer + '\'' +
                ", audience='" + audience + '\'' +
                ", timeslot=" + timeslot +
                ", auditorium=" + auditorium +
                '}';
    }
}
