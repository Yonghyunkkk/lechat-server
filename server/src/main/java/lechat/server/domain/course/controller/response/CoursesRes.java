package lechat.server.domain.course.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CoursesRes {

    private Long id;
    private String courseCode;
}
