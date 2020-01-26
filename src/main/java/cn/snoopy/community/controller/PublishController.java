package cn.snoopy.community.controller;

import cn.snoopy.community.mapper.QuertionMapper;
import cn.snoopy.community.mapper.UserMapper;
import cn.snoopy.community.model.Question;
import cn.snoopy.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {
    @Autowired
    private QuertionMapper quertionMapper;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("tag") String tag,
            HttpServletRequest request,
            Model model){
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        if(title == null || title == ""){
            model.addAttribute("error","标题不可为空!");
            return "publish";
        }
        if(description == null || description == ""){
            model.addAttribute("error","问题描述不可为空!");
            return "publish";
        }
        if(tag == null || tag == ""){
            model.addAttribute("error","标题不可为空!");
            return "publish";
        }
        User user = null;
        Cookie[] cookies = request.getCookies();
        if(cookies!=null&&cookies.length!=0)
        for (Cookie cookie : cookies) {
            if(cookie.getName().equals("token")){
                String token = cookie.getValue();
                user = userMapper.findByToken(token);
                if(user != null){
                    request.getSession().setAttribute("user",user);
                }
                break;
            }
        }
        if(user == null){
            model.addAttribute("error","你还没有登陆!");
            return "publish";
        }

        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());

        quertionMapper.create(question);
        return "redirect:/";
    }
}
