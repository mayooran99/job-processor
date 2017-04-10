package com.rest.jobprocessor.jobs;

import com.rest.jobprocessor.exceptions.JobProcessingException;
import com.rest.jobprocessor.logger.LoggerFactory;
import org.slf4j.Logger;

import java.util.concurrent.Callable;

/**
 * Abstract class representing the contract of a job
 */
public abstract class Job implements Callable<Byte[]> {

    private Logger LOGGER = LoggerFactory.getLogger(Job.class);

    /**
     * Callable method implementation to execute the job
     *
     * @return value returned from the processing of the job
     */
    @Override
    public Byte[] call() {
        try {
            return execute();
        } catch (JobProcessingException e) {
            LOGGER.error("Error occurred during the processing of the job", e);
        }
        return new Byte[1];
    }

    /**
     * Method that should be implemented to contain the job logic
     *
     * @return value returned as a result of the execution
     * @throws JobProcessingException If error occurs during the processing of the job
     */
    public abstract Byte[] execute() throws JobProcessingException;
}
