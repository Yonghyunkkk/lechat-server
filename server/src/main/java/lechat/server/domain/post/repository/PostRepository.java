package lechat.server.domain.post.repository;

import lechat.server.domain.post.controller.response.GetAllPostsRes;
import lechat.server.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select new lechat.server.domain.post.controller.response.GetAllPostsRes(" +
            "p.id, p.member.name, p.title, p.content, p.createdAt, " +
            "case when p.member.id = :memberId then true else false end) " +
            "from Post p " +
            "where p.course.id = :courseId")
    List<GetAllPostsRes> findByCourseId(@Param("courseId") Long courseId, @Param("memberId") Long memberId);

}
