package springboot.redis.redisdemo.redis;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import springboot.redis.redisdemo.RedisDemoApplication;
import springboot.redis.redisdemo.entity.Employee;
import springboot.redis.redisdemo.service.EmployeeService;

/**
 * @Author: fengyibo
 * @Date: 2019/1/11 16:52
 * @Description:
 */
@SpringBootTest(classes = RedisDemoApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class RedisTest {

    @Autowired
    private EmployeeService employeeService;

    @Test
    public void selectTest(){
        Employee employee = employeeService.select("tudou");
        System.out.println(JSONObject.toJSONString(employee));
    }

    @Test
    public void testLock() throws InterruptedException {
        System.out.println("is lock: " + employeeService.setNX("lock", 60));
        Thread.sleep(5000);
        employeeService.freeLock("lock");
    }
}
