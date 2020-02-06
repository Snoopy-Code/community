package cn.snoopy.community.dto;

import cn.snoopy.community.model.User;
import lombok.Data;

@Data
public class CommentResponseDTO {
    private Long id;
    private Long parentId;
    private Integer type;
    private Long commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private Long likeCount;
    private String content;
    private User user;
}
