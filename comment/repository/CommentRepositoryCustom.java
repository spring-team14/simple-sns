package sparta.code3line.domain.comment.repository;

import sparta.code3line.domain.comment.dto.CommentResponseDto;
import sparta.code3line.domain.comment.entity.Comment;
import sparta.code3line.domain.user.entity.User;

import java.util.List;
import java.util.Optional;

public interface CommentRepositoryCustom {
    List<Comment> getLikeCommentWithPageAndSortDesc(User user, long offset, int pageSize);
}
