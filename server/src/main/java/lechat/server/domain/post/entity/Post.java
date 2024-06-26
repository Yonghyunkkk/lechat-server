package lechat.server.domain.post.entity;

import jakarta.persistence.*;
import lechat.server.domain.course.entity.Course;
import lechat.server.domain.member.entity.Member;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "content", nullable = false, length = 1000)
    private String content;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Comment> comments = new ArrayList<>();

    public static Post createPost(
            Member member,
            Course course,
            String title,
            String content,
            LocalDateTime createdAt
    ) {
        Post post = Post.builder()
                .member(member)
                .title(title)
                .course(course)
                .content(content)
                .createdAt(createdAt)
                .build();

        member.addPost(post);
        course.addPost(post);

        return post;
    }

    public void updatePost(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void addComments(Comment comment) {
        this.comments.add(comment);
        comment.setPost(this);
    }

}
