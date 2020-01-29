package cn.snoopy.community.service;

import cn.snoopy.community.dto.PaginationDTO;
import cn.snoopy.community.dto.QuestionDTO;
import cn.snoopy.community.mapper.QuestionMapper;
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
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    public PaginationDTO list(Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalPage;
        Integer totalCount = questionMapper.count();
        if(totalCount % size == 0){
            totalPage = totalCount / size;
        }else {
            totalPage = totalCount /size + 1;
        }

        if(page<1){
            page=1;
        }
        if(page>totalPage){
            page=totalPage;
        }
        paginationDTO.setPagination(totalPage,page);

        Integer offset = size * (page-1);
        List<Question> questions = questionMapper.list(offset,size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);//把question的属性全部拷贝进questionDTO中,相当于 questionDTO.setID(question.getId())老方法
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);

        return paginationDTO;
    }
    public PaginationDTO list(Integer userId, Integer page,Integer size){
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalPage;
        Integer totalCount = questionMapper.countByUserId(userId);
        if(totalCount % size == 0){
            totalPage = totalCount / size;
        }else {
            totalPage = totalCount /size + 1;
        }

        if(page<1){
            page=1;
        }
        if(page>totalPage){
            page=totalPage;
        }
        paginationDTO.setPagination(totalPage,page);
        Integer offset = size * (page-1);
        List<Question> questions = questionMapper.listByUserId(userId,offset,size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);//把question的属性全部拷贝进questionDTO中,相当于 questionDTO.setID(question.getId())老方法
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);
        return paginationDTO;
    }

    public QuestionDTO getById(Integer id) {
        Question question = questionMapper.getById(id);
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        User user = userMapper.findById(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void CreateOrUpdate(Question question) {
        if(question.getId()==null){
            questionMapper.create(question);
        }else {
           question.setGmtCreate(System.currentTimeMillis());
            questionMapper.update(question);
        }
    }
}
