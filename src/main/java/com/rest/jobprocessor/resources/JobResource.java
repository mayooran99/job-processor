package com.rest.jobprocessor.resources;

import com.google.inject.Inject;
import com.rest.jobprocessor.exceptions.JobProcessingException;
import com.rest.jobprocessor.schedulers.JobScheduler;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * Resource class to handle job processing requests
 */
@Path("/Jobs")
public class JobResource {

    @Inject
    private JobScheduler scheduler;

    /**
     * Method to handle job requests from external jar files which would be loaded as plugins
     *
     * @param jobName Name of the job to be processed. This would be the name of the job implementation class
     * @param jarName Name of the jar file in which the job exists
     * @return Json response containing the result
     */
    @GET
    @Produces("application/json")
    public Response scheduleJob(@PathParam("jobName") String jobName, @PathParam("jarName") String jarName) {
        Byte[] result;
        try {
            result = scheduler.scheduleJob(jobName, jarName);
        } catch (JobProcessingException e) {
            return Response.ok("Job processing error occurred").build();
        }
        return Response.ok(result).build();
    }

    /**
     * Method to handle requests to process jobs that declared internal
     *
     * @param jobName Name of the job to be process. This would be the name of the job implementation class
     * @return Json response containing the result
     */
    @GET
    @Produces("application/json")
    public Response scheduleJob(@PathParam("jobName") String jobName) {
        Byte[] result;
        try {
            result = scheduler.scheduleJob(jobName);
        } catch (JobProcessingException e) {
            return Response.ok("Job processing error occurred ").build();
        }
        return Response.ok(result).build();
    }

    /**
     * Method to set the worker thread size to process the jobs, at run time
     *
     * @param noOfWorkers amount of worker threads to be used from the thread pool
     * @return result of setting the size of the pool
     */
    @GET
    @Produces("application/json")
    public Response setWorkerThreadSize(@PathParam("noOfWorkers") int noOfWorkers) {
        scheduler.setPoolSize(noOfWorkers);
        return Response.ok("success").build();
    }
}
