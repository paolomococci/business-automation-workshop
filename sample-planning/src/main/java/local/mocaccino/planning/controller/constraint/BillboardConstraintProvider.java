package local.mocaccino.planning.controller.constraint;

import local.mocaccino.planning.entity.Lecture;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.score.stream.Constraint;
import org.optaplanner.core.api.score.stream.ConstraintFactory;
import org.optaplanner.core.api.score.stream.ConstraintProvider;
import org.optaplanner.core.api.score.stream.Joiners;

import java.time.Duration;

public class BillboardConstraintProvider
        implements ConstraintProvider {

    @Override
    public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
        return new Constraint[] {
                /* hard constrains */
                this.auditoriumHardConstraint(constraintFactory),
                this.lecturerHardConstraint(constraintFactory),
                this.audienceHardConstraint(constraintFactory),
                /* soft constraints */
                this.lecturerSoftConstraint(constraintFactory),
                this.lecturerTimeEfficiencySoftConstraint(constraintFactory),
                this.audienceTopicsVarietySoftConstraint(constraintFactory)
        };
    }

    /* 1. Hard constraint:
     *   only one reading in the same auditorium in the same time slot
     */
    public Constraint auditoriumHardConstraint(ConstraintFactory constraintFactory) {
        return constraintFactory
                .fromUniquePair(
                    Lecture.class,
                    Joiners.equal(Lecture::getTimeslot),
                    Joiners.equal(Lecture::getAuditorium))
                .penalize(
                    "only one reading in the same auditorium in the same time slot",
                    HardSoftScore.ONE_HARD
                );
    }

    /* 2. Hard constraint:
     *  a lecturer can speak in a single reading, at a given moment
     */
    public Constraint lecturerHardConstraint(ConstraintFactory constraintFactory) {
        return constraintFactory
                .fromUniquePair(
                    Lecture.class,
                    Joiners.equal(Lecture::getTimeslot),
                    Joiners.equal(Lecture::getLecturer))
                .penalize(
                    "a lecturer can speak in a single reading, at a given moment",
                    HardSoftScore.ONE_HARD
                );
    }

    /* 3. Hard constraint:
     *  an audience can attend one reading at a time
     */
    public Constraint audienceHardConstraint(ConstraintFactory constraintFactory) {
        return constraintFactory
                .fromUniquePair(
                    Lecture.class,
                    Joiners.equal(Lecture::getTimeslot),
                    Joiners.equal(Lecture::getAudience)
                ).penalize(
                    "an audience can attend one reading at a time",
                    HardSoftScore.ONE_HARD
                );
    }

    /* 4. Soft constraint:
     *  every lecturer would prefer to always read in the same auditorium
     */
    public Constraint lecturerSoftConstraint(ConstraintFactory constraintFactory) {
        return constraintFactory
                .fromUniquePair(
                    Lecture.class,
                    Joiners.equal(Lecture::getLecturer)
                )
                .filter(
                    ((lecture1, lecture2) -> lecture1.getAuditorium() != lecture2.getAuditorium())
                )
                .penalize(
                    "every lecturer would prefer to always read in the same auditorium",
                    HardSoftScore.ONE_SOFT
                );
    }

    /* 5. Soft constraint:
     *  each speaker would prefer to have their readings sequentially without too many interruptions
     */
    public Constraint lecturerTimeEfficiencySoftConstraint(ConstraintFactory constraintFactory) {
        return constraintFactory
                .from(Lecture.class)
                .join(
                    Lecture.class,
                    Joiners.equal(Lecture::getLecturer),
                    Joiners.equal((lecture) -> lecture.getTimeslot().getDayOfWeek()))
                .filter(
                    (lecture1, lecture2) -> {
                        Duration duration = Duration.between(
                                lecture1.getTimeslot().getLocalTimeStop(),
                                lecture2.getTimeslot().getLocalTimeStart()
                        );
                        return !duration.isNegative() && duration.compareTo(Duration.ofMinutes(30)) <= 0;
                    }
                ).penalize(
                    "each speaker would prefer to have their readings sequentially without too many interruptions",
                    HardSoftScore.ONE_SOFT
                );
    }

    /* 6. Soft constraint:
     *  audiences would prefer not to attend more than one reading that focuses on topics that are too similar
     */
    public Constraint audienceTopicsVarietySoftConstraint(ConstraintFactory constraintFactory) {
        return constraintFactory
                .from(Lecture.class)
                .join(
                    Lecture.class,
                    Joiners.equal(Lecture::getTopic),
                    Joiners.equal(Lecture::getAudience),
                    Joiners.equal((lecture) -> lecture.getTimeslot().getDayOfWeek()))
                .filter(
                    (lecture1, lecture2) -> {
                        Duration duration = Duration.between(
                                lecture1.getTimeslot().getLocalTimeStop(),
                                lecture2.getTimeslot().getLocalTimeStart()
                        );
                        return !duration.isNegative() && duration.compareTo(Duration.ofMinutes(30)) <= 0;
                    }
                ).penalize(
                    "audiences would prefer not to attend more than one reading that focuses on topics that are too similar",
                    HardSoftScore.ONE_SOFT
                );
    }
}
