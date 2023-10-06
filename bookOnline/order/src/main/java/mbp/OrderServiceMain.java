package mbp;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@Slf4j
@EnableAutoConfiguration
@ComponentScan(basePackages = {"mbp.order.*"})
@EnableFeignClients
public class OrderServiceMain implements CommandLineRunner {

    public static void main(String[] args) {

        SpringApplication.run(OrderServiceMain.class, args);
        System.out.println("Hello world!");
    }

    @Override
    public void run(String... args) throws Exception {

    }
}