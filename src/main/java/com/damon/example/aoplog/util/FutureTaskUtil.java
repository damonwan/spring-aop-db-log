package com.damon.example.aoplog.util;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Supplier;

public class FutureTaskUtil {
	
	private FutureTaskUtil(){}
	
	public static <T> CompletableFuture<T> getCompletableFuture(Supplier<T> supplier){
		ExecutorService executor = Executors.newCachedThreadPool();
		CompletableFuture<T> future = CompletableFuture.supplyAsync(supplier, executor);
		executor.shutdown();
		return future;
	}
	
	public static CompletableFuture<?> getCompletableFuture(Runnable runnable){
		ExecutorService executor = Executors.newCachedThreadPool();
		CompletableFuture<?> future = CompletableFuture.runAsync(runnable, executor);
		executor.shutdown();
		return future;
	}

	public static <T> Future<T> getFuture(Callable<T> task){
		ExecutorService executor = Executors.newCachedThreadPool();
		Future<T> future = executor.submit(task);
		executor.shutdown();
		return future;
	}
	
	public static Future<?> getFuture(Runnable task){
		ExecutorService executor = Executors.newCachedThreadPool();
		Future<?> future = executor.submit(task);
		executor.shutdown();
		return future;
	}
}
