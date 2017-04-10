package com.rest.jobprocessor.jobs;

import com.google.inject.Inject;
import com.rest.jobprocessor.exceptions.JobProcessingException;
import com.rest.jobprocessor.utils.ByteArrayConverter;

/**
 * Class to calculate the value of PI
 */
public class CalculatePi extends Job {

    @Inject
    private ByteArrayConverter byteArrayConverter;

    /**
     * Method to execute the pi calculation job
     *
     * @return byte array containing the value of PI
     * @throws JobProcessingException if error occurs while processing the job
     */
    @Override
    public Byte[] execute() throws JobProcessingException {
        return byteArrayConverter.doubleToByteArray(Math.PI);
    }


}
