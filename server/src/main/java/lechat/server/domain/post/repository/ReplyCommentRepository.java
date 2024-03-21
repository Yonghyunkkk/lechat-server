package lechat.server.domain.post.repository;

import lechat.server.domain.post.entity.ReplyComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyCommentRepository extends JpaRepository<ReplyComment, Long> {
}
