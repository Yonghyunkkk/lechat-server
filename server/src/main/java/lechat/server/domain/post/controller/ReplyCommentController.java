package lechat.server.domain.post.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lechat.server.domain.post.controller.request.CreateCommentReq;
import lechat.server.domain.post.controller.request.CreateReplyCommentReq;
import lechat.server.domain.post.controller.response.CreateCommentRes;
import lechat.server.domain.post.controller.response.CreateReplyCommentRes;
import lechat.server.domain.post.service.ReplyCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "5. Reply Comments")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ReplyCommentController {

    private final ReplyCommentService replyCommentService;

    @PostMapping("/reply-comments")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a reply comment")
    public ResponseEntity<CreateReplyCommentRes> createReplyComment(@Valid @RequestBody CreateReplyCommentReq request) {
        CreateReplyCommentRes responseBody = replyCommentService.createReplyComment(request, 1L);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
    }

    @DeleteMapping("/reply-comments/{replyCommentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a reply comment")
    public void deleteEvent(@PathVariable(name = "replyCommentId") Long replyCommentId) {
        replyCommentService.deleteReplyComment(1L, replyCommentId);
    }
}
