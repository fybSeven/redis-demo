package springboot.redis.redisdemo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springboot.redis.redisdemo.entity.Employee;
import springboot.redis.redisdemo.service.EmployeeService;

/**
 * @Author: fengyibo
 * @Date: 2019/1/15 10:29
 * @Description:
 */
@RestController
@RequestMapping("/employee")
@Api(tags = "员工")
public class EmployeeCtrl {

    @Autowired
    private EmployeeService employeeService;

    @RequestMapping(value = "/select", method = RequestMethod.GET)
    @ApiOperation(value = "员工查询", notes = "根据名字查询员工信息", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "姓名", required = true, dataType = "String")
    })
    public Employee selectEmployee(String name){
        return employeeService.select(name);
    }

}
