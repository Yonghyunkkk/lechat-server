package lechat.server.domain.course.repository;

import lechat.server.domain.course.controller.response.CoursesRes;
import lechat.server.domain.course.entity.Course;
import lechat.server.domain.course.entity.DayType;
import lechat.server.domain.course.entity.SemesterType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalTime;
import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query("select new lechat.server.domain.course.controller.response.CoursesRes(c.id, c.courseCode) from Course c " +
            "where c.dayOfWeek = :dayOfWeek and " +
            "c.semester = :semester and " +
            "c.startTime <= :currentTime and " +
            "c.endTime >= :currentTime")
    List<CoursesRes> findCoursesInSchedule(
            @Param("dayOfWeek") DayType dayOfWeek,
            @Param("semester") SemesterType semester,
            @Param("currentTime") LocalTime currentTime);

    @Query("select new lechat.server.domain.course.controller.response.CoursesRes(c.id, c.courseCode) from Course c " +
            "where c.semester = :semester and " +
            "(((c.startTime > :currentTime or c.endTime < :currentTime) and c.dayOfWeek = :dayOfWeek) or (c.dayOfWeek != :dayOfWeek))")
    List<CoursesRes> findCoursesNotInSchedule(
            @Param("dayOfWeek") DayType dayOfWeek,
            @Param("semester") SemesterType semester,
            @Param("currentTime") LocalTime currentTime);
}
