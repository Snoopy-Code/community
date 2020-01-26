package cn.snoopy.community.service;

import cn.snoopy.community.dto.QuestionDTO;
import cn.snoopy.community.mapper.QuertionMapper;
import cn.snoopy.community.mapper.UserMapper;
import cn.snoopy.community.model.Question;
import cn.snoopy.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuertionMapper quertionMapper;

    @Autowired
    private UserMapper userMapper;

    public List<QuestionDTO> list() {
        List<Question> questions = quertionMapper.list();
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);//把question的属性全部拷贝进questionDTO中,相当于 questionDTO.setID(question.getId())老方法
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        return questionDTOList;
    }
}
