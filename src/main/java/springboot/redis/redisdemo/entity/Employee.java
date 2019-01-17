package springboot.redis.redisdemo.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: fengyibo
 * @Date: 2019/1/11 16:37
 * @Description: 被序列化的对象一定要有默认无参的构造器，否则序列化会报错
 */
@Data
public class Employee implements Serializable {

    private static final long serialVersionUID = 7961406393306708335L;

    private String name;

    private String address;

    private String phone;

    private Integer age;

    private Boolean signle;

    public Employee(){}

    public Employee(String name, String address, String phone, Integer age, Boolean signle) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.age = age;
        this.signle = signle;
    }
}
