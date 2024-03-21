package lechat.server.domain.post.entity;

import jakarta.persistence.*;
import lechat.server.domain.member.entity.Member;
import lombok.*;

import java.time.LocalDate;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Comment {

    @Id @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    @Setter
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Setter
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Column(name = "content", nullable = false, length = 1000)
    private String content;

    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;

    public static Comment createComment(
            Member member,
            Post post,
            String content,
            LocalDate createdAt
    ) {
        Comment comment = Comment.builder()
                .member(member)
                .post(post)
                .content(content)
                .createdAt(createdAt)
                .build();

        post.addComments(comment);
        member.addComment(comment);

        return comment;
    }

}
