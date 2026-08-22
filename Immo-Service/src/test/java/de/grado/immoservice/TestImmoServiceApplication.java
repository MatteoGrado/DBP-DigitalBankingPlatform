package de.grado.immoservice;

import org.springframework.boot.SpringApplication;

public class TestImmoServiceApplication
{

    public static void main(String[] args)
    {
        SpringApplication.from(ImmoServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
