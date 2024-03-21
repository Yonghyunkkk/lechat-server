package lechat.server.domain.post.repository;

import lechat.server.domain.post.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.yaml.snakeyaml.events.CommentEvent;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
