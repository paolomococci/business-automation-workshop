package local.mocaccino.planning.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import local.mocaccino.planning.entity.Auditorium;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AuditoriumRepository
        implements PanacheRepository<Auditorium> {
}
