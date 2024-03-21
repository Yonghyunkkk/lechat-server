package lechat.server.domain.post.service;

import lechat.server.core.exception.CustomException;
import lechat.server.domain.course.entity.Course;
import lechat.server.domain.course.repository.CourseRepository;
import lechat.server.domain.member.entity.Member;
import lechat.server.domain.member.respository.MemberRepository;
import lechat.server.domain.post.controller.request.CreateCommentReq;
import lechat.server.domain.post.controller.request.CreatePostReq;
import lechat.server.domain.post.controller.response.CreateCommentRes;
import lechat.server.domain.post.controller.response.CreatePostRes;
import lechat.server.domain.post.entity.Comment;
import lechat.server.domain.post.entity.Post;
import lechat.server.domain.post.repository.CommentRepository;
import lechat.server.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;

import static lechat.server.core.exception.ErrorInfo.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Transactional
    public CreateCommentRes createComment(CreateCommentReq request, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

        Post post = postRepository.findById(request.getPostId())
                .orElseThrow(() -> new CustomException(POST_NOT_FOUND));

        Comment comment = Comment.createComment(member, post, request.getContent(), LocalDateTime.now());

        Comment savedComment = commentRepository.save(comment);

        return new CreateCommentRes(savedComment.getId());
    }

    @Transactional
    public void deleteComment(Long memberId, Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(COMMENT_NOT_FOUND));

        if (!Objects.equals(comment.getMember().getId(), memberId)) {
            throw new CustomException(UNAUTHORIZED_OPERATION);
        }

        commentRepository.delete(comment);
    }
}
