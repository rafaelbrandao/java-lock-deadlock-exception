package safe;

import java.util.concurrent.locks.Lock;

public class Sample {
    public static void main(String args[]) {
        final Lock lock1 = new safe.ReentrantLock();
        final Lock lock2 = new safe.ReentrantLock();
        
        Thread t1 = new Thread(new Runnable() {
            public void run() {
                lock2.lock();
                lock1.lock();
                lock1.unlock();
                lock2.unlock();
            }
          }        
        );
        Thread t2 = new Thread(new Runnable() {
            public void run() {
                lock1.lock();
                lock2.lock();
                lock1.unlock();
                lock2.unlock();
            }
          }        
        );
        t1.start();
        t2.start();
    }
}
