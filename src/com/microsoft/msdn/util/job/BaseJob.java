package com.microsoft.msdn.util.job;


import org.quartz.Job;

import java.util.concurrent.ThreadPoolExecutor;

public interface BaseJob extends Job {
    public boolean isSendImmediately();

    public ThreadPoolExecutor getThreadPool();
}
