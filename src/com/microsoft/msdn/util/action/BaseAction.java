package com.microsoft.msdn.util.action;


import java.util.concurrent.ThreadPoolExecutor;

public interface BaseAction {
    public ThreadPoolExecutor getThreadPool();
}
