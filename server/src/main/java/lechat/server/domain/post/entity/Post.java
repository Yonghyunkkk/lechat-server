package lechat.server.domain.post.entity;

import jakarta.persistence.*;
import lechat.server.domain.course.entity.Course;
import lechat.server.domain.member.entity.Member;
import lombok.*;

import java.time.LocalDate;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Post {

    @Id @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    @Setter
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Setter
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(name = "content", nullable = false, length = 1000)
    private String content;

    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;

    public static Post createPost(
            Member member,
            Course course,
            String content,
            LocalDate createdAt
    ) {
        Post post = Post.builder()
                .member(member)
                .course(course)
                .content(content)
                .createdAt(createdAt)
                .build();

        member.addPost(post);

        return post;
    }

}
