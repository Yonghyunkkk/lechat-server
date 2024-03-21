package lechat.server.domain.post.entity;

import jakarta.persistence.*;
import lechat.server.domain.member.entity.Member;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;

@Getter
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ReplyComment {

    @Id
    @GeneratedValue
    @Column(name = "reply_comment_id")
    private Long id;

    @Setter
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Setter
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "comment_id", nullable = false)
    private Comment comment;

    @Column(name = "content", nullable = false, length = 1000)
    private String content;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public static ReplyComment createReplyComment(
            Member member,
            Comment comment,
            String content,
            LocalDateTime createdAt
    ) {
        ReplyComment replyComment = ReplyComment.builder()
                .member(member)
                .comment(comment)
                .content(content)
                .createdAt(createdAt)
                .build();

        comment.addReplyComments(replyComment);
        member.addReplyComment(replyComment);

        return replyComment;
    }
}
