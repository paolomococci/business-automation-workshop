package local.mocaccino.planning.controller.resource;

//import io.quarkus.panache.common.Sort;

import local.mocaccino.planning.entity.Billboard;
import local.mocaccino.planning.repository.AuditoriumRepository;
import local.mocaccino.planning.repository.LectureRepository;
import local.mocaccino.planning.repository.TimeslotRepository;

import org.optaplanner.core.api.score.ScoreManager;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.solver.SolverManager;
import org.optaplanner.core.api.solver.SolverStatus;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("billboard")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BillboardResource {

    public static final Long SINGLETON_TIME_TABLE_ID = 1L;

    @Inject
    LectureRepository lectureRepository;

    @Inject
    AuditoriumRepository auditoriumRepository;

    @Inject
    TimeslotRepository timeslotRepository;

    @Inject
    SolverManager<Billboard, Long> billboardSolverManager;

    @Inject
    ScoreManager<Billboard, HardSoftScore> billboardScoreManager;

    @GET
    public Billboard getBillboard() {
        SolverStatus solverStatus = getSolverStatus();
        Billboard billboard = findById(SINGLETON_TIME_TABLE_ID);
        this.billboardScoreManager.updateScore(billboard);
        billboard.setSolverStatus(solverStatus);
        return billboard;
    }

    @POST
    @Path("start")
    public void startResolution() {
        this.billboardSolverManager.solveAndListen(
                SINGLETON_TIME_TABLE_ID,
                this::findById,
                this::save
        );
    }

    @POST
    @Path("stop")
    public void stopResolution() {
        this.billboardSolverManager
                .terminateEarly(SINGLETON_TIME_TABLE_ID);
    }

    @Transactional
    protected Billboard findById(Long id) {
        if (!SINGLETON_TIME_TABLE_ID.equals(id)) {
            throw new IllegalStateException("There is no billboard with id (" + id + ").");
        }
        return new Billboard(
                this.auditoriumRepository.listAll(
                        //Sort.by("label").and("id")
                ),
                this.timeslotRepository.listAll(
                        //Sort.by("day").and("start").and("end").and("id")
                ),
                this.lectureRepository.listAll(
                        //Sort.by("topic").and("lecturer").and("audience").and("id")
                )
        );
    }

    @Transactional
    protected void save(Billboard billboard) {

    }

    public SolverStatus getSolverStatus() {
        return this.billboardSolverManager.getSolverStatus(SINGLETON_TIME_TABLE_ID);
    }
}
