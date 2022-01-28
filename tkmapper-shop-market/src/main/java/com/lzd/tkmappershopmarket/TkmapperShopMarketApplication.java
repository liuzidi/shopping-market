package com.lzd.tkmappershopmarket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.lzd.dao")
public class TkmapperShopMarketApplication {

    public static void main(String[] args) {
        SpringApplication.run(TkmapperShopMarketApplication.class, args);
    }

}
