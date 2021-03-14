package com.example.demo;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
public class JerseyConfig extends ResourceConfig
{
    public JerseyConfig()
    {
        register(Home.class);
        register(Admin.class);
        register(Research.class);
        register(addResearch.class);
        register(updateResearch.class);
    }

}
