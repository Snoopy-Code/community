package cn.snoopy.community.mapper;

import cn.snoopy.community.model.Question;

import java.util.List;

public interface QuestionExtMapper {
   List<Question> selectRelated(Question question);
}