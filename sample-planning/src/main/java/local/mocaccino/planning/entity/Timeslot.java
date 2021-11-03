package local.mocaccino.planning.entity;

import org.optaplanner.core.api.domain.lookup.PlanningId;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity(name = "TIMESLOT")
public class Timeslot {

    @PlanningId
    @Id
    @GeneratedValue
    private Long id;

    private DayOfWeek dayOfWeek;
    private LocalTime localTimeStart;
    private LocalTime localTimeStop;

    public Timeslot() {
    }

    public Timeslot(
            DayOfWeek dayOfWeek,
            LocalTime localTimeStart,
            LocalTime localTimeStop
    ) {
        this.dayOfWeek = dayOfWeek;
        this.localTimeStart = localTimeStart;
        this.localTimeStop = localTimeStop;
    }

    public Timeslot(
            Long id,
            DayOfWeek dayOfWeek,
            LocalTime localTimeStart
    ) {
        this.id = id;
        this.dayOfWeek = dayOfWeek;
        this.localTimeStart = localTimeStart;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public LocalTime getLocalTimeStart() {
        return localTimeStart;
    }

    public void setLocalTimeStart(LocalTime localTimeStart) {
        this.localTimeStart = localTimeStart;
    }

    public LocalTime getLocalTimeStop() {
        return localTimeStop;
    }

    public void setLocalTimeStop(LocalTime localTimeStop) {
        this.localTimeStop = localTimeStop;
    }

    @Override
    public String toString() {
        return "Timeslot{" +
                "dayOfWeek=" + dayOfWeek +
                ", localTimeStart=" + localTimeStart +
                ", localTimeStop=" + localTimeStop +
                '}';
    }
}
