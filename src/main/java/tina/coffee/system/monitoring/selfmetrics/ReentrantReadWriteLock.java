package tina.coffee.system.monitoring.selfmetrics;

import java.util.function.Supplier;

public class ReentrantReadWriteLock extends java.util.concurrent.locks.ReentrantReadWriteLock {

    public <T> T secureRead(Supplier<T> action) {
        readLock().lock();
        try {
            return action.get();
        } finally {
            readLock().unlock();
        }
    }

    public void secureWrite(Runnable action) {
        writeLock().lock();
        try {
            action.run();
        } finally {
            writeLock().unlock();
        }
    }

    public <T> T secureWrite(Supplier<T> action) {
        writeLock().lock();
        try {
            return action.get();
        } finally {
            writeLock().unlock();
        }
    }
}