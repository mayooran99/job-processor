package com.rest.jobprocessor.schedulers;

import com.rest.jobprocessor.exceptions.JobProcessingException;
import com.rest.jobprocessor.jobs.CalculatePi;
import com.rest.jobprocessor.jobs.FailImmediately;
import com.rest.jobprocessor.jobs.Job;
import com.rest.jobprocessor.jobs.WaitFor20Seconds;
import com.rest.jobprocessor.logger.LoggerFactory;
import org.slf4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.concurrent.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Class to implement the thread pool scheduler for processing the jobs
 */
public class ThreadPoolScheduler implements JobScheduler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ThreadPoolScheduler.class);
    ThreadPoolExecutor executor;
    private static final BlockingQueue<Runnable> poolWorkQueue = new LinkedBlockingQueue<>();
    int corePoolSize = 5;
    int maxPoolSize = 10;
    long keepAliveTime = 5000;


    /**
     * Default constructor
     */
    public ThreadPoolScheduler() {
        executor = new ThreadPoolExecutor(corePoolSize, maxPoolSize, keepAliveTime, TimeUnit.MILLISECONDS, poolWorkQueue);
    }

    /**
     * Method to set the size of the thread pool at run time
     *
     * @param noOfWorkers No of worker threads to be used for executing the jobs
     */
    @Override
    public void setPoolSize(int noOfWorkers) {
        executor.setCorePoolSize(noOfWorkers);
    }

    /**
     * Schedule an internal job
     *
     * @param jobName Name of the job to be executed. This would be the name of the Job implementation class
     * @return
     */
    @Override
    public Byte[] scheduleJob(String jobName) throws JobProcessingException {
        Future<Byte[]> resultProcessed = null;
        Byte[] result = null;

        switch (jobName.toLowerCase()) {
            case "calculatepi":
                resultProcessed = executor.submit(new CalculatePi());
                break;
            case "failimmediately":
                resultProcessed = executor.submit(new FailImmediately());
                break;
            case "waitfor20seconds":
                resultProcessed = executor.submit(new WaitFor20Seconds());
                break;
            default:
                break;
        }
        if (resultProcessed != null && resultProcessed.isDone()) {
            try {
                result = resultProcessed.get();
            } catch (InterruptedException e) {
                throw new JobProcessingException("Interruption occurred while processing the job " + jobName, e);
            } catch (ExecutionException e) {
                throw new JobProcessingException("Execution error occurred while processing the job " + jobName, e);
            }
        }
        if (result != null) {
            return result;
        }
        return new Byte[0];
    }

    /**
     * Schedule a job from an external jar by loading the class through reflection
     *
     * @param jobName Name of the job to be executed. This should be the name of the Job implementation class
     * @param jarName Name of the jar containing the Job class implementation
     * @return Result returned from executing the job
     */
    @Override
    public Byte[] scheduleJob(String jobName, String jarName) throws JobProcessingException {
        JarFile jarFile = null;
        URLClassLoader cl = null;
        try {
            jarFile = new JarFile(jarName);
            Enumeration<?> e = jarFile.entries();
            URL[] urls = {new URL("jar:file:" + jarName + "!/")};
            cl = URLClassLoader.newInstance(urls, this.getClass().getClassLoader());
            return this.runJob(e, jobName, cl);
        } catch (IOException e) {
            throw new JobProcessingException("Error occurred when loading the external jar " + jarName, e);
        } finally {
            if (jarFile != null) {
                try {
                    jarFile.close();
                } catch (IOException e) {
                    throw new JobProcessingException("Error occurred when closing the jar file", e);
                }
            }
            try {
                cl.close();
            } catch (IOException e) {
                throw new JobProcessingException("Error occurred when closing the class loader", e);
            }
        }
    }

    /**
     * Method to run the job by submitting to the thread pool
     *
     * @param enumeration Entries contained in the jar file
     * @param jobName     Name of the job to be executed
     * @param cl          Class loader instance to load the plugin class
     * @return Result from the execution of the job
     */
    private Byte[] runJob(Enumeration<?> enumeration, String jobName, URLClassLoader cl) throws JobProcessingException {
        try {
            while (enumeration.hasMoreElements()) {
                JarEntry je = (JarEntry) enumeration.nextElement();
                if (!(je.isDirectory() || !je.getName().endsWith(".class"))) {

                    // -6 because of .class
                    String className = je.getName().substring(0, je.getName().length() - 6);
                    className = className.replace('/', '.');

                    Class<?> c = cl.loadClass(className);
                    if (className.endsWith(jobName)) {
                        Job job = (Job) c.newInstance();
                        Future<Byte[]> resultProcessed = executor.submit(job);
                        if (resultProcessed.isDone()) {
                            resultProcessed.get();
                        }
                    }

                }
            }
        } catch (ClassNotFoundException ex) {
            throw new JobProcessingException("Plugin class not found", ex);
        } catch (IllegalAccessException | InstantiationException ex) {
            throw new JobProcessingException("Error occurred when instantiating the plugin class", ex);
        } catch (InterruptedException | ExecutionException ex) {
            throw new JobProcessingException("Error occurred when retrieving the value from the future", ex);
        }
        return new Byte[0];
    }
}
