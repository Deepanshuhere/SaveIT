package com.saveit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class EConnectApplication
{
	public static void main(String[] args) 
	{
		SpringApplication.run(EConnectApplication.class, args);
	}

}
