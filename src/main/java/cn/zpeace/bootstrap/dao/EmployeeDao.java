package cn.zpeace.bootstrap.dao;

import cn.zpeace.bootstrap.anno.DataScope;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author zpeace
 * @date 2024/1/4
 */
@Mapper
public interface EmployeeDao {

    @DataScope
    @Select("SELECT * FROM t_employee WHERE username = #{username}")
    List<Employee> selectByUsername(@Param("username") String username);

    @Update("UPDATE t_employee SET username = #{employee.username} AND dept_id #{}")
    Integer update(@Param("employee") Employee employee);
}
