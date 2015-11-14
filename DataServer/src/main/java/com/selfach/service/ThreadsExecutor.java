package com.selfach.service;

import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by gekoreed on 11/14/15.
 */
@Component
public class ThreadsExecutor {

    private ExecutorService service = Executors.newFixedThreadPool(12);

    public void submit(Runnable thread){
        service.submit(thread);
    }
}
