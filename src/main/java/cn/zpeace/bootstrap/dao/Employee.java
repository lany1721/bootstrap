package cn.zpeace.bootstrap.dao;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author zpeace
 */
@Data
@TableName("t_employee")
public class Employee {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String username;

    private Long deptId;

}
