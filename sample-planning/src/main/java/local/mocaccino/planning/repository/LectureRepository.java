package local.mocaccino.planning.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import local.mocaccino.planning.entity.Lecture;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class LectureRepository
        implements PanacheRepository<Lecture> {
}
