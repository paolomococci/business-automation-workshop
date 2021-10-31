package local.mocaccino.planning.entity;

import org.optaplanner.core.api.domain.lookup.PlanningId;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
public class Timeslot {

    @PlanningId
    @Id
    @GeneratedValue
    private Long id;

    private DayOfWeek day;
    private LocalTime start;
    private LocalTime end;

    public Timeslot() {
    }

    public Timeslot(
            DayOfWeek day,
            LocalTime start,
            LocalTime end
    ) {
        this.day = day;
        this.start = start;
        this.end = end;
    }

    public Timeslot(
            Long id,
            DayOfWeek day,
            LocalTime start
    ) {
        this.id = id;
        this.day = day;
        this.start = start;
    }

    public Timeslot(
            Long id,
            DayOfWeek day,
            LocalTime start,
            LocalTime end
    ) {
        this.id = id;
        this.day = day;
        this.start = start;
        this.end = end;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DayOfWeek getDay() {
        return day;
    }

    public void setDay(DayOfWeek day) {
        this.day = day;
    }

    public LocalTime getStart() {
        return start;
    }

    public void setStart(LocalTime start) {
        this.start = start;
    }

    public LocalTime getEnd() {
        return end;
    }

    public void setEnd(LocalTime end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "Timeslot{" +
                "id=" + id +
                ", day=" + day +
                ", start=" + start +
                ", end=" + end +
                '}';
    }
}
