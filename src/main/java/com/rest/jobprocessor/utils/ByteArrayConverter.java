package com.rest.jobprocessor.utils;

import com.rest.jobprocessor.exceptions.ByteArrayConversionException;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Utility class to convert other data types to byte array
 */
public class ByteArrayConverter {

    /**
     * Method to convert byte array to double
     *
     * @param value double value to be converted
     * @return byte array representation of the double value
     */
    public Byte[] doubleToByteArray(double value) {
        byte[] bytes = new byte[8];
        ByteBuffer.wrap(bytes).putDouble(value);
        return toObjects(bytes);
    }

    /**
     * Method to convert byte array to integer
     *
     * @param value integer value to be converted
     * @return byte array representation of the int value
     * @throws ByteArrayConversionException if error occurs when converting to byte array
     */
    public Byte[] intToByteArray(int value) throws ByteArrayConversionException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);
        try {
            dos.writeInt(value);
            dos.flush();
        } catch (IOException ex) {
            throw new ByteArrayConversionException("Error occurred when converting to from integer to byte array", ex);
        }
        return toObjects(bos.toByteArray());
    }

    /**
     * Method to convert byte array to boolean
     *
     * @param value boolean value to be converted
     * @return byte array representation of the boolean value
     */
    public Byte[] booleanToByteArray(boolean value) {
        return toObjects(new byte[]{(byte) (value ? 1 : 0)});
    }

    /**
     * Method to convert from primitive byte array to Byte array wrapper class
     *
     * @param bytesPrim primitive byte array value to be converted
     * @return Byte array wrapper for the given primitive byte array
     */
    Byte[] toObjects(byte[] bytesPrim) {
        Byte[] bytes = new Byte[bytesPrim.length];
        Arrays.setAll(bytes, n -> bytesPrim[n]);
        return bytes;
    }
}
