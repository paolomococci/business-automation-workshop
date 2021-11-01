package local.mocaccino.planning.solution;

import local.mocaccino.planning.entity.Auditorium;
import local.mocaccino.planning.entity.Lecture;
import local.mocaccino.planning.entity.Timeslot;
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.solver.SolverStatus;

import java.util.List;

@PlanningSolution
public class Billboard {

    @ProblemFactCollectionProperty
    @ValueRangeProvider(id = "auditoriumRange")
    private List<Auditorium> auditoriums;

    @ProblemFactCollectionProperty
    @ValueRangeProvider(id = "timeslotRange")
    private List<Timeslot> timeslots;

    @PlanningEntityCollectionProperty
    private List<Lecture> lectures;

    @PlanningScore
    private HardSoftScore score;

    private SolverStatus solverStatus;

    public Billboard() {
    }

    public Billboard(
            List<Auditorium> auditoriums,
            List<Timeslot> timeslots,
            List<Lecture> lectures
    ) {
        this.auditoriums = auditoriums;
        this.timeslots = timeslots;
        this.lectures = lectures;
    }

    public List<Auditorium> getAuditoriums() {
        return auditoriums;
    }

    public List<Timeslot> getTimeslots() {
        return timeslots;
    }

    public List<Lecture> getLectures() {
        return lectures;
    }

    public HardSoftScore getScore() {
        return score;
    }

    public SolverStatus getSolverStatus() {
        return solverStatus;
    }

    public void setSolverStatus(SolverStatus solverStatus) {
        this.solverStatus = solverStatus;
    }
}
