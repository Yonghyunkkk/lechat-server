package lechat.server.domain.course.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalTime;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Course {

    @Id
    @GeneratedValue
    @Column(name = "course_id")
    private Long id;

    @NotBlank
    @Column(name = "course_code", unique = true, nullable = false, length = 100)
    private String courseCode;

    @NotBlank
    @Column(name = "course_name", unique = true, nullable = false, length = 100)
    private String courseName;

    @Enumerated(EnumType.STRING)
    @Column(name = "semester")
    private SemesterType semester;

    @Enumerated(EnumType.STRING)
    @Column(name = "day")
    private DayType day;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    public static Course createCourse(
            String courseCode,
            String courseName,
            SemesterType semester,
            DayType day,
            LocalTime startTime,
            LocalTime endTime
    ) {
        Course course = Course.builder()
                .courseCode(courseCode)
                .courseName(courseName)
                .semester(semester)
                .day(day)
                .startTime(startTime)
                .endTime(endTime)
                .build();

        return course;
    }

}
