package lechat.server.domain.post.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lechat.server.domain.post.controller.request.CreatePostReq;
import lechat.server.domain.post.controller.response.CreatePostRes;
import lechat.server.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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


}
