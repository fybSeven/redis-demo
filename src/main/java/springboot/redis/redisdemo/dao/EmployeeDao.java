package springboot.redis.redisdemo.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import springboot.redis.redisdemo.entity.Employee;

/**
 * @Author: fengyibo
 * @Date: 2019/1/11 16:41
 * @Description:
 */
@Repository
public class EmployeeDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeDao.class);

    public Employee selet(String name){
        LOGGER.info("请求进入数据库##################");
        return new Employee(name, "兰州", "18298339000", 27, true);
    }
}
