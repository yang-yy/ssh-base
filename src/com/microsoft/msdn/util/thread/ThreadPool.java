package com.microsoft.msdn.util.thread;

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class ThreadPool extends ThreadPoolExecutor {
    public ThreadPool(int corePoolSize, int cacheSize, long keepAliveTime, RejectedExecutionHandler handler) {
        super(corePoolSize, corePoolSize, keepAliveTime, TimeUnit.MILLISECONDS, new PriorityBlockingQueue<Runnable>(cacheSize), handler);
    }
}
