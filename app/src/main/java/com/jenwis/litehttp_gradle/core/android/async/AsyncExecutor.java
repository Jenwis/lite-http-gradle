package com.jenwis.litehttp_gradle.core.android.async;

import android.os.Handler;
import android.os.Looper;

import com.jenwis.litehttp_gradle.core.android.log.Log;

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * 异步执行
 * @author MaTianyu
 */
public class AsyncExecutor {
	private static final String TAG = AsyncExecutor.class.getSimpleName();
	private static ExecutorService threadPool;
	public static Handler handler = new Handler(Looper.getMainLooper());

    public AsyncExecutor() {
        this(null);
    }

    public AsyncExecutor(ExecutorService threadPool) {
        if (threadPool == null) {
            AsyncExecutor.threadPool = Executors.newCachedThreadPool();
        } else {
            AsyncExecutor.threadPool = threadPool;
        }
    }

	public static synchronized void shutdownNow() {
		if (threadPool != null && !threadPool.isShutdown()) threadPool.shutdownNow();
		threadPool = null;
		handler = null;
	}

	/**
	 * 将任务投入线程池执行
	 * @param worker
	 * @return
	 */
	public <T> FutureTask<T> execute(final Worker<T> worker) {
		Callable<T> call = new Callable<T>() {
			@Override
			public T call() throws Exception {
				return postResult(worker, worker.doInBackground());
			}
		};
		FutureTask<T> task = new FutureTask<T>(call) {
			@Override
			protected void done() {
				try {
					get();
				} catch (InterruptedException e) {
					Log.e(TAG, e);
					worker.abort();
					e.printStackTrace();
				} catch (ExecutionException e) {
					Log.e(TAG, e.getMessage());
					e.printStackTrace();
					throw new RuntimeException("An error occured while executing doInBackground()", e.getCause());
				} catch (CancellationException e) {
					worker.abort();
					Log.e(TAG, e);
					e.printStackTrace();
				}
			}
		};
		threadPool.execute(task);
		return task;
	}

	/**
	 * 将子线程结果传递到UI线程
	 * @param worker
	 * @param result
	 * @return
	 */
	private <T> T postResult(final Worker<T> worker, final T result) {
		handler.post(new Runnable() {
			@Override
			public void run() {
				worker.onPostExecute(result);
			}
		});
		return result;
	}

	public <T> FutureTask<T> execute(Callable<T> call) {
		FutureTask<T> task = new FutureTask<T>(call);
		threadPool.execute(task);
		return task;
	}

	public static abstract class Worker<T> {
		protected abstract T doInBackground();

		protected void onPostExecute(T data) {}

		protected void abort() {}
	}
}
