package lechat.server.domain.course.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lechat.server.domain.course.controller.response.CoursesRes;
import lechat.server.domain.course.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "2. Courses")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CourseController {

    private final CourseService courseService;

    @GetMapping("/courses/in-schedule")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get courses in schedule")
    public ResponseEntity<List<CoursesRes>> getCoursesInSchedule() {
        List<CoursesRes> responseBody = courseService.getCoursesInSchedule();
        return ResponseEntity.ok().body(responseBody);
    }

    @GetMapping("/courses/not-in-schedule")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get courses not in schedule")
    public ResponseEntity<List<CoursesRes>> getCoursesNotInSchedule() {
        List<CoursesRes> responseBody = courseService.getCoursesNotInSchedule();
        return ResponseEntity.ok().body(responseBody);
    }
}
