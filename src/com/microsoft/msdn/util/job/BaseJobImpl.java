package com.microsoft.msdn.util.job;


import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.concurrent.ThreadPoolExecutor;

public class BaseJobImpl extends QuartzJobBean implements BaseJob {
    protected boolean sendImmediately;

    protected ThreadPoolExecutor threadPool;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {

    }

    @Override
    public boolean isSendImmediately() {
        return this.sendImmediately;
    }

    @Override
    public ThreadPoolExecutor getThreadPool() {
        return null;
    }

    public void setSendImmediately(boolean sendImmediately) {
        this.sendImmediately = sendImmediately;
    }

    public void setThreadPool(ThreadPoolExecutor threadPool) {
        this.threadPool = threadPool;
    }
}
