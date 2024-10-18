package du.ac.kr.sb1008.dao;


import du.ac.kr.sb1008.dto.SimpleBbsDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ISimpleBbsDao {
//  @Select("select * from simple_bbs order by id desc")
    public List<SimpleBbsDto> listDao();

//  @Select("select * from simple_bbs where id =  + #{id}")
    public Object viewDao(String id);

//  @Insert("insert into simple_bbs(writer, title, content) values(#{writer},#{title},#{content})")
    public int writeDao(String writer, String title, String content);

//  @Update("update simple_bbs set writer = #{writer}, title = #{title}, content = #{content}  where id = #{id}")
    public int updateDao(String id, String writer, String title, String content);

//  @Delete("delete from simple_bbs where id = #{id})
    public int deleteDao(String id);
}
