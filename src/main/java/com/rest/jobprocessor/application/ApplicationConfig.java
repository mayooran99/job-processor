package com.rest.jobprocessor.application;

import com.rest.jobprocessor.resources.JobResource;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * Class to declare root resource classes and providers
 */
@ApplicationPath("/")
public class ApplicationConfig extends Application {

    /**
     * Method to delcare all request scoped resources and providers
     *
     * @return
     */
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(JobResource.class);
        return classes;
    }

    /**
     * Method to declare all singleton resources and providers
     *
     * @return
     */
    @Override
    public Set<Object> getSingletons() {
        Set<Object> singletons = new HashSet<>();
        return singletons;
    }
}
