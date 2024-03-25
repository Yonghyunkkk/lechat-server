package lechat.server.domain.post.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class GetAllPostsRes {

    private Long postId;
    private String author;
    private String title;
    private String content;
    private String createdAt;
    private Boolean isAuthor;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public GetAllPostsRes(Long postId, String author, String title, String content, LocalDateTime createdAt, Boolean isAuthor) {
        this.postId = postId;
        this.author = author;
        this.title = title;
        this.content = content;
        this.createdAt = formatDateTime(createdAt);
        this.isAuthor = isAuthor;
    }

    private String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(formatter);
    }

}
