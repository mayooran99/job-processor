package com.rest.jobprocessor.schedulers;

import com.rest.jobprocessor.exceptions.JobProcessingException;

/**
 * Interface for the job scheduler
 */
public interface JobScheduler {

    /**
     * Method to be implemented to schedule a job from an external jar
     *
     * @param jobName Name of the job to be executed. This should be the name of the Job implementation class
     * @param jarName Name of the jar containing the Job class implementation
     * @return byte array containing the result of the job
     * @throws JobProcessingException If error occurs when scheduling the job
     */
    Byte[] scheduleJob(String jobName, String jarName) throws JobProcessingException;

    /**
     * Method to be implemented to schedule an internal job from within the project
     *
     * @param jobName Name of the job to be executed. This would be the name of the Job implementation class
     * @return byte array containing the result of the job
     * @throws JobProcessingException If error occurs when scheduling the job
     */
    Byte[] scheduleJob(String jobName) throws JobProcessingException;

    /**
     * Set the size of the worker threads during run time
     *
     * @param noOfWorkers No of worker threads to be used for executing the jobs
     */
    void setPoolSize(int noOfWorkers);
}
