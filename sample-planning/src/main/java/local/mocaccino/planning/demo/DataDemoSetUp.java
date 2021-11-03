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

/*
Attention: the following is only code aimed at testing the application, and has no claim to historical classification.
Likewise, the names of the lecturers are completely made up.
 */

@ApplicationScoped
public class DataDemoSetUp {

    @ConfigProperty(name = "billboard.dataDemo", defaultValue = "MEDIUM")
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
        timeslots.add(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(8, 30), LocalTime.of(9, 20)));
        timeslots.add(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(9, 30), LocalTime.of(10, 20)));
        timeslots.add(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(10, 30), LocalTime.of(11, 20)));
        timeslots.add(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(13, 30), LocalTime.of(14, 20)));
        timeslots.add(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(14, 30), LocalTime.of(15, 20)));
        timeslots.add(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(15, 30), LocalTime.of(16, 20)));
        timeslots.add(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(16, 30), LocalTime.of(17, 20)));
        // TUESDAY
        timeslots.add(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(8, 30), LocalTime.of(9, 20)));
        timeslots.add(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(9, 30), LocalTime.of(10, 20)));
        timeslots.add(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(10, 30), LocalTime.of(11, 20)));
        timeslots.add(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(13, 30), LocalTime.of(14, 20)));
        timeslots.add(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(14, 30), LocalTime.of(15, 20)));
        timeslots.add(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(15, 30), LocalTime.of(16, 20)));
        timeslots.add(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(16, 30), LocalTime.of(17, 20)));
        if (this.dataDemo == DataDemoEnum.MEDIUM) {
            // WEDNESDAY
            timeslots.add(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(8, 30), LocalTime.of(9, 20)));
            timeslots.add(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 30), LocalTime.of(10, 20)));
            timeslots.add(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(10, 30), LocalTime.of(11, 20)));
            timeslots.add(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(14, 20)));
            timeslots.add(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 30), LocalTime.of(15, 20)));
            timeslots.add(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(15, 30), LocalTime.of(16, 20)));
            timeslots.add(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.of(16, 30), LocalTime.of(17, 20)));
            // THURSDAY
            timeslots.add(new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(8, 30), LocalTime.of(9, 20)));
            timeslots.add(new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(9, 30), LocalTime.of(10, 20)));
            timeslots.add(new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(10, 30), LocalTime.of(11, 20)));
            timeslots.add(new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(13, 30), LocalTime.of(14, 20)));
            timeslots.add(new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(14, 30), LocalTime.of(15, 20)));
            timeslots.add(new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(15, 30), LocalTime.of(16, 20)));
            timeslots.add(new Timeslot(DayOfWeek.THURSDAY, LocalTime.of(16, 30), LocalTime.of(17, 20)));
            // FRIDAY
            timeslots.add(new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(8, 30), LocalTime.of(9, 20)));
            timeslots.add(new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(9, 30), LocalTime.of(10, 20)));
            timeslots.add(new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(10, 30), LocalTime.of(11, 20)));
            timeslots.add(new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(13, 30), LocalTime.of(14, 20)));
            timeslots.add(new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(14, 30), LocalTime.of(15, 20)));
            timeslots.add(new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(15, 30), LocalTime.of(16, 20)));
            timeslots.add(new Timeslot(DayOfWeek.FRIDAY, LocalTime.of(16, 30), LocalTime.of(17, 20)));
        }
        this.timeslotRepository.persist(timeslots);

        // auditorium
        List<Auditorium> auditoriums = new ArrayList<>(3);
        auditoriums.add(new Auditorium("Peyton Randolph"));
        auditoriums.add(new Auditorium("John Hancock"));
        auditoriums.add(new Auditorium("Benjamin Franklin"));
        auditoriums.add(new Auditorium("George Washington"));
        if (this.dataDemo == DataDemoEnum.MEDIUM) {
            auditoriums.add(new Auditorium("Horatio Gates"));
            auditoriums.add(new Auditorium("Nathanael Greene"));
            auditoriums.add(new Auditorium("Henry Knox"));
            auditoriums.add(new Auditorium("John Sullivan"));
            auditoriums.add(new Auditorium("Benedict Arnold"));
            auditoriums.add(new Auditorium("George Rogers Clark"));
            auditoriums.add(new Auditorium("Lafayette"));
        }
        this.auditoriumRepository.persist(auditoriums);

        // lecture: American Revolutionary War
        // lectures.add(new Lecture("historical figures", "lecturer", "audience"));
        List<Lecture> lectures = new ArrayList<>();

        // first: Prelude
        lectures.add(new Lecture("Peyton Randolph", "Alan Arisen", "prelude"));
        lectures.add(new Lecture("John Hancock", "Alan Arisen", "prelude"));
        lectures.add(new Lecture("Benjamin Franklin", "Amy Built", "prelude"));
        lectures.add(new Lecture("George Washington", "Amy Built", "prelude"));
        lectures.add(new Lecture("Horatio Gates", "Betty Clad", "prelude"));
        lectures.add(new Lecture("Nathanael Greene", "Claire Done", "prelude"));
        lectures.add(new Lecture("Henry Knox", "Clair Done", "prelude"));
        lectures.add(new Lecture("John Sullivan", "Roger Driven", "prelude"));
        lectures.add(new Lecture("Benedict Arnold", "Alan Arisen", "prelude"));
        lectures.add(new Lecture("George Rogers Clark", "Roger Driven", "prelude"));
        lectures.add(new Lecture("Gilbert du Motier, Marquis de Lafayette", "Gary Bidden", "prelude"));
        if (this.dataDemo == DataDemoEnum.MEDIUM) {
            lectures.add(new Lecture("George III", "Susan Woven", "prelude"));
            lectures.add(new Lecture("Frederick North, Lord North", "Sophie Won", "prelude"));
            lectures.add(new Lecture("William Petty, 2nd Earl of Shelburne", "Sophie Won", "prelude"));
            lectures.add(new Lecture("George Germain, 1st Viscount Sackville", "Mark Sown", "prelude"));
            lectures.add(new Lecture("Thomas Gage", "Mark Sown", "prelude"));
            lectures.add(new Lecture("William Howe, 5th Viscount Howe", "Gary Bidden", "prelude"));
            lectures.add(new Lecture("Henry Clinton", "Gary Bidden", "prelude"));
            lectures.add(new Lecture("John Burgoyne", "Roger Driven", "prelude"));
            lectures.add(new Lecture("Charles Cornwallis, 1st Marquess Cornwallis", "Amy Built", "prelude"));
            lectures.add(new Lecture("Henry Hamilton", "Amy Built", "prelude"));
            lectures.add(new Lecture("Banastre Tarleton", "Claire Done", "prelude"));
            lectures.add(new Lecture("Charles Watson-Wentworth, 2nd Marquess of Rockingham", "Claire Done", "prelude"));
            lectures.add(new Lecture("John Montagu, 4th Earl of Sandwich", "Betty Clad", "prelude"));
            lectures.add(new Lecture("Jeffery Amherst, 1st Baron Amherst", "Alan Arisen", "prelude"));
            lectures.add(new Lecture("Henry Seymour Conway", "Claire Done", "prelude"));
            lectures.add(new Lecture("William Barrington, 2nd Viscount Barrington", "Susan Woven", "prelude"));
            lectures.add(new Lecture("Charles Jenkinson, 1st Earl of Liverpool", "Sophie Won", "prelude"));
        }

        // second: war breaks out
        lectures.add(new Lecture("Peyton Randolph", "Alan Arisen", "war breaks out"));
        lectures.add(new Lecture("John Hancock", "Alan Arisen", "war breaks out"));
        lectures.add(new Lecture("Benjamin Franklin", "Amy Built", "war breaks out"));
        lectures.add(new Lecture("George Washington", "Amy Built", "war breaks out"));
        lectures.add(new Lecture("Horatio Gates", "Betty Clad", "war breaks out"));
        lectures.add(new Lecture("Nathanael Greene", "Claire Done", "war breaks out"));
        lectures.add(new Lecture("Henry Knox", "Clair Done", "war breaks out"));
        lectures.add(new Lecture("John Sullivan", "Roger Driven", "war breaks out"));
        lectures.add(new Lecture("Benedict Arnold", "Alan Arisen", "war breaks out"));
        lectures.add(new Lecture("George Rogers Clark", "Roger Driven", "war breaks out"));
        lectures.add(new Lecture("Gilbert du Motier, Marquis de Lafayette", "Gary Bidden", "war breaks out"));
        if (this.dataDemo == DataDemoEnum.MEDIUM) {
            lectures.add(new Lecture("George III", "Susan Woven", "war breaks out"));
            lectures.add(new Lecture("Frederick North, Lord North", "Sophie Won", "war breaks out"));
            lectures.add(new Lecture("William Petty, 2nd Earl of Shelburne", "Sophie Won", "war breaks out"));
            lectures.add(new Lecture("George Germain, 1st Viscount Sackville", "Mark Sown", "war breaks out"));
            lectures.add(new Lecture("Thomas Gage", "Mark Sown", "war breaks out"));
            lectures.add(new Lecture("William Howe, 5th Viscount Howe", "Gary Bidden", "war breaks out"));
            lectures.add(new Lecture("Henry Clinton", "Gary Bidden", "war breaks out"));
            lectures.add(new Lecture("John Burgoyne", "Roger Driven", "war breaks out"));
            lectures.add(new Lecture("Charles Cornwallis, 1st Marquess Cornwallis", "Amy Built", "war breaks out"));
            lectures.add(new Lecture("Henry Hamilton", "Amy Built", "war breaks out"));
            lectures.add(new Lecture("Banastre Tarleton", "Claire Done", "war breaks out"));
            lectures.add(new Lecture("Charles Watson-Wentworth, 2nd Marquess of Rockingham", "Claire Done", "war breaks out"));
            lectures.add(new Lecture("John Montagu, 4th Earl of Sandwich", "Betty Clad", "war breaks out"));
            lectures.add(new Lecture("Jeffery Amherst, 1st Baron Amherst", "Alan Arisen", "war breaks out"));
            lectures.add(new Lecture("Henry Seymour Conway", "Claire Done", "war breaks out"));
            lectures.add(new Lecture("William Barrington, 2nd Viscount Barrington", "Susan Woven", "war breaks out"));
            lectures.add(new Lecture("Charles Jenkinson, 1st Earl of Liverpool", "Sophie Won", "war breaks out"));
        }


        // third: strategy
        lectures.add(new Lecture("Peyton Randolph", "Alan Arisen", "strategy"));
        lectures.add(new Lecture("John Hancock", "Alan Arisen", "strategy"));
        lectures.add(new Lecture("Benjamin Franklin", "Amy Built", "strategy"));
        lectures.add(new Lecture("George Washington", "Amy Built", "strategy"));
        lectures.add(new Lecture("Horatio Gates", "Betty Clad", "strategy"));
        lectures.add(new Lecture("Nathanael Greene", "Claire Done", "strategy"));
        lectures.add(new Lecture("Henry Knox", "Clair Done", "strategy"));
        lectures.add(new Lecture("John Sullivan", "Roger Driven", "strategy"));
        lectures.add(new Lecture("Benedict Arnold", "Alan Arisen", "strategy"));
        lectures.add(new Lecture("George Rogers Clark", "Roger Driven", "strategy"));
        lectures.add(new Lecture("Gilbert du Motier, Marquis de Lafayette", "Gary Bidden", "strategy"));
        if (this.dataDemo == DataDemoEnum.MEDIUM) {
            lectures.add(new Lecture("George III", "Susan Woven", "strategy"));
            lectures.add(new Lecture("Frederick North, Lord North", "Sophie Won", "strategy"));
            lectures.add(new Lecture("William Petty, 2nd Earl of Shelburne", "Sophie Won", "strategy"));
            lectures.add(new Lecture("George Germain, 1st Viscount Sackville", "Mark Sown", "strategy"));
            lectures.add(new Lecture("Thomas Gage", "Mark Sown", "strategy"));
            lectures.add(new Lecture("William Howe, 5th Viscount Howe", "Gary Bidden", "strategy"));
            lectures.add(new Lecture("Henry Clinton", "Gary Bidden", "strategy"));
            lectures.add(new Lecture("John Burgoyne", "Roger Driven", "strategy"));
            lectures.add(new Lecture("Charles Cornwallis, 1st Marquess Cornwallis", "Amy Built", "strategy"));
            lectures.add(new Lecture("Henry Hamilton", "Amy Built", "strategy"));
            lectures.add(new Lecture("Banastre Tarleton", "Claire Done", "strategy"));
            lectures.add(new Lecture("Charles Watson-Wentworth, 2nd Marquess of Rockingham", "Claire Done", "strategy"));
            lectures.add(new Lecture("John Montagu, 4th Earl of Sandwich", "Betty Clad", "strategy"));
            lectures.add(new Lecture("Jeffery Amherst, 1st Baron Amherst", "Alan Arisen", "strategy"));
            lectures.add(new Lecture("Henry Seymour Conway", "Claire Done", "strategy"));
            lectures.add(new Lecture("William Barrington, 2nd Viscount Barrington", "Susan Woven", "strategy"));
            lectures.add(new Lecture("Charles Jenkinson, 1st Earl of Liverpool", "Sophie Won", "strategy"));
        }

        // fourth: civil war
        lectures.add(new Lecture("John Hancock", "Alan Arisen", "civil war"));
        lectures.add(new Lecture("Benjamin Franklin", "Amy Built", "civil war"));
        lectures.add(new Lecture("George Washington", "Amy Built", "civil war"));
        lectures.add(new Lecture("Horatio Gates", "Betty Clad", "civil war"));
        lectures.add(new Lecture("Nathanael Greene", "Claire Done", "civil war"));
        lectures.add(new Lecture("Henry Knox", "Clair Done", "civil war"));
        lectures.add(new Lecture("John Sullivan", "Roger Driven", "civil war"));
        lectures.add(new Lecture("Benedict Arnold", "Alan Arisen", "civil war"));
        lectures.add(new Lecture("George Rogers Clark", "Roger Driven", "civil war"));
        lectures.add(new Lecture("Gilbert du Motier, Marquis de Lafayette", "Gary Bidden", "civil war"));
        if (this.dataDemo == DataDemoEnum.MEDIUM) {
            lectures.add(new Lecture("George III", "Susan Woven", "civil war"));
            lectures.add(new Lecture("Frederick North, Lord North", "Sophie Won", "civil war"));
            lectures.add(new Lecture("William Petty, 2nd Earl of Shelburne", "Sophie Won", "civil war"));
            lectures.add(new Lecture("George Germain, 1st Viscount Sackville", "Mark Sown", "civil war"));
            lectures.add(new Lecture("Thomas Gage", "Mark Sown", "civil war"));
            lectures.add(new Lecture("William Howe, 5th Viscount Howe", "Gary Bidden", "civil war"));
            lectures.add(new Lecture("Henry Clinton", "Gary Bidden", "civil war"));
            lectures.add(new Lecture("John Burgoyne", "Roger Driven", "civil war"));
            lectures.add(new Lecture("Charles Cornwallis, 1st Marquess Cornwallis", "Amy Built", "civil war"));
            lectures.add(new Lecture("Henry Hamilton", "Amy Built", "civil war"));
            lectures.add(new Lecture("Banastre Tarleton", "Claire Done", "civil war"));
            lectures.add(new Lecture("Charles Watson-Wentworth, 2nd Marquess of Rockingham", "Claire Done", "civil war"));
            lectures.add(new Lecture("John Montagu, 4th Earl of Sandwich", "Betty Clad", "civil war"));
            lectures.add(new Lecture("Jeffery Amherst, 1st Baron Amherst", "Alan Arisen", "civil war"));
            lectures.add(new Lecture("Henry Seymour Conway", "Claire Done", "civil war"));
            lectures.add(new Lecture("William Barrington, 2nd Viscount Barrington", "Susan Woven", "civil war"));
            lectures.add(new Lecture("Charles Jenkinson, 1st Earl of Liverpool", "Sophie Won", "civil war"));
        }

        // fifth: peace
        lectures.add(new Lecture("John Hancock", "Alan Arisen", "peace"));
        lectures.add(new Lecture("Benjamin Franklin", "Amy Built", "peace"));
        lectures.add(new Lecture("George Washington", "Amy Built", "peace"));
        lectures.add(new Lecture("Horatio Gates", "Betty Clad", "peace"));
        lectures.add(new Lecture("Nathanael Greene", "Claire Done", "peace"));
        lectures.add(new Lecture("Henry Knox", "Clair Done", "peace"));
        lectures.add(new Lecture("John Sullivan", "Roger Driven", "peace"));
        lectures.add(new Lecture("Benedict Arnold", "Alan Arisen", "peace"));
        lectures.add(new Lecture("George Rogers Clark", "Roger Driven", "peace"));
        lectures.add(new Lecture("Gilbert du Motier, Marquis de Lafayette", "Gary Bidden", "peace"));
        if (this.dataDemo == DataDemoEnum.MEDIUM) {
            lectures.add(new Lecture("George III", "Susan Woven", "peace"));
            lectures.add(new Lecture("Frederick North, Lord North", "Sophie Won", "peace"));
            lectures.add(new Lecture("William Petty, 2nd Earl of Shelburne", "Sophie Won", "peace"));
            lectures.add(new Lecture("George Germain, 1st Viscount Sackville", "Mark Sown", "peace"));
            lectures.add(new Lecture("Thomas Gage", "Mark Sown", "peace"));
            lectures.add(new Lecture("William Howe, 5th Viscount Howe", "Gary Bidden", "peace"));
            lectures.add(new Lecture("Henry Clinton", "Gary Bidden", "peace"));
            lectures.add(new Lecture("John Burgoyne", "Roger Driven", "peace"));
            lectures.add(new Lecture("Charles Cornwallis, 1st Marquess Cornwallis", "Amy Built", "peace"));
            lectures.add(new Lecture("Henry Hamilton", "Amy Built", "peace"));
            lectures.add(new Lecture("Banastre Tarleton", "Claire Done", "peace"));
            lectures.add(new Lecture("Charles Watson-Wentworth, 2nd Marquess of Rockingham", "Claire Done", "peace"));
            lectures.add(new Lecture("John Montagu, 4th Earl of Sandwich", "Betty Clad", "peace"));
            lectures.add(new Lecture("Jeffery Amherst, 1st Baron Amherst", "Alan Arisen", "peace"));
            lectures.add(new Lecture("Henry Seymour Conway", "Claire Done", "peace"));
            lectures.add(new Lecture("William Barrington, 2nd Viscount Barrington", "Susan Woven", "peace"));
            lectures.add(new Lecture("Charles Jenkinson, 1st Earl of Liverpool", "Sophie Won", "peace"));
        }

        Lecture lecture = lectures.get(0);
        lecture.setTimeslot(timeslots.get(0));
        lecture.setAuditorium(auditoriums.get(0));

        this.lectureRepository.persist(lectures);
    }
}
