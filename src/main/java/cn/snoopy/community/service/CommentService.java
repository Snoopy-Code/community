package cn.snoopy.community.service;

import cn.snoopy.community.dto.CommentResponseDTO;
import cn.snoopy.community.enums.CommentTypeEnum;
import cn.snoopy.community.exception.CustomizeErrorCode;
import cn.snoopy.community.exception.CustomizeException;
import cn.snoopy.community.mapper.CommentMapper;
import cn.snoopy.community.mapper.QuestionMapper;
import cn.snoopy.community.mapper.UserMapper;
import cn.snoopy.community.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private UserMapper userMapper;

    @Transactional
    public void insert(Comment comment) {
        if(comment.getParentId() == null || comment.getParentId() == 0){
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        if(comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())){
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }
        if(comment.getType() == CommentTypeEnum.COMMENT.getType()){
            //回复评论
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if(dbComment == null){
                throw  new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insert(comment);
        }else {
            //回复问题
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if(question == null){
                throw  new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insert(comment);
            question.setCommentCount(1);
            questionService.inComment(comment.getParentId());
        }
    }

    public List<CommentResponseDTO> listByTargetId(Long id, CommentTypeEnum type) {
        CommentExample example = new CommentExample();
        example.createCriteria().andParentIdEqualTo(id).andTypeEqualTo(type.getType());//因为type有两个类型，1是问题，2是回复，所以需要确定是1
        //让最新回复置顶
        example.setOrderByClause("gmt_create desc");
        List<Comment> comments = commentMapper.selectByExample(example);
        if(comments.size() == 0){
            return  new ArrayList<>();
        }
        //lamdar语法
        //查询评论，循环查每条评论的作者,得到作者的id
        //避免重复，如果有10个回复，但是只是5个人写的这10个回复，避免查询10次重复
        Set<Long> commentators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());//将list集合改为set集合
        List<Long> userIds = new ArrayList<>();
        userIds.addAll(commentators);
        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdIn(userIds);
        List<User> users = userMapper.selectByExample(userExample);

        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));//list改为map,toMap里面是kv，k为user的id，v是user对象
        //将comments改为commentResponseDTO
        List<CommentResponseDTO> commentResponseDTOS = comments.stream().map(comment -> {
            CommentResponseDTO commentResponseDTO = new CommentResponseDTO();
            BeanUtils.copyProperties(comment,commentResponseDTO);
            commentResponseDTO.setUser(userMap.get(comment.getCommentator()));
            return commentResponseDTO;
        }).collect(Collectors.toList());
        return commentResponseDTOS;
    }
}
