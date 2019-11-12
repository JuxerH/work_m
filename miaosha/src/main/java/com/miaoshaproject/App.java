package com.miaoshaproject;

import com.miaoshaproject.dao.UserDOMapper;
import com.miaoshaproject.dataobject.UserDO;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Hello world!
 *spring.datasource.url=jdbc:mysql://rm-bp1s8wez9j4hw42oemo.mysql.rds.aliyuncs.com:3306/dizi-01
 * spring.datasource.username=dizi
 * spring.datasource.password=Dizi1234
 */
@SpringBootApplication(scanBasePackages = {"com.miaoshaproject"})
@RestController
@MapperScan("com.miaoshaproject.dao")
public class App 
{
    @Autowired
    private UserDOMapper userDOMapper;

    @RequestMapping(path = "/q")
    public String home(){
        UserDO userDO=userDOMapper.selectByPrimaryKey(1);
        if (userDO==null)
            return "用户对象不存在";
        else
            return userDO.getName();
    }
    public static void main( String[] args )
    {


        System.out.println( "Hello World!" );
        SpringApplication.run(App.class,args);
    }
}
