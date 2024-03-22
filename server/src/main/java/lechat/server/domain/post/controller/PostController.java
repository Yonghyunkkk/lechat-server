package lechat.server.domain.post.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lechat.server.domain.post.controller.request.CreatePostReq;
import lechat.server.domain.post.controller.request.UpdatePostReq;
import lechat.server.domain.post.controller.response.CreatePostRes;
import lechat.server.domain.post.controller.response.GetAllPostsRes;
import lechat.server.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "3. Posts")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PostController {

    private final PostService postService;

    @PostMapping("/posts")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a post")
    public ResponseEntity<CreatePostRes> createPost(@Valid @RequestBody CreatePostReq request) {
        CreatePostRes responseBody = postService.createPost(request, 1L);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
    }

    @PatchMapping("/posts/{postId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Update a post")
    public void updateEvent(@PathVariable(name = "postId") Long postId, @RequestBody UpdatePostReq request) {
        postService.updatePost(1L, postId, request);
    }

    @DeleteMapping("/posts/{postId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a post")
    public void deleteEvent(@PathVariable(name = "postId") Long postId) {
        postService.deletePost(1L, postId);
    }

    @GetMapping("/posts/{courseId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all posts for course")
    public ResponseEntity<List<GetAllPostsRes>> getAllPosts(@PathVariable(name = "courseId") Long courseId) {
        List<GetAllPostsRes> responseBody = postService.getAllPosts(courseId);
        return ResponseEntity.ok().body(responseBody);
    }
}
