package com.microsoft.msdn.util;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.helpers.OptionConverter;

import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-4-10
 * Time: ����11:11
 * To change this template use File | Settings | File Templates.
 */
public class FileLogger {
    private static final String LOG_CONFIG_FILE = "logconfig.xml";
    protected Logger mLogger;

    static {
        try {
            URL url = Thread.currentThread().getContextClassLoader().getResource("logconfig.xml");
            OptionConverter.selectAndConfigure(url, null, LogManager.getLoggerRepository());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public FileLogger() {
        mLogger = null;
        Throwable throwable = new Throwable();
        StackTraceElement el[] = throwable.getStackTrace();
        if (el != null && el[1] != null)
            mLogger = getLogger(el[1].getClassName());
        else
            mLogger = Logger.getRootLogger();
    }

    public FileLogger(String sName) {
        mLogger = null;
        mLogger = getLogger(sName);
    }

    public static void initial() throws Exception {
        URL url = Thread.currentThread().getContextClassLoader().getResource("logconfig.xml");
        OptionConverter.selectAndConfigure(url, null, LogManager.getLoggerRepository());
    }

    public static Logger getLogger(String sName) {
        if (sName == null)
            return Logger.getRootLogger();
        else
            return Logger.getLogger(sName);
    }

    public static Logger getLogger() {
        Throwable throwable = new Throwable();
        StackTraceElement el[] = throwable.getStackTrace();
        if (el != null && el[1] != null)
            return getLogger(el[1].getClassName());
        else
            return Logger.getRootLogger();
    }

    public void debug(Object message) {
        mLogger.debug(message);
    }

    public void info(Object message) {
        mLogger.info(message);
    }

    public void warn(Object message) {
        mLogger.warn(message);
    }

    public void error(Object message) {
        mLogger.error(message);
    }

    public void fatal(Object message) {
        mLogger.fatal(message);
    }

    public void debug(Object message, Throwable ex) {
        mLogger.debug(message, ex);
    }

    public void info(Object message, Throwable ex) {
        mLogger.info(message, ex);
    }

    public void warn(Object message, Throwable ex) {
        mLogger.warn(message, ex);
    }

    public void error(Object message, Throwable ex) {
        mLogger.error(message, ex);
    }

    public void fatal(Object message, Throwable ex) {
        mLogger.fatal(message, ex);
    }
}
