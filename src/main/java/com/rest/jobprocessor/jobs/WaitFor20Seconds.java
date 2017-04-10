package com.rest.jobprocessor.jobs;

import com.google.inject.Inject;
import com.rest.jobprocessor.exceptions.JobProcessingException;
import com.rest.jobprocessor.logger.LoggerFactory;
import com.rest.jobprocessor.utils.ByteArrayConverter;
import org.slf4j.Logger;

import java.util.concurrent.TimeUnit;

/**
 * Class containing the job to wait for 20 seconds
 */
public class WaitFor20Seconds extends Job {

    @Inject
    private ByteArrayConverter byteArrayConverter;

    private static final Logger LOGGER = LoggerFactory.getLogger(WaitFor20Seconds.class);

    /**
     * Method to wait for 20 seconds
     *
     * @return byte array containing the result
     * @throws JobProcessingException if interruption occurs during the wait
     */
    @Override
    public Byte[] execute() throws JobProcessingException {
        try {
            TimeUnit.SECONDS.sleep(20);
        } catch (InterruptedException ex) {
            LOGGER.error("Thread interrupted while waiting", ex);
            throw new JobProcessingException("Thread interruption occurred during the wait");
        }
        return byteArrayConverter.booleanToByteArray(true);
    }
}
