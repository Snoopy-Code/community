package cn.snoopy.community.controller;

import cn.snoopy.community.dto.CommentDTO;
import cn.snoopy.community.dto.CommentResponseDTO;
import cn.snoopy.community.dto.ResultDTO;
import cn.snoopy.community.enums.CommentTypeEnum;
import cn.snoopy.community.exception.CustomizeErrorCode;
import cn.snoopy.community.model.Comment;
import cn.snoopy.community.model.User;
import cn.snoopy.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;

    @ResponseBody
    @PostMapping("/comment")
     public Object post(@RequestBody CommentDTO commentDTO,
                        HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        if(user == null){
           return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
        if(commentDTO == null||commentDTO.getContent() == null||commentDTO.getContent()==""){
            return ResultDTO.errorOf(CustomizeErrorCode.CONTENT_IS_EMPTY);
        }
        Comment comment = new Comment();
        comment.setParentId(commentDTO.getParentId());
        comment.setContent(commentDTO.getContent());
        comment.setType(commentDTO.getType());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setCommentator(user.getId());
        comment.setLikeCount(0L);
        commentService.insert(comment);
        return ResultDTO.okOf();
     }

     @ResponseBody
     @GetMapping("/comment/{id}")
     public ResultDTO<List> comment(@PathVariable(name = "id")Long id){
         List<CommentResponseDTO> commentResponseDTOS = commentService.listByTargetId(id, CommentTypeEnum.COMMENT);
         return ResultDTO.okOf(commentResponseDTOS);
     }
}

