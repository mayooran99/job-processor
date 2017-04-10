package com.rest.jobprocessor.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.rest.jobprocessor.schedulers.JobScheduler;
import com.rest.jobprocessor.schedulers.ThreadPoolScheduler;
import com.rest.jobprocessor.utils.ByteArrayConverter;
import com.sun.jersey.guice.JerseyServletModule;

/**
 * Class to declare the dependency injection bindings
 */
public class ServletContextListener extends GuiceServletContextListener {
    @Override
    protected Injector getInjector() {
        return Guice.createInjector(new JerseyServletModule() {
            @Override
            protected void configureServlets() {
                bind(JobScheduler.class).toInstance(new ThreadPoolScheduler());
                bind(ByteArrayConverter.class).toInstance(new ByteArrayConverter());
            }
        });
    }
}
