package cn.snoopy.community.controller;

import cn.snoopy.community.dto.NotifyDTO;
import cn.snoopy.community.enums.NotifyTypeEnum;
import cn.snoopy.community.model.User;
import cn.snoopy.community.service.NotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
public class NotifyController {
    @Autowired
    private NotifyService notifyService;

    @GetMapping("/notify/{id}")
    public String profile(HttpServletRequest request,
                          @PathVariable(name = "id")Long id
                         ){
        User user = (User)request.getSession().getAttribute("user");
        if(user == null){
            return "redirect:/";
        }

       NotifyDTO notifyDTO = notifyService.read(id,user);
        if(NotifyTypeEnum.REPLY_COMMENT.getType() == notifyDTO.getType()
                || NotifyTypeEnum.REPLY_QUESTION.getType() == notifyDTO.getType()){
            return "redirect:/question/"+notifyDTO.getOuterid();
        }else{
            return "redirect:/";
        }
    }
}
