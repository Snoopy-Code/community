package cn.snoopy.community.mapper;

import cn.snoopy.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuertionMapper {
    @Insert("insert into question (title,description,gmtCreate,gmtModified,creator,commentCount,viewCount,likeCount,tag) values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{commentCount},#{viewCount},#{likeCount},#{tag})")
    void create (Question question);

    @Select("select * from question")
    List<Question> list();
}
