package lechat.server.domain.post.service;

import lechat.server.core.exception.CustomException;
import lechat.server.domain.member.entity.Member;
import lechat.server.domain.member.respository.MemberRepository;
import lechat.server.domain.post.controller.request.CreateCommentReq;
import lechat.server.domain.post.controller.request.CreateReplyCommentReq;
import lechat.server.domain.post.controller.response.CreateCommentRes;
import lechat.server.domain.post.controller.response.CreateReplyCommentRes;
import lechat.server.domain.post.entity.Comment;
import lechat.server.domain.post.entity.Post;
import lechat.server.domain.post.entity.ReplyComment;
import lechat.server.domain.post.repository.CommentRepository;
import lechat.server.domain.post.repository.PostRepository;
import lechat.server.domain.post.repository.ReplyCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;

import static lechat.server.core.exception.ErrorInfo.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReplyCommentService {

    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
    private final ReplyCommentRepository replyCommentRepository;

    @Transactional
    public CreateReplyCommentRes createReplyComment(CreateReplyCommentReq request, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

        Comment comment = commentRepository.findById(request.getCommentId())
                .orElseThrow(() -> new CustomException(COMMENT_NOT_FOUND));

        ReplyComment replyComment = ReplyComment.createReplyComment(member, comment, request.getContent(), LocalDateTime.now());

        ReplyComment savedReplyComment = replyCommentRepository.save(replyComment);

        return new CreateReplyCommentRes(savedReplyComment.getId());
    }

    @Transactional
    public void deleteReplyComment(Long memberId, Long replyCommentId) {
        ReplyComment replyComment = replyCommentRepository.findById(replyCommentId)
                .orElseThrow(() -> new CustomException(REPLY_COMMENT_NOT_FOUND));

        if (!Objects.equals(replyComment.getMember().getId(), memberId)) {
            throw new CustomException(UNAUTHORIZED_OPERATION);
        }

        replyCommentRepository.delete(replyComment);
    }
}
