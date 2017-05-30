package com.appdirect.sdk.executor;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.slf4j.MDC;

public class MdcExecutor implements ExecutorService {

	private ExecutorService underlying;

	public MdcExecutor(ExecutorService underlying) {
		this.underlying = underlying;
	}

	@Override
	public Future<?> submit(Runnable task) {
		return underlying.submit(wrap(task, MDC.getCopyOfContextMap()));
	}

	@Override
	public void execute(Runnable task) {
		underlying.execute(task);
	}

	@Override
	public void shutdown() {
		underlying.shutdown();
	}

	@Override
	public List<Runnable> shutdownNow() {
		return underlying.shutdownNow();
	}

	@Override
	public boolean isShutdown() {
		return underlying.isShutdown();
	}

	@Override
	public boolean isTerminated() {
		return underlying.isTerminated();
	}

	@Override
	public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
		return underlying.awaitTermination(timeout, unit);
	}

	@Override
	public <T> Future<T> submit(Callable<T> task) {
		return underlying.submit(task);
	}

	@Override
	public <T> Future<T> submit(Runnable task, T result) {
		return underlying.submit(task, result);
	}

	@Override
	public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
		return underlying.invokeAll(tasks);
	}

	@Override
	public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException {
		return underlying.invokeAll(tasks, timeout, unit);
	}

	@Override
	public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
		return underlying.invokeAny(tasks);
	}

	@Override
	public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
		return underlying.invokeAny(tasks, timeout, unit);
	}

	private Runnable wrap(Runnable runnable, Map<String, String> context) {
		return () -> {
			if (context != null) {
				MDC.setContextMap(context);
			}
			try {
				runnable.run();
			} finally {
				MDC.clear();
			}
		};
	}
}
