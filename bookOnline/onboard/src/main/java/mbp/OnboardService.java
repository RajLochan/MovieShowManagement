package mbp;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@Slf4j
@EnableAutoConfiguration
@ComponentScan(basePackages = {"mbp.onboard.*"})
public class OnboardService  {
    @Autowired

    public static void main(String[] args) {
        SpringApplication.run(OnboardService.class, args);
        System.out.println("Hello world!");
    }


}