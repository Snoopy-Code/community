package cn.snoopy.community.controller;

import cn.snoopy.community.dto.AccesstokenDTO;
import cn.snoopy.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;
    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code")String code,
                            @RequestParam(name = "state")String state) throws IOException {
        AccesstokenDTO accesstokenDTO = new AccesstokenDTO();
        accesstokenDTO.setClient_id("ec488d44344ef745bdce");
        accesstokenDTO.setClient_secret("8a1446f3b00d3cd4a34b74f914131672c7deaa79");
        accesstokenDTO.setCode(code);
        accesstokenDTO.setRedirect_uri("http://localhost:8080/callback");
        accesstokenDTO.setState(state);
        githubProvider.getAccessToken(accesstokenDTO);
        return "index";
    }
}
