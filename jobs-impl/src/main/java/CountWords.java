import com.rest.jobprocessor.exceptions.ByteArrayConversionException;
import com.rest.jobprocessor.exceptions.JobProcessingException;
import com.rest.jobprocessor.jobs.Job;
import com.rest.jobprocessor.utils.ByteArrayConverter;

/**
 * Plugin class job for counting the words
 */
public class CountWords extends Job {

    private ByteArrayConverter byteArrayConverter = new ByteArrayConverter();

    /**
     * Method for executing the word count job
     *
     * @return byte array containing the number of words
     * @throws JobProcessingException if error occurs during the job processing
     */
    @Override
    public Byte[] execute() throws JobProcessingException {
        try {
            return byteArrayConverter.intToByteArray(getWordsCount("This is a test sentence"));
        } catch (ByteArrayConversionException e) {
            System.out.println("Error occurred when converting from integer to byte array" + e);
        }
        return new Byte[0];
    }

    private int getWordsCount(String sentence) {
        return sentence.split("\\s+").length;
    }
}
