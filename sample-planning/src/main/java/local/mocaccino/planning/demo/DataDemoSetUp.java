package local.mocaccino.planning.demo;

import io.quarkus.runtime.StartupEvent;

import local.mocaccino.planning.entity.Auditorium;
import local.mocaccino.planning.entity.Lecture;
import local.mocaccino.planning.entity.Timeslot;
import local.mocaccino.planning.repository.AuditoriumRepository;
import local.mocaccino.planning.repository.LectureRepository;
import local.mocaccino.planning.repository.TimeslotRepository;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class DataDemoSetUp {

    @ConfigProperty(name = "billboard.dataDemo", defaultValue = "SMALL")
    DataDemoEnum dataDemo;

    @Inject
    TimeslotRepository timeslotRepository;

    @Inject
    AuditoriumRepository auditoriumRepository;

    @Inject
    LectureRepository lectureRepository;

    @Transactional
    public void generateDataDemo(@Observes StartupEvent startupEvent) {
        if (this.dataDemo == DataDemoEnum.NONE) {
            return;
        }

        // timeslot
        List<Timeslot> timeslots = new ArrayList<>(10);
        // MONDAY
        timeslots.add(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(8, 30), LocalTime.of(9, 30)));
        timeslots.add(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(9, 30), LocalTime.of(10, 30)));
        timeslots.add(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(10, 30), LocalTime.of(11, 30)));
        timeslots.add(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(13, 30), LocalTime.of(14, 30)));
        timeslots.add(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(14, 30), LocalTime.of(15, 30)));
        // TUESDAY
        timeslots.add(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(8, 30), LocalTime.of(9, 30)));
        timeslots.add(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(9, 30), LocalTime.of(10, 30)));
        timeslots.add(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(10, 30), LocalTime.of(11, 30)));
        timeslots.add(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(13, 30), LocalTime.of(14, 30)));
        timeslots.add(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(14, 30), LocalTime.of(15, 30)));
        if (this.dataDemo == DataDemoEnum.LARGE) {
            // WEDNESDAY
            timeslots.add(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(8, 30), LocalTime.of(9, 30)));
            timeslots.add(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 30), LocalTime.of(10, 30)));
            timeslots.add(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(10, 30), LocalTime.of(11, 30)));
            timeslots.add(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(14, 30)));
            timeslots.add(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 30), LocalTime.of(15, 30)));
            // THURSDAY
            timeslots.add(new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(8, 30), LocalTime.of(9, 30)));
            timeslots.add(new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(9, 30), LocalTime.of(10, 30)));
            timeslots.add(new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(10, 30), LocalTime.of(11, 30)));
            timeslots.add(new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(13, 30), LocalTime.of(14, 30)));
            timeslots.add(new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(14, 30), LocalTime.of(15, 30)));
            // FRIDAY
            timeslots.add(new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(8, 30), LocalTime.of(9, 30)));
            timeslots.add(new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(9, 30), LocalTime.of(10, 30)));
            timeslots.add(new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(10, 30), LocalTime.of(11, 30)));
            timeslots.add(new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(13, 30), LocalTime.of(14, 30)));
            timeslots.add(new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(14, 30), LocalTime.of(15, 30)));
        }
        this.timeslotRepository.persist(timeslots);

        // auditorium
        List<Auditorium> auditoriums = new ArrayList<>(3);
        auditoriums.add(new Auditorium("Mercury"));
        auditoriums.add(new Auditorium("Venus"));
        auditoriums.add(new Auditorium("Mars"));
        if (this.dataDemo == DataDemoEnum.LARGE) {
            auditoriums.add(new Auditorium("Jupiter"));
            auditoriums.add(new Auditorium("Saturn"));
            auditoriums.add(new Auditorium("Uranus"));
        }
        this.auditoriumRepository.persist(auditoriums);

        // lecture
        List<Lecture> lectures = new ArrayList<>();

        // 4th
        lectures.add(new Lecture());
        lectures.add(new Lecture());
        lectures.add(new Lecture());
        lectures.add(new Lecture());
        lectures.add(new Lecture());
        lectures.add(new Lecture());
        lectures.add(new Lecture());
        lectures.add(new Lecture());
        lectures.add(new Lecture());
        lectures.add(new Lecture());
        if (this.dataDemo == DataDemoEnum.LARGE) {
            lectures.add(new Lecture());
            lectures.add(new Lecture());
            lectures.add(new Lecture());
            lectures.add(new Lecture());
            lectures.add(new Lecture());
            lectures.add(new Lecture());
            lectures.add(new Lecture());
            lectures.add(new Lecture());
            lectures.add(new Lecture());
            lectures.add(new Lecture());
            lectures.add(new Lecture());
            lectures.add(new Lecture());
            lectures.add(new Lecture());
            lectures.add(new Lecture());
            lectures.add(new Lecture());
        }

        // 5th
        lectures.add(new Lecture());
        lectures.add(new Lecture());
        lectures.add(new Lecture());
        lectures.add(new Lecture());
        lectures.add(new Lecture());
        lectures.add(new Lecture());
        lectures.add(new Lecture());
        lectures.add(new Lecture());
        lectures.add(new Lecture());
        lectures.add(new Lecture());
        if (this.dataDemo == DataDemoEnum.LARGE) {
            lectures.add(new Lecture());
            lectures.add(new Lecture());
            lectures.add(new Lecture());
            lectures.add(new Lecture());
            lectures.add(new Lecture());
            lectures.add(new Lecture());
            lectures.add(new Lecture());
            lectures.add(new Lecture());
            lectures.add(new Lecture());
            lectures.add(new Lecture());
            lectures.add(new Lecture());
            lectures.add(new Lecture());
            lectures.add(new Lecture());
            lectures.add(new Lecture());
            lectures.add(new Lecture());
        }


        // 6th
        lectures.add(new Lecture());
        lectures.add(new Lecture());
        lectures.add(new Lecture());
        lectures.add(new Lecture());
        lectures.add(new Lecture());
        lectures.add(new Lecture());
        lectures.add(new Lecture());
        lectures.add(new Lecture());
        lectures.add(new Lecture());
        lectures.add(new Lecture());
        if (this.dataDemo == DataDemoEnum.LARGE) {
            lectures.add(new Lecture());
            lectures.add(new Lecture());
            lectures.add(new Lecture());
            lectures.add(new Lecture());
            lectures.add(new Lecture());
            lectures.add(new Lecture());
            lectures.add(new Lecture());
            lectures.add(new Lecture());
            lectures.add(new Lecture());
            lectures.add(new Lecture());
            lectures.add(new Lecture());
            lectures.add(new Lecture());
            lectures.add(new Lecture());
            lectures.add(new Lecture());
            lectures.add(new Lecture());
        }

        // 7th
        lectures.add(new Lecture());
        lectures.add(new Lecture());
        lectures.add(new Lecture());
        lectures.add(new Lecture());
        lectures.add(new Lecture());
        lectures.add(new Lecture());
        lectures.add(new Lecture());
        lectures.add(new Lecture());
        lectures.add(new Lecture());
        lectures.add(new Lecture());
        if (this.dataDemo == DataDemoEnum.LARGE) {
            lectures.add(new Lecture());
            lectures.add(new Lecture());
            lectures.add(new Lecture());
            lectures.add(new Lecture());
            lectures.add(new Lecture());
            lectures.add(new Lecture());
            lectures.add(new Lecture());
            lectures.add(new Lecture());
            lectures.add(new Lecture());
            lectures.add(new Lecture());
            lectures.add(new Lecture());
            lectures.add(new Lecture());
            lectures.add(new Lecture());
            lectures.add(new Lecture());
            lectures.add(new Lecture());
        }

        Lecture lecture = lectures.get(0);
        lecture.setTimeslot(timeslots.get(0));
        lecture.setAuditorium(auditoriums.get(0));

        this.lectureRepository.persist(lectures);
    }
}
