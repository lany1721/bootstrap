package cn.zpeace.bootstrap.controller;

import cn.zpeace.bootstrap.dao.Employee;
import cn.zpeace.bootstrap.dao.EmployeeDao;
import cn.zpeace.bootstrap.support.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zpeace
 * @date 2024/1/4
 */
@RestController
@RequiredArgsConstructor
public class MyBatisController {

    private final EmployeeDao employeeDao;

    @GetMapping("/employees")
    public ApiResponse<List<Employee>> list(String username) {
        return ApiResponse.ok(employeeDao.selectByUsername(username));
    }

}
