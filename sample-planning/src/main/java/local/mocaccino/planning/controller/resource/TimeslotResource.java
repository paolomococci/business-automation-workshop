package local.mocaccino.planning.controller.resource;

import io.quarkus.hibernate.orm.rest.data.panache.PanacheRepositoryResource;
import io.quarkus.rest.data.panache.ResourceProperties;
import local.mocaccino.planning.entity.Timeslot;
import local.mocaccino.planning.repository.TimeslotRepository;

@ResourceProperties(path = "timeslots")
public interface TimeslotResource
        extends PanacheRepositoryResource<TimeslotRepository, Timeslot, Long> {
}
