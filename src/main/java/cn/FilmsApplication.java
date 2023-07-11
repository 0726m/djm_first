package cn;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
@MapperScan("cn.mapper")
@EnableCaching
public class FilmsApplication {
    //主类
    public static void main(String[] args) {
        SpringApplication.run(FilmsApplication.class, args);
    }
}
