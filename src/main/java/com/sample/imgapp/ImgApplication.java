package com.sample.imgapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication//(scanBasePackages= {"com.sample.imgapp"})
//@ComponentScan({"com.sample.imgapp.repository", "com.sample.imgapp.controller", "com.sample.imgapp.model"})
//@Configuration
public class ImgApplication extends SpringBootServletInitializer 
{
    public static void main( String[] args )
    {
    	SpringApplication.run(ImgApplication.class, args);
    }
}
