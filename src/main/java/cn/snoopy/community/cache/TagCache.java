package cn.snoopy.community.cache;

import cn.snoopy.community.dto.TagDTO;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TagCache {
    public static List<TagDTO> get(){
        List<TagDTO> tagDTOS = new ArrayList<>();

        TagDTO language = new TagDTO();
        language.setCategoryName("开发语言");
        language.setTags(Arrays.asList("java","c","c#","php","python","html","css","js","html5","golang"));
        tagDTOS.add(language);

        TagDTO problem = new TagDTO();
        problem.setCategoryName("开发问题");
        problem.setTags(Arrays.asList("BUG","小坑","调试","debug","测试","软件"));
        tagDTOS.add(problem);

        TagDTO tool = new TagDTO();
        tool.setCategoryName("使用工具");
        tool.setTags(Arrays.asList("devtools","mysql","lombok","idea","postman","moment.js","github","mybatis generator"));
        tagDTOS.add(tool);

        TagDTO server = new TagDTO();
        server.setCategoryName("服务器");
        server.setTags(Arrays.asList("linux","docker","centos","hadoop","windows","nginx","tomcat"));
        tagDTOS.add(server);

        TagDTO DB = new TagDTO();
        DB.setCategoryName("数据库");
        DB.setTags(Arrays.asList("mysql","h2","redis","oracle","mongodb","sql","nosql memcached"));
        tagDTOS.add(DB);
        return tagDTOS;
    }
    public static String filterInvalid(String tags){
        String[] split = StringUtils.split(tags, ",");
        List<TagDTO> tagDTOS = get();
        List<String> tagList = tagDTOS.stream().flatMap(tag -> tag.getTags().stream()).collect(Collectors.toList());
        String inValid = Arrays.stream(split).filter(t -> !tagList.contains(t)).collect(Collectors.joining(","));
        return inValid;
    }
}
