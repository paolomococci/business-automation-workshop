package local.mocaccino.planning.controller.constraint;

import local.mocaccino.planning.entity.Lecture;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.score.stream.Constraint;
import org.optaplanner.core.api.score.stream.ConstraintFactory;
import org.optaplanner.core.api.score.stream.ConstraintProvider;
import org.optaplanner.core.api.score.stream.Joiners;

public class BillboardConstraintProvider
        implements ConstraintProvider {

    @Override
    public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
        return new Constraint[] {

        };
    }

    /* Constraint: only one reading in the same auditorium in the same time slot */
    public Constraint auditoriumConflictConstraint(ConstraintFactory constraintFactory) {
        return constraintFactory.fromUniquePair(
                Lecture.class,
                Joiners.equal(Lecture::getTimeslot),
                Joiners.equal(Lecture::getAuditorium)
        ).penalize("", HardSoftScore.ONE_HARD);
    }

    /* Constraint: a speaker can speak in a single reading, at a given moment */

    /* Constraint: an audience can attend one reading at a time */

    /* Constraint: every speaker would prefer to always read in the same auditorium */

    /* Constraint: each speaker would prefer to have their readings sequentially without too many interruptions */

    /* Constraint: audiences would prefer not to attend more than one reading that focuses on topics that are too similar */
}
