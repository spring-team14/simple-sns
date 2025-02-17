package sparta.code3line.domain.comment.dto;

import lombok.Builder;
import lombok.Data;
import sparta.code3line.domain.comment.entity.Comment;

@Data
public class CommentResponseDto {

    private Long userId;
    private Long boardId;
    private String contents;
    private Integer likeCount;

    public CommentResponseDto(Comment comment) {
        this.userId = comment.getUser().getId();
        this.boardId = comment.getBoard().getId();
        this.contents = comment.getContents();
        this.likeCount = comment.getLikeCount();
    }

    @Builder
    public CommentResponseDto(Long userId, Long boardId, String contents, Integer likeCount) {
        this.userId = userId;
        this.boardId = boardId;
        this.contents = contents;
        this.likeCount = likeCount;
    }
}
