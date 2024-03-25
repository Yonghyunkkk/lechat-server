package lechat.server.domain.post.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@AllArgsConstructor
public class GetSinglePostRes {

    private Long postId;
    private String postTitle;
    private String postContent;
    private String postAuthor;
    private String postCreatedAt;
    private Boolean isAuthor;
    private List<CommentDto> comments;

    @Getter
    @AllArgsConstructor
    public static class CommentDto {
        private Long commentId;
        private String commentContent;
        private String commentAuthor;
        private String commentCreatedAt;
        private Boolean isAuthor;
        private List<ReplyCommentDto> replyComments;
    }

    @Getter
    @Builder
    public static class ReplyCommentDto {
        private Long replyCommentId;
        private String replyCommentContent;
        private String replyCommentAuthor;
        private String replyCommentCreatedAt;
        private Boolean replyCommentIsAuthor;
    }
}
