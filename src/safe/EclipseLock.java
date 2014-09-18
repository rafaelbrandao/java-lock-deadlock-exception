package safe;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.TimeUnit;
import safe.eclipse.LockManager;
import safe.eclipse.ILock;

public class EclipseLock implements Lock {
	static final LockManager lockManager = new LockManager();
	ILock lock;

	EclipseLock(){
		lock = lockManager.newLock();
	}

    public void lock() {
    	lock.acquire();
    }

    public void unlock() {
    	lock.release();
    }

	public void lockInterruptibly() throws InterruptedException {

    }

    public boolean tryLock() {
        return false;
    }

    public Condition newCondition() {
        return null;
    }

    public boolean tryLock(long timeout, TimeUnit unit)
            throws InterruptedException {
        return false;
    }

    public static LockManager getLockManager() {
    	return lockManager;
    }
}