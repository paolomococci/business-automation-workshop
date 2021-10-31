package local.mocaccino.planning.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import local.mocaccino.planning.entity.Timeslot;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TimeslotRepository
        implements PanacheRepository<Timeslot> {
}
