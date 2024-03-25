package lechat.server;


import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lechat.server.domain.course.entity.Course;
import lechat.server.domain.course.entity.DayType;
import lechat.server.domain.course.entity.SemesterType;
import lechat.server.domain.member.entity.Member;
import lechat.server.domain.member.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct // Indicate a method that should be executed after a bean has been initialized.
    public void init() {
        initService.dbInit1(); // 별도의 빈으로 등록해줘야 한다.
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;
        public void dbInit1() {
            Member m1 = Member.createMember("Bob", "bob@gmail.com", "abcdefg", Role.USER);
            Member m2 = Member.createMember("John", "john@gmail.com", "abcdefg", Role.USER);
            Member m3 = Member.createMember("Mary", "mary@gmail.com", "abcdefg", Role.USER);
            Member m4 = Member.createMember("David", "david@gmail.com", "abcdefg", Role.USER);
            Member m5 = Member.createMember("Sally", "sally@gmail.com", "abcdefg", Role.USER);
            Member m6 = Member.createMember("Katy", "katy@gmail.com", "abcdefg", Role.USER);


            em.persist(m1);
            em.persist(m2);
            em.persist(m3);
            em.persist(m4);
            em.persist(m5);
            em.persist(m6);

            Course c1 = Course.createCourse("COMP3230", "Operating System", SemesterType.SECOND, DayType.FRIDAY, LocalTime.parse("12:30"), LocalTime.parse("14:30"));
            Course c2 = Course.createCourse("COMP3251", "Algorithm", SemesterType.SECOND, DayType.FRIDAY, LocalTime.parse("12:30"), LocalTime.parse("14:30"));
            Course c3 = Course.createCourse("COMP3239", "Game", SemesterType.SECOND, DayType.FRIDAY, LocalTime.parse("12:30"), LocalTime.parse("14:30"));
            Course c4 = Course.createCourse("COMP3270", "Database", SemesterType.SECOND, DayType.FRIDAY, LocalTime.parse("12:30"), LocalTime.parse("14:30"));
            Course c5 = Course.createCourse("COMP3278", "Software Engineering", SemesterType.SECOND, DayType.FRIDAY, LocalTime.parse("12:30"), LocalTime.parse("14:30"));
            Course c6 = Course.createCourse("COMP2501", "DataScience", SemesterType.SECOND, DayType.FRIDAY, LocalTime.parse("12:30"), LocalTime.parse("14:30"));

            em.persist(c1);
            em.persist(c2);
            em.persist(c3);
            em.persist(c4);
            em.persist(c5);
            em.persist(c6);
        }
    }
}