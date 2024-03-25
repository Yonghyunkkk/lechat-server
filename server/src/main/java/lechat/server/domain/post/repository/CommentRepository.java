package lechat.server.domain.post.repository;

import lechat.server.domain.post.entity.Comment;
import lechat.server.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.yaml.snakeyaml.events.CommentEvent;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select c from Comment c " +
            "left join fetch c.member m " +
            "left join fetch c.replyComments rc " +
            "left join fetch rc.member rcm " +
            "where c.post.id = :postId")
    Optional<List<Comment>> findByPostId(@Param("postId") Long postId);
}
