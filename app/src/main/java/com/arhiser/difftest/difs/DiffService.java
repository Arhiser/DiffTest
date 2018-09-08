package com.arhiser.difftest.difs;

import android.os.Handler;
import android.support.annotation.NonNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import de.danielbechler.diff.ObjectDiffer;
import de.danielbechler.diff.ObjectDifferBuilder;
import de.danielbechler.diff.node.DiffNode;

public class DiffService {

    private ExecutorService executorService;

    private ThreadLocal<ObjectDiffer> objectDiffer = new ThreadLocal<>();

    private Handler handler = new Handler();

    public DiffService(int threadsCount) {
        this.executorService = Executors.newFixedThreadPool(threadsCount);
    }

    public <T> Cancellable getDiff(T objectOld, T objectNew, DiffResultListener<T> listener) {
        if (executorService.isShutdown()) {
            throw new IllegalStateException("Executor was shut down");
        }
        DiffTask<T> diffTask = new DiffTask<T>(objectOld, objectNew, listener);
        executorService.execute(diffTask);
        return diffTask;
    }

    public void shutdown() {
        executorService.shutdown();
    }

    private ObjectDiffer getObjectDiffer() {
        ObjectDiffer objectDifferInstance = objectDiffer.get();
        if (objectDifferInstance == null) {
            ObjectDifferBuilder builder = ObjectDifferBuilder.startBuilding();
            builder.introspection().setDefaultIntrospector(new AndroidIntrospector());
            objectDifferInstance = builder.build();
            objectDiffer.set(objectDifferInstance);
        }
        return objectDifferInstance;
    }

    public interface DiffResultListener<T> {
        void onDiffResult(DiffData<T> diffData);
    }

    private class DiffTask<T> implements Runnable, Cancellable {
        private T object;
        private T objectNew;

        private DiffResultListener<T> resultListener;

        private volatile boolean isCanceled;

        private DiffTask(T object, T objectNew, @NonNull DiffResultListener<T> resultListener) {
            this.object = object;
            this.objectNew = objectNew;
            this.resultListener = resultListener;
        }

        @Override
        public void run() {
            final DiffNode diffNode = getObjectDiffer().compare(objectNew, object);
            if (!isCanceled) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(!isCanceled) {
                            resultListener.onDiffResult(new DiffData<>(object, objectNew, diffNode));
                        }
                    }
                });
            }
        }

        @Override
        public void cancel() {
            isCanceled = true;
        }
    }

    public static class DiffData<T> {
        private T object;
        private T objectNew;
        private DiffNode difs;

        public DiffData(T object, T objectNew, DiffNode difs) {
            this.object = object;
            this.objectNew = objectNew;
            this.difs = difs;
        }

        public T getObject() {
            return object;
        }

        public DiffNode getDifs() {
            return difs;
        }

        public T getObjectNew() {
            return objectNew;
        }
    }
}
