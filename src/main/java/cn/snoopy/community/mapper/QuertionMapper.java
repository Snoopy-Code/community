package cn.snoopy.community.mapper;

import cn.snoopy.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QuertionMapper {
    @Insert("insert into question (title,description,gmtCreate,gmtModified,creator,commentCount,viewCount,likeCount,tag) values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{commentCount},#{viewCount},#{likeCount},#{tag})")
    public void create (Question question);
}
