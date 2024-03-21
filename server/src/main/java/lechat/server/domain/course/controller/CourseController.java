package lechat.server.domain.course.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lechat.server.domain.course.controller.response.CoursesRes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "2. Courses")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/courses")
public class CourseController {

    @GetMapping("/in-schedule")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get courses in schedule")
    public ResponseEntity<CoursesRes> getCoursesInSchedule() {

    }
}
