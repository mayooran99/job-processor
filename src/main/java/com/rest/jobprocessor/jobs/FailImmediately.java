package com.rest.jobprocessor.jobs;

import com.google.inject.Inject;
import com.rest.jobprocessor.exceptions.JobProcessingException;
import com.rest.jobprocessor.logger.LoggerFactory;
import com.rest.jobprocessor.utils.ByteArrayConverter;
import org.slf4j.Logger;

import java.io.IOException;

/**
 * Class to fail a job immediately
 */
public class FailImmediately extends Job {

    @Inject
    private ByteArrayConverter byteArrayConverter;

    private static final Logger LOGGER = LoggerFactory.getLogger(FailImmediately.class);

    /**
     * Method to fail a job immediately by throwing an exception
     *
     * @throws JobProcessingException Processing exception to fail the job
     */
    @Override
    public Byte[] execute() throws JobProcessingException {
        LOGGER.info("Job failed!");
        throw new JobProcessingException("Fail Immediately");
    }
}
