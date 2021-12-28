package local.mocaccino.planning.controller.resource;

import io.quarkus.hibernate.orm.rest.data.panache.PanacheRepositoryResource;
import io.quarkus.rest.data.panache.ResourceProperties;
import local.mocaccino.planning.entity.Auditorium;
import local.mocaccino.planning.repository.AuditoriumRepository;

@ResourceProperties(path = "auditoriums")
public interface AuditoriumResource
        extends PanacheRepositoryResource<AuditoriumRepository, Auditorium, Long> {
}
