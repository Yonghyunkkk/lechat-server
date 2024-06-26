package lechat.server.domain.post.service;

import lechat.server.core.exception.CustomException;
import lechat.server.core.exception.ErrorInfo;
import lechat.server.domain.course.entity.Course;
import lechat.server.domain.course.repository.CourseRepository;
import lechat.server.domain.member.entity.Member;
import lechat.server.domain.member.respository.MemberRepository;
import lechat.server.domain.post.controller.request.CreatePostReq;
import lechat.server.domain.post.controller.request.UpdatePostReq;
import lechat.server.domain.post.controller.response.CreatePostRes;
import lechat.server.domain.post.controller.response.GetAllPostsRes;
import lechat.server.domain.post.controller.response.GetSinglePostRes;
import lechat.server.domain.post.entity.Comment;
import lechat.server.domain.post.entity.Post;
import lechat.server.domain.post.entity.ReplyComment;
import lechat.server.domain.post.repository.CommentRepository;
import lechat.server.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static lechat.server.core.exception.ErrorInfo.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final MemberRepository memberRepository;
    private final CourseRepository courseRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Transactional
    public CreatePostRes createPost(CreatePostReq request, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new CustomException(COURSE_NOT_FOUND));

        Post post = Post.createPost(member, course, request.getTitle(), request.getContent(), LocalDateTime.now());

        Post savedPost = postRepository.save(post);

        return new CreatePostRes(savedPost.getId());
    }

    @Transactional
    public void updatePost(Long memberId, Long postId, UpdatePostReq request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(POST_NOT_FOUND));

        if (!Objects.equals(post.getMember().getId(), memberId)) {
            throw new CustomException(UNAUTHORIZED_OPERATION);
        }

        post.updatePost(request.getTitle(), request.getContent());
    }

    @Transactional
    public void deletePost(Long memberId, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(POST_NOT_FOUND));

        if (!Objects.equals(post.getMember().getId(), memberId)) {
            throw new CustomException(UNAUTHORIZED_OPERATION);
        }

        postRepository.delete(post);
    }

    public List<GetAllPostsRes> getAllPosts(Long courseId, Long memberId) {
        courseRepository.findById(courseId)
                .orElseThrow(() -> new CustomException(COURSE_NOT_FOUND));

        return postRepository.findByCourseId(courseId, memberId);
    }

    public GetSinglePostRes getSinglePost(Long postId, Long memberId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(POST_NOT_FOUND));

        List<Comment> comments = commentRepository.findByPostId(postId)
                .orElseThrow(() -> new CustomException(COMMENT_NOT_FOUND));

        List<GetSinglePostRes.CommentDto> commentDtos = new ArrayList<>();

        for (Comment comment : comments) {
            List<GetSinglePostRes.ReplyCommentDto> replyCommentDtos = new ArrayList<>();
            for (ReplyComment replyComment : comment.getReplyComments()) {

                GetSinglePostRes.ReplyCommentDto replyCommentDto = GetSinglePostRes.ReplyCommentDto.builder()
                        .replyCommentId(replyComment.getId())
                        .replyCommentContent(replyComment.getContent())
                        .replyCommentAuthor(replyComment.getMember().getName())
                        .replyCommentCreatedAt(formatDateTime(replyComment.getCreatedAt()))
                        .replyCommentIsAuthor(memberId.equals(replyComment.getMember().getId()))
                        .build();

                replyCommentDtos.add(replyCommentDto);
            }

            commentDtos.add(
                    new GetSinglePostRes.CommentDto(
                    comment.getId(),
                    comment.getContent(),
                    comment.getMember().getName(),
                    formatDateTime(comment.getCreatedAt()),
                    memberId.equals(comment.getMember().getId()),
                    replyCommentDtos
                    )
            );
        }

        return new GetSinglePostRes(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getMember().getName(),
                formatDateTime(post.getCreatedAt()),
                memberId.equals(post.getMember().getId()),
                commentDtos
        );
    }

    private String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(formatter);
    }
}
