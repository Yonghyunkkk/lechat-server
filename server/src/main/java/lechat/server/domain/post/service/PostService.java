package lechat.server.domain.post.service;

import lechat.server.core.exception.CustomException;
import lechat.server.core.exception.ErrorInfo;
import lechat.server.domain.course.entity.Course;
import lechat.server.domain.course.repository.CourseRepository;
import lechat.server.domain.member.entity.Member;
import lechat.server.domain.member.respository.MemberRepository;
import lechat.server.domain.post.controller.request.CreatePostReq;
import lechat.server.domain.post.controller.response.CreatePostRes;
import lechat.server.domain.post.entity.Post;
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
public class PostService {

    private final MemberRepository memberRepository;
    private final CourseRepository courseRepository;
    private final PostRepository postRepository;

    @Transactional
    public CreatePostRes createPost(CreatePostReq request, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new CustomException(COURSE_NOT_FOUND));

        Post post = Post.createPost(member, course, request.getContent(), LocalDateTime.now());

        Post savedPost = postRepository.save(post);

        return new CreatePostRes(savedPost.getId());
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
}
