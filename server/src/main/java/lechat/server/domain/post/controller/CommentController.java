package lechat.server.domain.post.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lechat.server.domain.post.controller.request.CreateCommentReq;
import lechat.server.domain.post.controller.request.CreatePostReq;
import lechat.server.domain.post.controller.request.UpdateCommentReq;
import lechat.server.domain.post.controller.request.UpdatePostReq;
import lechat.server.domain.post.controller.response.CreateCommentRes;
import lechat.server.domain.post.controller.response.CreatePostRes;
import lechat.server.domain.post.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "4. Comments")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comments")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a comment")
    public ResponseEntity<CreateCommentRes> createComment(@Valid @RequestBody CreateCommentReq request) {
        CreateCommentRes responseBody = commentService.createComment(request, 1L);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
    }

    @PatchMapping("/comments/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Update a comment")
    public void updateEvent(@PathVariable(name = "commentId") Long commentId, @RequestBody UpdateCommentReq request) {
        commentService.updateComment(1L, commentId, request);
    }

    @DeleteMapping("/comments/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a comment")
    public void deleteEvent(@PathVariable(name = "commentId") Long commentId) {
        commentService.deleteComment(1L, commentId);
    }
}
