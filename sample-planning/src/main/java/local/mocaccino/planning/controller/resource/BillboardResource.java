package local.mocaccino.planning.controller.resource;

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
        return null;
    }

    @POST
    @Path("start")
    public void startResolution() {

    }

    @POST
    @Path("stop")
    public void stopResolution() {

    }

    @Transactional
    protected Billboard findById(Long id) {
        return null;
    }

    @Transactional
    protected void save(Billboard billboard) {

    }

    public SolverStatus getSolverStatus() {
        return null;
    }
}
