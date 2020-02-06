package cn.snoopy.community.controller;

import cn.snoopy.community.dto.CommentResponseDTO;
import cn.snoopy.community.dto.QuestionDTO;
import cn.snoopy.community.enums.CommentTypeEnum;
import cn.snoopy.community.service.CommentService;
import cn.snoopy.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id")Long id, Model model){
        QuestionDTO questionDTO = questionService.getById(id);
        List<QuestionDTO> relatedQuestions = questionService.selectRelated(questionDTO);
        List<CommentResponseDTO> comments = commentService.listByTargetId(id, CommentTypeEnum.QUESTION);
        //增加阅读数
        questionService.inView(id);
        model.addAttribute("question",questionDTO);
        model.addAttribute("comments",comments);
        model.addAttribute("relatedQuestions",relatedQuestions);
        return "question";
    }
}
