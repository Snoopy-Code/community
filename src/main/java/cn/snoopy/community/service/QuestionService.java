package cn.snoopy.community.service;

import cn.snoopy.community.dto.PaginationDTO;
import cn.snoopy.community.dto.QuestionDTO;
import cn.snoopy.community.exception.CustomizeErrorCode;
import cn.snoopy.community.exception.CustomizeException;
import cn.snoopy.community.mapper.QuestionExtMapper;
import cn.snoopy.community.mapper.QuestionMapper;
import cn.snoopy.community.mapper.UserMapper;
import cn.snoopy.community.model.Question;
import cn.snoopy.community.model.QuestionExample;
import cn.snoopy.community.model.User;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionExtMapper questionExtMapper;


    public PaginationDTO list(Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalPage;
//       Integer totalCount = questionMapper.count();
        Integer totalCount = (int)questionMapper.countByExample(new QuestionExample());
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
//        List<Question> questions = questionMapper.list(offset,size);
         QuestionExample questionExample = new QuestionExample();
         questionExample.setOrderByClause("gmt_create desc");
        List<Question> questions = questionMapper.selectByExampleWithBLOBsWithRowbounds(questionExample,new RowBounds(offset,size));
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);//把question的属性全部拷贝进questionDTO中,相当于 questionDTO.setID(question.getId())老方法
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setData(questionDTOList);

        return paginationDTO;
    }
    public PaginationDTO list(Long userId, Integer page,Integer size){
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalPage;
//        Integer totalCount = questionMapper.countByUserId(userId);
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andCreatorEqualTo(userId);
        Integer totalCount = (int)questionMapper.countByExample(questionExample);
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
        QuestionExample example = new QuestionExample();
        example.createCriteria().andCreatorEqualTo(userId);
        example.setOrderByClause("gmt_create desc");
        List<Question> questions = questionMapper.selectByExampleWithBLOBsWithRowbounds(example,new RowBounds(offset,size));
//        List<Question> questions = questionMapper.listByUserId(userId,offset,size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);//把question的属性全部拷贝进questionDTO中,相当于 questionDTO.setID(question.getId())老方法
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setData(questionDTOList);
        return paginationDTO;
    }

    public QuestionDTO getById(Long id) {
//        Question question = questionMapper.getById(id);
        Question question = questionMapper.selectByPrimaryKey(id);
        if(question == null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void CreateOrUpdate(Question question) {
        if(question.getId()==null){
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            question.setCommentCount(0);
            question.setViewCount(0);
            question.setLikeCount(0);
            questionMapper.insert(question);
        }else {
            Question updateQuestion = new Question();
            updateQuestion.setGmtModified(System.currentTimeMillis());
            updateQuestion.setTitle(question.getTitle());
            updateQuestion.setDescription(question.getDescription());
            updateQuestion.setTag(question.getTag());
            QuestionExample example = new QuestionExample();
            example.createCriteria().andIdEqualTo(question.getId());
            int updated = questionMapper.updateByExampleSelective(updateQuestion, example);
            if(updated!=1){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }

    public void inView(Long id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        Question updateQuestion = new Question();
        updateQuestion.setViewCount(question.getViewCount()+1);
        QuestionExample example = new QuestionExample();
        example.createCriteria().andIdEqualTo(id);
        questionMapper.updateByExampleSelective(updateQuestion, example);
    }

    public void inComment(Long id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        Question updateQuestion = new Question();
        updateQuestion.setCommentCount(question.getCommentCount()+1);
        QuestionExample example = new QuestionExample();
        example.createCriteria().andIdEqualTo(id);
        questionMapper.updateByExampleSelective(updateQuestion, example);
    }

    public List<QuestionDTO> selectRelated(QuestionDTO queryDTO) {
        if(StringUtils.isEmpty(queryDTO.getTag())){
            return  new ArrayList<>();
        }
        String[] tags = StringUtils.split(queryDTO.getTag(), ",");
        String regexpTag = Arrays.stream(tags).collect(Collectors.joining("|"));
        Question question = new Question();
        question.setId(queryDTO.getId());
        question.setTag(regexpTag);

        List<Question> questions = questionExtMapper.selectRelated(question);
        List<QuestionDTO> questionDTOs = questions.stream().map(q -> {
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(q,questionDTO);
            return questionDTO;
        }).collect(Collectors.toList());
        return questionDTOs;
    }
}
