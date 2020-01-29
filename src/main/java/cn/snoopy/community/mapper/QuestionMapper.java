package cn.snoopy.community.mapper;

import cn.snoopy.community.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionMapper {
    @Insert("insert into question (title,description,gmtCreate,gmtModified,creator,commentCount,viewCount,likeCount,tag) values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{commentCount},#{viewCount},#{likeCount},#{tag})")
    void create (Question question);

    @Select("select * from question limit #{offset},#{size}")
    List<Question> list(Integer offset, Integer size);

    @Select("select count(1) from question")
    Integer count();

    @Select("select * from question where creator = #{userId} limit #{offset},#{size}")
    List<Question> listByUserId(Integer userId, Integer offset, Integer size);

    @Select("select count(1) from question where creator = #{userId}")
    Integer countByUserId(@Param("userId") Integer userId);

    @Select("select * from question where id = #{id}")
    Question getById(Integer id);

    @Update("update question set title = #{title},description = #{description},gmtCreate = #{gmtCreate},tag =  #{tag} where id = #{id}")
    void update(Question question);
}
