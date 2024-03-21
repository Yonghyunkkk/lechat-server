package lechat.server.domain.course.service;

import lechat.server.domain.course.controller.response.CoursesRes;
import lechat.server.domain.course.entity.DayType;
import lechat.server.domain.course.entity.SemesterType;
import lechat.server.domain.course.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;

    public List<CoursesRes> getCoursesInSchedule() {
        DayType dayOfWeek = getDayOfWeek();
        LocalTime currentTime = getCurrentTime();
        SemesterType semester = checkSemester(LocalDate.now());

        return courseRepository.findCoursesInSchedule(dayOfWeek, semester, currentTime);
    }

    public List<CoursesRes> getCoursesNotInSchedule() {
        DayType dayOfWeek = getDayOfWeek();
        LocalTime currentTime = getCurrentTime();
        SemesterType semester = checkSemester(LocalDate.now());

        return courseRepository.findCoursesNotInSchedule(dayOfWeek, semester, currentTime);
    }

    private LocalTime getCurrentTime() {
        LocalTime currentTime = LocalTime.now();
        return currentTime;
    }

    private DayType getDayOfWeek() {
        LocalDate currentDate = LocalDate.now();
        DayOfWeek dayOfWeek = currentDate.getDayOfWeek();
        return DayType.fromDayOfWeek(dayOfWeek.toString());
    }

    public SemesterType checkSemester(LocalDate date) {
        LocalDate firstSemesterStart = LocalDate.of(date.getYear(), 7, 30);
        LocalDate firstSemesterEnd = LocalDate.of(date.getYear(), 12, 31);

        if (!date.isBefore(firstSemesterStart) && !date.isAfter(firstSemesterEnd)) {
            return SemesterType.FIRST;
        } else {
            return SemesterType.SECOND;
        }
    }
}
