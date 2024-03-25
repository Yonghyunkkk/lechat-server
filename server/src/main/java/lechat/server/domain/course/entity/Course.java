package lechat.server.domain.course.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lechat.server.domain.post.entity.Post;
import lombok.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

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
    @Column(name = "day_of_week")
    private DayType dayOfWeek;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    @OneToMany(mappedBy = "course")
    @Builder.Default
    private List<Post> posts = new ArrayList<>();

    public static Course createCourse(
            String courseCode,
            String courseName,
            SemesterType semester,
            DayType dayOfWeek,
            LocalTime startTime,
            LocalTime endTime
    ) {
        Course course = Course.builder()
                .courseCode(courseCode)
                .courseName(courseName)
                .semester(semester)
                .dayOfWeek(dayOfWeek)
                .startTime(startTime)
                .endTime(endTime)
                .build();

        return course;
    }

    public void addPost(Post post) {
        this.posts.add(post);
        post.setCourse(this);
    }

}
