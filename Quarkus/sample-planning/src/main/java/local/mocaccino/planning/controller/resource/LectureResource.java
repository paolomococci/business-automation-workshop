package local.mocaccino.planning.controller.resource;

import io.quarkus.hibernate.orm.rest.data.panache.PanacheRepositoryResource;
import io.quarkus.rest.data.panache.ResourceProperties;
import local.mocaccino.planning.entity.Lecture;
import local.mocaccino.planning.repository.LectureRepository;

@ResourceProperties(path = "lectures")
public interface LectureResource
        extends PanacheRepositoryResource<LectureRepository, Lecture, Long> {
}
